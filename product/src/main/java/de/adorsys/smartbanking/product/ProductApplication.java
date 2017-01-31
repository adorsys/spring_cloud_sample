package de.adorsys.smartbanking.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@RestController
public class ProductApplication {

    private Random rnd = new Random();

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @GetMapping(path = "/{id}")
    public Product getProduct(@PathVariable("id") int id) {
      return new Product(id, "Product " + id, rnd.nextInt(1000));
    }

    @GetMapping(path = "/fallback")
    public List<Product> getFallback() {
        return Collections.singletonList(new Product(0, "Fallback", 12345));
    }

}
