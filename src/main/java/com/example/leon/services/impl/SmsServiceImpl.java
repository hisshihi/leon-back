package com.example.leon.services.impl;

import com.example.leon.services.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    private final RestTemplate restTemplate;
    @Value("${iqsms.login}")
    private String login;
    @Value("${iqsms.password}")
    private String password;
    @Value("${iqsms.url}")
    private String urlApi;

    public SmsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendSms(String phone, String text) {
        try {
            String encodedPhone = URLEncoder.encode(phone, StandardCharsets.UTF_8.toString());
            String url = String.format("%s/messages/v2/send?login=%s&password=%s&phone=%s&text=%s",
                    urlApi, login, password, encodedPhone, text);
            log.info(url);
            restTemplate.getForObject(url, String.class);
        } catch (UnsupportedEncodingException e) {
            log.error("Ошибка отправки SMS {}", e);
        }
    }
}
