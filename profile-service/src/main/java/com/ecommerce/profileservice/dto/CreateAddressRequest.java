package com.ecommerce.profileservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAddressRequest(
        @NotBlank String country,
        @NotBlank String city,
        @NotBlank String street,
        @NotBlank String ziipcode,
        boolean primary
) {
}
