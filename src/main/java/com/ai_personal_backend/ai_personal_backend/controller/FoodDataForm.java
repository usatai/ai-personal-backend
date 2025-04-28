package com.ai_personal_backend.ai_personal_backend.controller;

public record FoodDataForm(
        String description,
        float calories,
        float protein,
        float fat,
        float carbohydrates) {
}
