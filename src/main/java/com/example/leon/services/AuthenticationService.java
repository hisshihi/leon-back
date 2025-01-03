package com.example.leon.services;

import com.example.leon.domain.dto.JwtAuthenticationResponse;
import com.example.leon.domain.dto.RefreshTokenRequest;
import com.example.leon.domain.dto.SignInRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
