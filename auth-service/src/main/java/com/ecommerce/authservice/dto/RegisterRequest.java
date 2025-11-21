package com.ecommerce.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        String fullName,

        @NotBlank
        @Size(min = 6, max = 20)
        String phone,

        @NotBlank
        @Size(min = 6, max = 10)
        String password
) {
}
