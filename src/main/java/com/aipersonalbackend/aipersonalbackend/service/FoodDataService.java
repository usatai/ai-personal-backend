package com.aipersonalbackend.aipersonalbackend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipersonalbackend.aipersonalbackend.controller.FoodDataForm;
import com.aipersonalbackend.aipersonalbackend.model.Food;
import com.aipersonalbackend.aipersonalbackend.repository.FoodDataRepository;

@Service
public class FoodDataService {

    @Autowired
    FoodDataRepository foodDataRepository;

    private Food createFoodData(FoodDataForm foodDataForm, Long userId, String mealType) {
        Food food = new Food();
        LocalDateTime now = LocalDateTime.now();

        food.setUserId(userId);
        food.setFoodName(foodDataForm.description());
        food.setCategory(mealType);
        food.setCalories(foodDataForm.calories());
        food.setProtein(foodDataForm.protein());
        food.setFat(foodDataForm.fat());
        food.setCarbohydrates(foodDataForm.carbohydrates());
        food.setCreatedAt(now);

        return food;
    }

    public void input(FoodDataForm foodDataForm, Long userId, String mealType) {
        foodDataRepository.save(createFoodData(foodDataForm, userId, mealType));
    }

}
