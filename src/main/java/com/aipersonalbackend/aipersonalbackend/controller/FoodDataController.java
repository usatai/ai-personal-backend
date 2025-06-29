package com.aipersonalbackend.aipersonalbackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aipersonalbackend.aipersonalbackend.model.NutritionData;
import com.aipersonalbackend.aipersonalbackend.repository.NutririonDataRepository;
import com.aipersonalbackend.aipersonalbackend.service.FoodDataService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/fooddata")
public class FoodDataController {

    @Autowired
    FoodDataService foodDataService;

    @Autowired
    NutririonDataRepository nutririonDataRepository;

    @PostMapping("/input")
    public ResponseEntity<?> inputFoodData(@RequestBody FoodListForm foodListForm, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        for (FoodDataForm food : foodListForm.foods()) {
            foodDataService.input(food, userId, foodListForm.mealType());
        }
        
        return ResponseEntity.ok(Map.of("message", "成功"));
    }

    @GetMapping("/foodlist")
    public List<NutritionData> getFoodList(@RequestParam(name = "query") String param) {
        return nutririonDataRepository.findByFoodNameContaining(param);
    }
    

}
