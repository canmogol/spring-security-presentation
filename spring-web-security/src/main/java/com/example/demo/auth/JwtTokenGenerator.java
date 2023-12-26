package com.example.demo.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

@Component
public class JwtTokenGenerator {

    private long tokenExpiration = 86400; // 24 * 60 * 60 = 86400 = 1 day

    private String tokenPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCnxrcnH5fseWMO/1D17SfCIl8w976kKFlA9yFZn4ymID8hdxTPXzeUR5YwaorEUNgVBaX3Znzd4gULhnDQIUUbeue1WiJtsjkYfDZjOCA5bMF+2IhA48yy2kn2ux6GAJzICVUUhwNgLdzSK6zhHrCFOsoFbMCYixLQd1uP6nW4CvewW+CarUMBJOyhipY7d2sNYc43qFozQG5Q8h9ikF9aFA5tFa96ncWGMzH4gqu/i5MTkMsaT77Ry4sZxzrCxtlfnlu0clyZegJHRbj1GUkZUn/Q6Tb3PxOd1mlBK4Xj8tY112E6WwbdGW+3nQloR5XwbL9CB4GVFQypHNr1mgqzAgMBAAECggEAUMTLW1xMSR1O4ONs2FepMj5V0T8TWffKsAnI1lqG3VrcyYGIVSSjnxCvbvN7hnLc85HaijWemzq57whzut6pZEdQ0O9pb6HC9tSlYjKc4MhIhoY7YfHIk7merdb7JMuarno/qTguGdk6WzuHaIDbvefFZcMrHMSm9BDh1XCr50MoAPNbrXsZNs3n8CDWE/g9gCZL4X7a3j/5HE/eq8+TFGn96n5m74VPQfcaPqQC2pqBQgfkWEwcy5eKhL/qzmQupWlp1wvsNFIYc8X5Mr5C4xXvqgYbqu7Ug3vXzbegiUAVGPB3RUZBGOd8YNe59bhoW4w1JR8UZQifuyHAPf0qAQKBgQDjVjN1n21lJ9P2+cttQRuTOubk1+Xe/CAknF9AKJf+CPeYtClXkXmSDl752l8pijTuI2LZ7iSQhQpYk2JWoVWuTBZwR0m6AnkFyQRQM//eRH095hp194a7hdW6FeJTuBWNK3GFQR6Plciymwnfkx41SovaLKAA3RAiNns9XM7BgQKBgQC87hEkPGIaPB7n4JJWCYNpZvxx51clHZ/xtrU4GD77WueBKMgcRc1wUEL9w19MJa71loqu6UW0gm9TPRqeRAQv+wJ3BImSgXdyKj0VqPjqSwQzGedbvh8jWXsI1WDe8isIJuXuO3/0vuV1uJeOA44a+wogB6g9ti1ZrYs98yx+MwKBgEIWkSckjbzWczxdKdI9FzMZ8H2edej5Cq8Z9mbEZksneDMgTNR0Kg9XtiyUWw3Ma86+4sHQBTkuM+/ECNZ+WFYvMGa7kqdMiLox0dsvArcu2engttiMeNKS+ar7KayE218Kvhmq8KN1O78QazJfpmx/m2B1kJ4vpb0McY9JJ6IBAoGACnJ/sClGo3y/mMLqVKeiBKoVO5c3Um4Y8hzQWJG+qdeVBadjqWySt6s4iR/MnUFYM2xnrtRnblvoHAo89pN8R8rxj/NISDmwiTecuR3m5c3QJaNBD+mrfyDrlMl32dw6VPERteLEaJxFxAS5HLlFmmhXXv4NZ5wzVcoq8PoJc9ECgYBzI4ry1trF6LWSnBx1wbuY//T4eTuwIqDTUjq8dGccbUNz3ihQkO03dBfwTurX/UCKxj1DxvS1CJTimlmB4O+oO9pQT4fDa8h74zeBo+jJr6LUwwJvWir1qxSpfKnx4Tkp13sU9C8C1psdZ5hchk0Y+3VdIyU58awu+aSEqA3Ffw==";

    private PrivateKey privateKey;

    public JwtTokenGenerator() {
        try {
            final PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(tokenPrivateKey.getBytes()));
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPrivate);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(role));
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + (tokenExpiration * 1000)))
                .signWith(privateKey, StandardSecureDigestAlgorithms.findBySigningKey(privateKey))
                .compact();
    }

}
