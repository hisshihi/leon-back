package com.example.leon.services.impl;

import com.example.leon.services.OTPService;
import com.example.leon.services.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
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

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final SmsService smsService;

    @Override
    public String generateOTP(String phone) {
        String otp = String.format("%04d", new Random().nextInt(10000)); // Генерируем 4-х значный код
        log.info("Номер телефона: {}", phone);
        otpStorage.put(phone, otp);
        sendSms(phone, otp);
        // Запланированное удаление OTP через 3 минуты
        scheduler.schedule(() -> removeOTP(phone), 3, TimeUnit.MINUTES);

        return otp;
    }

    private void removeOTP(String phone) {
        otpStorage.remove(phone);
        log.info("OTP для {} был удалён", phone);
    }

    private void sendSms(String phone, String otp) {
        String text = "Код для записи: " + otp;
        smsService.sendSms(phone, text);
        log.info("Текст смс: {}", text);
    }

    @Override
    public boolean validateOTP(String phone, String otp) {
        String storedOtp = otpStorage.get(phone);
        if (otp.equals(storedOtp)) otpStorage.remove(phone);
        return otp.equals(storedOtp);
    }
}
