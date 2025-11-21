package com.ecommerce.authservice.service;


import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.LoginRequest;
import com.ecommerce.authservice.dto.RefreshTokenRequest;
import com.ecommerce.authservice.dto.RegisterRequest;
import com.ecommerce.authservice.model.Role;
import com.ecommerce.authservice.model.User;
import com.ecommerce.authservice.repository.UserRepository;
import com.ecommerce.authservice.security.JwtService;
import com.ecommerce.authservice.utils.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByPhone(request.phone())) {
            throw new IllegalArgumentException("username is already exist");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        User user = User.builder().fullName(request.fullName()).phone(request.phone()).password(passwordEncoder.encode(request.password())).roles(roles).build();


        User saved = userRepository.save(user);
        var accessToken = jwtService.generateToken(saved.getId(), saved.getPhone(), saved.getRoles());
        var refreshToken = refreshTokenService.create(saved);

        return new AuthResponse(accessToken, refreshToken.getToken(), TokenType.BEARER.name(), saved.getId(), saved.getPhone(), saved.getRoles());
    }


    public AuthResponse login(LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.phone(), request.password());

        authenticationManager.authenticate(authToken);

        User user = userRepository.findByPhone(request.phone()).orElseThrow(() -> new IllegalArgumentException("username or password is incorrect"));

        var accessToken = jwtService.generateToken(user.getId(), user.getPhone(), user.getRoles());
        var refreshToken = refreshTokenService.create(user);
        return new AuthResponse(accessToken, refreshToken.getToken(), TokenType.BEARER.name(), user.getId(), user.getPhone(), user.getRoles());
    }


    public AuthResponse refresh(RefreshTokenRequest request) {
        var refreshToken = refreshTokenService.validate(request.refreshToken());
        var user = refreshToken.getUser();

        var newAccessToken = jwtService.generateToken(user.getId(), user.getPhone(), user.getRoles());

        refreshTokenService.revoke(refreshToken);
        var newRefreshToken = refreshTokenService.create(user);
        return new AuthResponse(newAccessToken, newRefreshToken.getToken(), TokenType.BEARER.name(), user.getId(), user.getPhone(), user.getRoles());
    }

}
