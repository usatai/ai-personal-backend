package com.aipersonalbackend.aipersonalbackend.controller;

public record FoodDataForm(
        String description,
        float calories,
        float protein,
        float fat,
        float carbohydrates) {
}
