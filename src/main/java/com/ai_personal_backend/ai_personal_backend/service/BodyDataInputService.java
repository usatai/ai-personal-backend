package com.ai_personal_backend.ai_personal_backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.BodyDataForm;
import com.ai_personal_backend.ai_personal_backend.controller.BodyProgressDataForm;
import com.ai_personal_backend.ai_personal_backend.model.BodyData;
import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;
import com.ai_personal_backend.ai_personal_backend.repository.BodyDataInputRepository;
import com.ai_personal_backend.ai_personal_backend.repository.BodyProgressDataRepository;

@Service
public class BodyDataInputService {

    @Autowired
    BodyDataInputRepository bodyDataRepository;

    @Autowired
    BodyProgressDataRepository bodyProgressDataRepository;

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

    public void progressDataInput(BodyProgressDataForm bodyProgressDataForm, Long userId) {
        bodyProgressDataRepository.save(progressBodyDataCreate(bodyProgressDataForm, userId));
    }

}
