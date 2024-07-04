package com.example.leon.services.impl;

import com.example.leon.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/*
* указывает на то, что этот класс является сервисом в Spring.
* Это позволяет Spring автоматически обнаруживать и управлять этим классом как компонентом.
* */
@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

//    Создание токена
    private String generateToken(UserDetails userDetails) {
//        Jwts.builder() - используется для создания нового JWT.
//        setSubject(userDetails.getUsername()) - устанавливает субъекта токена, которым является имя пользователя.
        return Jwts.builder().setSubject(userDetails.getUsername())
                // Дата выпуска
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Срок жизни
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                // Ключ подписи
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
//                compact() - генерирует и возвращает окончательный строковый представление токена.
                .compact();
    }

//    Этот метод принимает токен и функцию для извлечения определенной части данных (претензий) из токена.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
//        extractAllClaims(token) - извлекает все претензии из токена.
        final Claims claims = extractAllClaims(token);
//        claimsResolvers.apply(claims) - применяет функцию для извлечения нужной информации из претензий.
        return claimsResolvers.apply(claims);
    }

    // Извлечение имени пользователяя
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    Извлечение всех данных из токена
    private Claims extractAllClaims(String token) {
        /*
        * Jwts.parserBuilder() - создает новый парсер для JWT.
        * setSigningKey(getSiginKey()) - устанавливает ключ для подписи.
        *  build().parseClaimsJws(token).getBody() - разбирает токен и извлекает его тело (претензии).
        * */
        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

//    Получение ключа для подписи
    private Key getSiginKey() {
        /*
        * Этот метод декодирует строку Base64 в байтовый массив.
        * Keys.hmacShaKeyFor(key) - создает ключ для HMAC-SHA алгоритма на основе байтового массива.
        * */
        byte[] key = Decoders.BASE64.decode("ad70b2a8c1da44cbe209223bbf4252ed1e5c41254f88b6242990aafb81856f4ade43957ab6f7be57f020743db00c9e6987053bde0f3dd1fbbbed1bb57a2d94d19a2e47f49aeb4e42cacf72395a58c9128cf988d6e9716061986e9901e4917ac7d39b0a147ec34225164afd5074b7c5d98f321209804346798ed3050289d544923580c1ce9241b47d643b98310440c5583c33f02d3041ce553b015615b8f351ab6e09ec76f17215373af520444dd3885c4ae9798fab70a31944cafaf801ec43f19a81a60cd67a52487c56d68d1fecc7289c7327470f12526b14ffe98fe5dff297f96413d8f62de8aa80cafd2ca6b3d5290112469d16a2d59401a27c375b06bfbf");
        return Keys.hmacShaKeyFor(key);
    }
}
