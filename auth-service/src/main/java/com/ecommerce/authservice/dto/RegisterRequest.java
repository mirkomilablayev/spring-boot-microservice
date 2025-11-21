package com.ecommerce.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        String fullName,

        @NotBlank
        @Size(min = 12, max = 12)
        @Pattern(
                regexp = "^998\\d{9}$",
                message = "the number should start with 998 and size must be exact same size 12"
        )
        String phone,

        @NotBlank
        @Size(min = 6, max = 30)
        String password
) {
}
