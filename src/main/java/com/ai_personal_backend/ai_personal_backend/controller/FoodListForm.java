package com.ai_personal_backend.ai_personal_backend.controller;

import java.util.List;

public record FoodListForm(
        String mealType,
        List<FoodDataForm> foods) {
}
