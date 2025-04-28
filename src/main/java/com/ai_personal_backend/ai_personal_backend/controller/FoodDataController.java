package com.ai_personal_backend.ai_personal_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_personal_backend.ai_personal_backend.service.FoodDataService;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/fooddata")
public class FoodDataController {

    @Autowired
    FoodDataService foodDataService;

    @PostMapping("/input")
    public ResponseEntity<?> inputFoodData(@RequestBody FoodListForm foodListForm, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        for (FoodDataForm food : foodListForm.foods()) {
            foodDataService.input(food, userId, foodListForm.mealType());
        }

        return ResponseEntity.ok(Map.of("message", "成功"));
    }

}
