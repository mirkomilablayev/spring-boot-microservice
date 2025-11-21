package com.ecommerce.authservice.service;

import com.ecommerce.authservice.model.RefreshToken;
import com.ecommerce.authservice.model.User;
import com.ecommerce.authservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expirations}")
    private long refreshExpirationMs;

    @Transactional
    public RefreshToken create(User user){
       var token = RefreshToken.builder()
               .token(java.util.UUID.randomUUID().toString())
               .user(user)
               .expiresAt(Instant.now().plusMillis(refreshExpirationMs))
               .revoked(false)
               .build();
       return refreshTokenRepository.save(token);
    }

    public RefreshToken validate(String token){
        var refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if(refreshToken.isRevoked()){
            throw new IllegalArgumentException("Refresh token is revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())){
            throw new IllegalArgumentException("Refresh token expired");
        }

        return refreshToken;
    }

    @Transactional
    public void revoke(RefreshToken refreshToken){
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }



}

