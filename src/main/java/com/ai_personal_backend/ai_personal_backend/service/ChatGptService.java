package com.ai_personal_backend.ai_personal_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ChatGptService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public ChatGptService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.openai.com/v1/chat/completions").build();
    }

}
