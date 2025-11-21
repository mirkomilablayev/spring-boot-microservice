package com.ecommerce.authservice.security;


import com.ecommerce.authservice.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access-expiration}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String phone, Set<Role> roles) {
        var now = new Date();
        var expiry = new Date(now.getTime() + expirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("roles", roles.stream().map(Enum::name).toList());
        return Jwts.builder().setSubject(phone).setClaims(claims).setIssuedAt(now).setExpiration(expiry).signWith(secretKey).compact();
    }


    public String extractPhone(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("phone", String.class);
    }


    public boolean isValid(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
