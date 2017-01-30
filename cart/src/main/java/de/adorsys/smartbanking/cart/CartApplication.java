package de.adorsys.smartbanking.cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    @Value("${springSale}")
    private boolean springSale;

    @GetMapping("{id}")
    public HashMap<String, Object> getCart(@PathVariable int id) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("id", id);
        body.put("items", Arrays.asList(4711, 1820, 3433));
        double total = 104.12;
        body.put("total", springSale ? total * 0.9 : total);
        return body;
    }

}
