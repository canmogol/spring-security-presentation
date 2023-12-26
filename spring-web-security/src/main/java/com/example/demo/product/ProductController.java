package com.example.demo.product;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private final List<String> products = new ArrayList<>();

    public ProductController() {
        products.add("product1");
        products.add("product2");
        products.add("product3");
    }

    @GetMapping("/products")
    public List<String> getProducts() {
        return products;
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody String product) {
        products.add(product);
    }

    @DeleteMapping("/products")
    public void deleteProduct(@RequestBody String product) {
        products.remove(product);
    }

}
