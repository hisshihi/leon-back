package com.example.leon.jwt;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String userName;
    private String password;

}
