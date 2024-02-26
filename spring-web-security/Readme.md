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


## Requests Examples (Only Authentication)

Login
```bash
curl --request POST 'http://localhost:8080/login' --header 'Content-Type: application/json' \
--data '{
    "username": "john",
    "password": "123"
}'

{"jwt":"eyJ...agA"}
```

GET Products
```bash
curl --request GET http://localhost:8080/products --header 'Authorization: Bearer ${jwt}'
```

Add Product
```bash
curl --request POST http://localhost:8080/products --header 'Content-Type: text/plain'  --header 'Authorization: Bearer ${jwt}' --data 'product4'
```

Delete Product
```bash
curl --request DELETE 'http://localhost:8080/products' --header 'Content-Type: text/plain'  --header 'Authorization: Bearer ${jwt}' --data 'product4'
```


## Spring Security Internals

Spring Security checks the authority at `org.springframework.security.access.expression.SecurityExpressionRoot::hasAnyAuthorityName()` method, before executing any method annotated with `@PreAuthorize` annotation.
