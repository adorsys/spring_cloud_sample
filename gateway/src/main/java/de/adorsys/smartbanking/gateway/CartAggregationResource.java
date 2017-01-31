package de.adorsys.smartbanking.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Hirsch
 */
@RestController
public class CartAggregationResource {

  private static final Logger LOG = LoggerFactory.getLogger(CartAggregationResource.class);

  private static final String CART_URL = "http://localhost:8080/cart/{id}";

  private static final String PRODUCT_URL = "http://localhost:8080/product/{id}";

  private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

  /**
   * Fetches the cart for given id. The cart comes with a list of productIds.
   * We'll fetch every product and return the enriched cart.
   * Using DeferredResult and ListenableFuture as currently spring does not provide
   * an async httpclient using CompleteableFuture
   */
  @RequestMapping("/cart-view/{id}")
  public DeferredResult<ResponseEntity<?>> cart(@PathVariable("id") int id) {
    DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(5000L);
    ListenableFuture<ResponseEntity<Cart>> cartFuture = asyncRestTemplate.getForEntity(CART_URL, Cart.class, id);
    cartFuture.addCallback(
      cartResponse -> {
        Cart cart = cartResponse.getBody();
        EnrichedCart enrichedCart = new EnrichedCart(cart.id, cart.total, new ArrayList<>());
        List<ListenableFuture<?>> productFutures = new ArrayList<>();
        cart.items.forEach(productId -> productFutures.add(fetchProduct(productId, enrichedCart.items)));
        // need to block and check ourselves if all futures are done
        waitForFutures(productFutures);
        deferredResult.setResult(new ResponseEntity<>(enrichedCart, HttpStatus.OK));
      },
      throwable -> {
        LOG.error("Failed to fetch cart {}", id, throwable);
        deferredResult.setResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }
    );
    return deferredResult;
  }

  private ListenableFuture<?> fetchProduct(int productId, List<Product> products) {
    ListenableFuture<ResponseEntity<Product>> productFuture = asyncRestTemplate.getForEntity(PRODUCT_URL, Product.class, productId);
    productFuture.addCallback(
      productResponse -> products.add(productResponse.getBody()),
      throwable -> LOG.warn("Could not fetch product {}", productId, throwable)
    );
    return productFuture;
  }

  private void waitForFutures(List<ListenableFuture<?>> futures) {
    while (futures.stream().anyMatch(future -> !future.isDone())) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException ex) {
        throw new IllegalStateException("Error waiting for futures", ex);
      }
    }
  }

  static class Cart {
    public int id;
    public double total;
    public List<Integer> items;
  }

  static class EnrichedCart {
    public int id;
    public double total;
    public List<Product> items;
    public EnrichedCart(int id, double total, List<Product> items) {
      this.id = id;
      this.total = total;
      this.items = items;
    }
  }

  static class Product {
    public int id;
    public String name;
    public int price;
  }

}
