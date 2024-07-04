package com.example.leon.config;

import com.example.leon.services.JWTService;
import com.example.leon.services.MastersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.startsWith;

@Component
@RequiredArgsConstructor
// JwtAuthenticatonFilter - фильтр, который выполняется один раз за запрос (OncePerRequestFilter).
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final MastersService mastersService;

    @Override

    /*
    * doFilterInternal - основной метод фильтра, выполняющий проверку JWT токена:
    *   Извлекает заголовок Authorization из запроса.
    *   Проверяет наличие заголовка и его формат (должен начинаться с "Bearer ").
    *   Извлекает JWT токен из заголовка.
    *   Извлекает имя пользователя из токена.
    *   Проверяет, что имя пользователя не пустое и аутентификация ещё не выполнена.
    *   Загружает данные пользователя из mastersService.
    *   Проверяет валидность токена с помощью jwtService.
    *   Создает объект UsernamePasswordAuthenticationToken с данными пользователя и устанавливает его в контекст безопасности SecurityContextHolder.
    *   Передает запрос следующему фильтру в цепочке.
    * */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        // Проверка наличия заголовка Authorization и его формата
        if (StringUtils.isEmpty(authHeader) || !startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Извлечение JWT из заголовка
        jwt = authHeader.substring(7);

        // Извлечение имени пользователя из JWT
        userName = jwtService.extractUserName(jwt);

        // Проверка валидности имени пользователя и аутентификации в контексте безопасности
        if (isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = mastersService.masterDetailService().loadUserByUsername(userName);

            // Проверка валидности токена
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Создание объекта аутентификации
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Установка аутентификационного контекста
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        // Передача запроса следующему фильтру
        filterChain.doFilter(request, response);

    }
}
