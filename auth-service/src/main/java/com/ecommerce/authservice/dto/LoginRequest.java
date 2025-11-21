package com.ecommerce.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String phone,

        @NotBlank
        String password
) {
}
