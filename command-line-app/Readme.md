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
jbang Main.java GET /products john.admin.rest-of-the-token
["product1", "product2", "product3"]
```

```shell
jbang Main.java DELETE /products/product1 john.admin.rest-of-the-token
["product2", "product3"]
```

```shell
jbang Main.java POST /products/product4 john.admin.rest-of-the-token
["product1", "product2", "product3", "product4"]
```

Login: `POST /login/{username}` > `{TOKEN}`
```shell
jbang Main.java POST /login/john
"john.admin.rest-of-the-token"
```
```shell
jbang Main.java POST /login/mike
"mike.user.rest-of-the-token"
```
