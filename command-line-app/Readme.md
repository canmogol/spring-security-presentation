# CLI App

## Usage

Command line:
```shell
java -jar app.jar [args...]
```

Example(s):

```shell
java -jar Main.jar GET /products john.admin.rest-of-the-token
["product1", "product2", "product3"]
```

```shell
java -jar Main.jar DELETE /products/product1 john.admin.rest-of-the-token
["product2", "product3"]
```

```shell
java -jar Main.jar POST /products/product4 john.admin.rest-of-the-token
["product1", "product2", "product3", "product4"]
```
