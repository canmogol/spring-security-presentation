package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String httpMethod = args[0];
        String path = args[1];

        if (path.startsWith("/login")) {
            String username = path.substring("/login/".length());
            if("john".equals(username)){
                System.out.println("john.admin.rest-of-the-token");
            }else{
                System.out.println("invalid username");
            }

        } else if (path.startsWith("/products")) {
            List<String> products = new ArrayList<>();
            products.add("product1");
            products.add("product2");
            products.add("product3");

            if ("GET".equals(httpMethod)) {
                System.out.println(products);

            } else if ("DELETE".equals(httpMethod)) {
                String productToDelete = path.substring("/products/".length());
                products.remove(productToDelete);
                System.out.println(products);

            } else if ("POST".equals(httpMethod)) {
                String productToAdd = path.substring("/products/".length());
                products.add(productToAdd);
                System.out.println(products);
            }
        }
    }
}