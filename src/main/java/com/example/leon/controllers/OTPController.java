package com.example.leon.controllers;

import com.example.leon.services.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
@RequiredArgsConstructor
public class OTPController {

    private final OTPService otpService;

    @PostMapping("/send")
    private ResponseEntity<String> sendOTP(@RequestParam String email) {
        otpService.generateOTP(email);
        return ResponseEntity.ok("OTP отправлен на " + email);
    }

    @PostMapping("/validate")
    private ResponseEntity<Void> validateOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);
        if (isValid) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
