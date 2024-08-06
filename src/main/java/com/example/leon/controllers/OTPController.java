package com.example.leon.controllers;

import com.example.leon.services.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
@RequiredArgsConstructor
@Slf4j
public class OTPController {

    private final OTPService otpService;

    @PostMapping("/send")
    private ResponseEntity<String> sendOTP(@RequestParam String phone) {
        otpService.generateOTP(phone);
        log.info("OTP отправлен на " + phone);
        return ResponseEntity.ok("OTP отправлен на " + phone);
    }

    @PostMapping("/validate")
    private ResponseEntity<Void> validateOTP(@RequestParam String phone, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(phone, otp);
        if (isValid) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
