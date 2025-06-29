package com.aipersonalbackend.aipersonalbackend.controller;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record BodyProgressDataForm(
        LocalDateTime createdAt,
        @NotNull(message = "体重を入力してください")
        Float progressWeight,
        @NotNull(message = "体脂肪を入力してください")
        Float progressFat) {
}
