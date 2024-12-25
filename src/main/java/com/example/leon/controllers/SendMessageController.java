package com.example.leon.controllers;

import com.example.leon.services.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-message")
@RequiredArgsConstructor
public class SendMessageController {

    private final TelegramService telegramService;

    @GetMapping
    public ResponseEntity<?> sendMessage() {
        String message = "Ведутся технические работы, сайт будет недоступен в течении нескольких часов";
        telegramService.sendMessage(message);
        return ResponseEntity.ok().build();
    }

}
