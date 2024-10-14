package com.example.leon.exceptions;

public class PhoneInBlackListException extends RuntimeException {
    public PhoneInBlackListException(String message) {
        super(message);
    }
}
