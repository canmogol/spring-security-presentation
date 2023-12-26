package com.example.demo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.isNull;

@Component
public class JwtTokenUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String ROLES = "roles";

    private String secret = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp8a3Jx+X7HljDv9Q9e0nwiJfMPe+pChZQPchWZ+MpiA/IXcUz183lEeWMGqKxFDYFQWl92Z83eIFC4Zw0CFFG3rntVoibbI5GHw2YzggOWzBftiIQOPMstpJ9rsehgCcyAlVFIcDYC3c0ius4R6whTrKBWzAmIsS0Hdbj+p1uAr3sFvgmq1DASTsoYqWO3drDWHON6haM0BuUPIfYpBfWhQObRWvep3FhjMx+IKrv4uTE5DLGk++0cuLGcc6wsbZX55btHJcmXoCR0W49RlJGVJ/0Ok29z8TndZpQSuF4/LWNddhOlsG3Rlvt50JaEeV8Gy/QgeBlRUMqRza9ZoKswIDAQAB";

    public boolean validateToken(String token) {
        String username = getUsernameFromToken(token);
        return !isNull(username) && !isTokenExpired(token);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoleList(String token) {
        return getClaimFromToken(token, claims -> claims.get(ROLES, List.class));
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(secret));
            final PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            final JwtParser jwtParser = Jwts.parser().verifyWith(publicKey).build();
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            log.error("Error while parsing token", e);
        }
        throw new RuntimeException("Error while parsing token: '%s'".formatted(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

}
