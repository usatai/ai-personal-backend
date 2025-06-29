package com.aipersonalbackend.aipersonalbackend.controller;

import java.util.List;

public record FoodListForm(
        String mealType,
        List<FoodDataForm> foods) {
}
