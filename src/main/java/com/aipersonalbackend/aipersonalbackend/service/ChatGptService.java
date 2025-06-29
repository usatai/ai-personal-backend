package com.aipersonalbackend.aipersonalbackend.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

@Service
public class ChatGptService {

    private final WebClient webClient;

    public ChatGptService(WebClient.Builder builder,
            @Value("${openai.api.key}") String apiKey) {

        
        // Reactor Netty HttpClient の設定
        HttpClient httpClient = HttpClient.create()
                // 接続タイムアウトを設定 (サーバーへの接続を確立するまでの時間)
                // 例: 5秒。通常は短めに設定します。
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // 応答タイムアウトを設定 (接続後、サーバーからの応答が完了するまでの最大待ち時間)
                // 元のコードは60秒設定でタイムアウトするとのことなので、少し長めに設定してみます。
                // APIの応答時間によっては更に長くする必要があるかもしれません。
                .responseTimeout(Duration.ofSeconds(90)); // 例: 90秒に延長

                
        this.webClient = builder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .clientConnector(
                    new ReactorClientHttpConnector(httpClient)
                )
                .build();
    }

    public String ask(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o",
                "messages", List.of(
                        Map.of("role", "system", "content", "あなたは優秀なフィットネストレーナー兼管理栄養士です。"),
                        Map.of("role", "user", "content", prompt)),
                "temperature", 0.7);

        try {
            return webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(OpenAIResponseDTO.class)
                    .map(response -> response.choices.get(0).message.content)
                    .block(); // 同期処理（非同期にする場合は .subscribe()）
        } catch (WebClientResponseException e) {
            return "OpenAI APIエラー: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "OpenAIとの通信中にエラーが発生しました: " + e.getMessage();
        }
    }
}