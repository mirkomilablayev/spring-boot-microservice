package com.ecommerce.profileservice.security;

import java.util.Set;

public record UserPrincipal(Long userId, String phone, Set<String> roles) {
}
