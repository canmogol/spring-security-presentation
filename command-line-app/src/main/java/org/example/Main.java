package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String httpMethod = args[0];
        String path = args[1];
        if ("GET".equals(httpMethod) && "/products".equals(path)) {
            List<String> products = List.of("Product 1", "Product 2", "Product 3");
            System.out.println(products);
        }
    }
}