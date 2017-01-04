package de.adorsys.smartbanking.cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    @Value("${cart.items:-1}")
    private int cartItems;

    @GetMapping("{id}/count")
    public HashMap<String, Integer> getCartItems(@PathVariable int id) {
        HashMap<String, Integer> body = new HashMap<>();
        body.put("id", id);
        body.put("items", cartItems);
        return body;
    }


}
