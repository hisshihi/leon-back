package com.example.leon.services.impl;

import com.example.leon.domain.dto.JwtAuthenticationResponse;
import com.example.leon.domain.dto.SignInRequest;
import com.example.leon.repositories.MastersRepository;
import com.example.leon.services.AuthenticationService;
import com.example.leon.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MastersRepository mastersRepository;
    private final JWTService jwtService;

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
//        Поиск нужного мастера для авторизации
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUserName(),
                signInRequest.getPassword()));

        var master = mastersRepository.findByUserName(signInRequest.getUserName()).orElseThrow(() -> new IllegalArgumentException("Мастер не найден"));
        // Генерация токена
        var jwt = jwtService.generateToken(master);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), master);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
}
