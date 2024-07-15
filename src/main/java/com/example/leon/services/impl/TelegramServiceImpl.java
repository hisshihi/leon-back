package com.example.leon.services.impl;

import com.example.leon.services.TelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final WebClient webClient = WebClient.create();
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.chat.id}")
    private String chatId;

    @Override
    public void sendMessage(String message) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);
        webClient
                .post()
                .uri(url)
                .bodyValue(new TelegramMessage(chatId, message))
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    private static class TelegramMessage {
        private final String chat_id;
        private final String text;

        public TelegramMessage(String chat_id, String text) {
            this.chat_id = chat_id;
            this.text = text;
        }

        public String getChat_id() {
            return chat_id;
        }

        public String getText() {
            return text;
        }
    }
}
