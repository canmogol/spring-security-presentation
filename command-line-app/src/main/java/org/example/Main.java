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
            if(args.length != 3){
                System.out.println("Usage: jbang Main.java <httpMethod> <path> <token>");
                System.exit(1);
            }

            // Authenticate the user
            String token = args[2];
            if(!token.equals("john.admin.rest-of-the-token")){
                System.out.println("invalid token");
                System.exit(1);
            }

            ProductController productController = new ProductController();
            if ("GET".equals(httpMethod)) {
                List<String> products = productController.getAll();
                System.out.println(products);

            } else if ("DELETE".equals(httpMethod)) {
                String productToDelete = path.substring("/products/".length());
                List<String> products = productController.remove(productToDelete);
                System.out.println(products);

            } else if ("POST".equals(httpMethod)) {
                String productToAdd = path.substring("/products/".length());
                List<String> products = productController.add(productToAdd);
                System.out.println(products);
            }
        }
    }
}


class ProductController {
    List<String> products = new ArrayList<>();

    public ProductController() {
        products.add("product1");
        products.add("product2");
        products.add("product3");
    }

    public List<String> getAll() {
        return products;
    }

    public List<String> remove(String productToDelete) {
        products.remove(productToDelete);
        return products;
    }

    public List<String> add(String productToAdd) {
        products.add(productToAdd);
        return products;
    }
}