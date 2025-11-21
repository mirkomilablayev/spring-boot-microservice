package com.ecommerce.authservice.utils;

public enum TokenType {

    BEARER("Bearer"),;


    String name;
    TokenType(String name) {
        this.name = name;
    }
}
