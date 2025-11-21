package com.ecommerce.profileservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Set;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @SuppressWarnings("unchecked")
    public UserPrincipal parseUser(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody();

        var userId = claims.get("userId", Number.class).longValue();
        var phone = claims.get("phone", String.class);
        var roles = Set.copyOf((List<String>) claims.get("roles"));

        return new UserPrincipal(userId, phone, roles);
    }

    public boolean isValid(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
