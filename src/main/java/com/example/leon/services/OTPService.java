package com.example.leon.services;

public interface OTPService {

    String generateOTP(String email);
    boolean validateOTP(String email, String otp);

}
