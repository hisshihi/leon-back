package com.example.leon.services.impl;

import com.example.leon.services.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public String generateOTP(String email) {
        String otp = String.format("%04d", new Random().nextInt(10000)); // Генерируем 4-х значный код
        otpStorage.put(email, otp);
        sendEmail(email, otp);

//        Запланированное удаление OTP через 2 минуты
        scheduler.schedule(() -> removeOTP(email), 2, TimeUnit.MINUTES);

        return otp;
    }

    private void removeOTP(String email) {
        otpStorage.remove(email);
        log.info("OTP для {} был удалён", email);
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("leon@leon-studio.ru");
        message.setSubject("Ваш код подтверждения ЛЕОН");
        message.setText("Ваш код подтверждения\nДействителен 2 минуты: " + otp);
        mailSender.send(message);
    }

    @Override
    public boolean validateOTP(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if (otp.equals(storedOtp)) otpStorage.remove(email);
        return otp.equals(storedOtp);
    }
}
