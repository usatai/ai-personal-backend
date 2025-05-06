package com.ai_personal_backend.ai_personal_backend.controller;

import jakarta.validation.constraints.NotNull;

public record BodyProgressDataForm(
        @NotNull(message = "体重を入力してください")
        Float progressWeight,
        @NotNull(message = "体脂肪を入力してください")
        Float progressFat) {
}
