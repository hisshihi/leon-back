package com.example.leon.jwt;

import lombok.Getter;

@Getter
public class JWTResponse {

    private final String jwt;
    private final String refreshToken;

    public JWTResponse(String jwt, String refreshToken) {
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

}
