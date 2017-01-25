package de.adorsys.smartbanking.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RestController
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @GetMapping(path = "/")
    public List<Product> getProducts() {
        return Arrays.asList(new Product("Foo", 12345), new Product("Bar", 1099));
    }

    @GetMapping(path = "/fallback")
    public List<Product> getFallback() {
        return Collections.singletonList(new Product("Fallback", 12345));
    }

}
