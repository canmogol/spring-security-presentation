# CLI App

## Filters

- Authentication

## Usage

Command line:
```shell
jbang Main.java [args...]
```

Example(s):

```shell
jbang Main.java GET /products
["product1", "product2", "product3"]
```

```shell
jbang Main.java DELETE /products/product1
["product2", "product3"]
```

```shell
jbang Main.java POST /products/product4
["product1", "product2", "product3", "product4"]
```
