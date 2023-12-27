package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String httpMethod = args[0];
        String path = args[1];

        if (path.startsWith("/login")) {
            String username = path.substring("/login/".length());
            if ("john".equals(username)) {
                System.out.println("john.admin.rest-of-the-token");
            } else if ("mike".equals(username)) {
                System.out.println("mike.user.rest-of-the-token");
            } else {
                System.out.println("invalid username");
            }

        } else if (path.startsWith("/products")) {
            if (args.length != 3) {
                System.out.println("Usage: jbang Main.java <httpMethod> <path> <token>");
                System.exit(1);
            }

            AuthenticationFilter authenticationFilter = new AuthenticationFilter();
            authenticationFilter.authenticate(args);

            AuthorizationFilter authorizationFilter = new AuthorizationFilter();
            authorizationFilter.authorize(args);

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

class AuthenticationFilter {
    public void authenticate(String[] args) {
        // Authenticate the user
        String token = args[2];
        if (token.equals("john.admin.rest-of-the-token")
                || token.equals("mike.user.rest-of-the-token")) {
            System.out.println("authenticated, valid token");
        } else {
            System.out.println("invalid token");
            System.exit(1);
        }
    }
}

class AuthorizationFilter {
    public void authorize(String[] args) {
        String httpMethod = args[0];
        String path = args[1];
        String token = args[2];
        // admin users can delete products
        // [_, admin, _]
        String[] tokenParts = token.split("\\.");
        String role = tokenParts[1];
        if ("DELETE".equals(httpMethod)
                && path.startsWith("/products")
                && !role.equals("admin")) {
            System.out.println("unauthorized, not an 'admin'");
            System.exit(1);
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