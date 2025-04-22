package com.ai_personal_backend.ai_personal_backend.service;

import java.util.List;
import java.util.Map;

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

    public String ask(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "あなたは優秀なフィットネストレーナーです。"),
                        Map.of("role", "user", "content", prompt)),
                "temperature", 0.7);

        return webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return (String) message.get("content");
                    }
                    return "アドバイスを取得できませんでした。";
                })
                .block(); // 同期処理で返す（非同期化も可）
    }
}
