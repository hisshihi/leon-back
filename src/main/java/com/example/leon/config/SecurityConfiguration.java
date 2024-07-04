package com.example.leon.config;

import com.example.leon.domain.entities.Role;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final MastersService mastersService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        // Разрешение доступа без авторизации к GET запросу
                        .requestMatchers(HttpMethod.GET, "/masters").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schedule/**").permitAll()
                        // Разрешение доступа без авторизации к POST запросу
                        .requestMatchers(HttpMethod.POST, "/appointment").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        // Запросы требующие роль ADMIN
                        .requestMatchers(HttpMethod.POST, "/masters/**").hasAnyAuthority(Role.ADMIN.name())
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider — провайдер аутентификации, который использует базу данных для проверки подлинности пользователей.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        mastersService.masterDetailService() — сервис для загрузки пользовательских данных.
        authenticationProvider.setUserDetailsService(mastersService.masterDetailService());
//        passwordEncoder() — метод для кодирования паролей.
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
