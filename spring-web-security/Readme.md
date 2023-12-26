# Spring Security Web JWT Application

## Requests Examples (No Authentication and Authorization)

GET Products
```bash
curl --request GET http://localhost:8080/products
```

Add Product
```bash
curl --request POST http://localhost:8080/products --header 'Content-Type: text/plain' --data 'product4'
```

Delete Product
```bash
curl --request DELETE 'http://localhost:8080/products' --header 'Content-Type: text/plain' --data 'product4'
```

Login
```bash
curl --request POST 'http://localhost:8080/login' --header 'Content-Type: application/json' \
--data '{
    "username": "john",
    "password": "123"
}'
```
