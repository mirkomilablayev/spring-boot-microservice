package com.ecommerce.authservice.dto;

import com.ecommerce.authservice.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AuthResponse(
        @NotBlank
        @NotNull
        String accessToken,

        @NotBlank
        @NotNull
        String refreshToken,

        @NotBlank
        @NotNull
        String tokenType,

        @NotBlank
        @NotNull
        Long userId,

        @NotBlank
        @NotNull
        String phone,

        @NotBlank
        @NotNull
        Set<Role> roles
) {}
