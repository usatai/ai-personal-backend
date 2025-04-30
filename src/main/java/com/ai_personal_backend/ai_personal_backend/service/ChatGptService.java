package com.ai_personal_backend.ai_personal_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ChatGptService {

    private final WebClient webClient;

    public ChatGptService(WebClient.Builder builder,
            @Value("${openai.api.key}") String apiKey) {
        this.webClient = builder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer" + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String ask(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "あなたは優秀なフィットネストレーナー兼管理栄養士です。"),
                        Map.of("role", "user", "content", prompt)),
                "temperature", 0.7);

        try {
            return webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> extractMessage(response))
                    .block(); // 同期処理（非同期にする場合は .subscribe()）
        } catch (WebClientResponseException e) {
            return "OpenAI APIエラー: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "OpenAIとの通信中にエラーが発生しました: " + e.getMessage();
        }
    }

    private String extractMessage(Map<String, Object> response) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        }
        return "アドバイスを取得できませんでした。";
    }
}