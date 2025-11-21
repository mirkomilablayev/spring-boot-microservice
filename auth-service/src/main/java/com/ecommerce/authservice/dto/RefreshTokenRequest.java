package com.ecommerce.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(
        @NotNull
        @NotBlank
        String refreshToken
) {
}
