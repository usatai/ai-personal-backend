package com.aipersonalbackend.aipersonalbackend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipersonalbackend.aipersonalbackend.controller.BodyDataForm;
import com.aipersonalbackend.aipersonalbackend.controller.BodyProgressDataForm;
import com.aipersonalbackend.aipersonalbackend.model.BodyData;
import com.aipersonalbackend.aipersonalbackend.model.BodyProgressData;
import com.aipersonalbackend.aipersonalbackend.model.Food;
import com.aipersonalbackend.aipersonalbackend.repository.BodyDataInputRepository;
import com.aipersonalbackend.aipersonalbackend.repository.BodyProgressDataRepository;
import com.aipersonalbackend.aipersonalbackend.repository.FoodDataRepository;

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
        bodyData.setGoal_type(bodyDataForm.user_goal_type());
        bodyData.setSportType(bodyDataForm.user_sport_type());
        bodyData.setTarget_period(bodyDataForm.user_target_period());
        bodyData.setCreated_at(now);

        return bodyData;
    }

    private BodyProgressData progressBodyDataCreate(BodyProgressDataForm bodyProgressDataForm, Long userId) {
        // LocalDateTime now = LocalDateTime.now();

        BodyProgressData bodyProgressData = new BodyProgressData();
        bodyProgressData.setUserId(userId);
        bodyProgressData.setProgressWeight(bodyProgressDataForm.progressWeight());
        bodyProgressData.setProgressFat(bodyProgressDataForm.progressFat());
        bodyProgressData.setCreatedAt(bodyProgressDataForm.createdAt());

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

    public List<Food> getFoodDataForMonth(Long userId, LocalDateTime firstDayOfMonth,
            LocalDateTime lastDayOfMonth) {
        return foodDataRepository.findFoodData(userId, firstDayOfMonth, lastDayOfMonth);
    }

    public float getTargetWeight(Long userId) {
        return bodyDataRepository.findByUserWeight(userId);
    }

}
