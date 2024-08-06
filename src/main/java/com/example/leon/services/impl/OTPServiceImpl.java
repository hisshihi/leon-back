package com.example.leon.services.impl;

import com.example.leon.services.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OTPServiceImpl implements OTPService {

    private final RestTemplate restTemplate;
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Value("${iqsms.login}")
    private String login;

    @Value("${iqsms.password}")
    private String password;

    @Override
    public String generateOTP(String phone) {
        String otp = String.format("%04d", new Random().nextInt(10000)); // Генерируем 4-х значный код

        log.info("Номер телефона: {}", phone);

        otpStorage.put(phone, otp);
        try {
            sendSms(phone, otp);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // Запланированное удаление OTP через 2 минуты
        scheduler.schedule(() -> removeOTP(phone), 2, TimeUnit.MINUTES);

        return otp;
    }

    private void removeOTP(String phone) {
        otpStorage.remove(phone);
        log.info("OTP для {} был удалён", phone);
    }

    private void sendSms(String phone, String otp) throws UnsupportedEncodingException {
        String text = String.format("Kod dlya zapisi " + otp);

        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());

        String formattedPhone = phone.replaceAll("\\s+", "");

        String url = UriComponentsBuilder.fromHttpUrl("https://api.iqsms.ru/messages/v2/send")
                .queryParam("login", login)
                .queryParam("password", password)
                .queryParam("phone", formattedPhone)
                .queryParam("text", encodedText)
                .toUriString();

        log.info("Запрос URL: {}", url);

        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("Ответ сервера: {}", response);
        } catch (Exception e) {
            log.error("Ошибка при отправке SMS: ", e);
        }
    }

    @Override
    public boolean validateOTP(String phone, String otp) {
        String storedOtp = otpStorage.get(phone);
        if (otp.equals(storedOtp)) otpStorage.remove(phone);
        return otp.equals(storedOtp);
    }
}
