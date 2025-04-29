package com.ai_personal_backend.ai_personal_backend.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.BodyDataForm;
import com.ai_personal_backend.ai_personal_backend.controller.BodyProgressDataForm;
import com.ai_personal_backend.ai_personal_backend.model.BodyData;
import com.ai_personal_backend.ai_personal_backend.model.Food;
import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;
import com.ai_personal_backend.ai_personal_backend.repository.BodyDataInputRepository;
import com.ai_personal_backend.ai_personal_backend.repository.BodyProgressDataRepository;
import com.ai_personal_backend.ai_personal_backend.repository.FoodDataRepository;

@Service
public class BodyDataInputService {

    @Autowired
    BodyDataInputRepository bodyDataRepository;

    @Autowired
    BodyProgressDataRepository bodyProgressDataRepository;

    @Autowired
    FoodDataRepository foodDataRepository;

    private BodyData bodyDataCreate(BodyDataForm bodyDataForm, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        BodyData bodyData = new BodyData();
        bodyData.setUser_id(userId);
        bodyData.setHeight(bodyDataForm.user_height());
        bodyData.setWeight(bodyDataForm.user_weight());
        bodyData.setBody_fat(bodyDataForm.user_fat());
        bodyData.setGoal_weight(bodyDataForm.user_goal_weight());
        bodyData.setGoal_body_fat(bodyDataForm.user_goal_fat());
        bodyData.setGoal_type(bodyDataForm.user_goal_Type());
        bodyData.setTarget_period(bodyDataForm.user_target_period());
        bodyData.setCreated_at(now);

        return bodyData;
    }

    private BodyProgressData progressBodyDataCreate(BodyProgressDataForm bodyProgressDataForm, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        BodyProgressData bodyProgressData = new BodyProgressData();
        bodyProgressData.setUserId(userId);
        bodyProgressData.setProgressWeight(bodyProgressDataForm.progressWeight());
        bodyProgressData.setProgressFat(bodyProgressDataForm.progressFat());
        bodyProgressData.setCreatedAt(now);

        return bodyProgressData;
    }

    // 体データを保存するメソッド
    public void bodyDataSave(BodyDataForm bodyDataForm, Long userId) {
        bodyDataRepository.save(bodyDataCreate(bodyDataForm, userId));
    }

    // 体データの体重、体脂肪の進捗を保存するメソッド
    public void progressDataInput(BodyProgressDataForm bodyProgressDataForm, Long userId) {
        bodyProgressDataRepository.save(progressBodyDataCreate(bodyProgressDataForm, userId));
    }

    // 今月今週の体データを取得するメソッド
    public List<BodyProgressData> getProgressData(Long userId, LocalDateTime firstDayOfMonth,
            LocalDateTime lastDayOfMonth) {

        return bodyProgressDataRepository.findProgressData(userId, firstDayOfMonth, lastDayOfMonth);

    }

    // public Optional<Float> getFoodData(Long userId, LocalDateTime now) {

    // List<Food> food = foodDataRepository.findFoodData(userId, now);

    // if (food != null && !food.isEmpty()) {
    // float totalCalories = (float) food.stream()
    // .mapToDouble(data -> data.getCalories())
    // .sum();
    // return Optional.of(totalCalories);
    // } else {
    // return Optional.empty();
    // }

    // }

    public List<Food> getFoodDataForMonth(Long userId, YearMonth ym) {
        return foodDataRepository.findFoodData(userId, ym);
    }

}
