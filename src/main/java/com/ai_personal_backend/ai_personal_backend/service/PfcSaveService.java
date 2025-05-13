package com.ai_personal_backend.ai_personal_backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.PfcForm;
import com.ai_personal_backend.ai_personal_backend.model.PfcData;
import com.ai_personal_backend.ai_personal_backend.repository.PfcRepository;

@Service
public class PfcSaveService {

    @Autowired
    private PfcRepository pfcRepository;

    public void savePfc(PfcForm data,String aiAdvice, Long userId) {
        PfcData pfcData = new PfcData();
        pfcData.setUserId(userId);
        pfcData.setTargetCalorie(data.getTargetCalorie());
        pfcData.setProtein(data.getProtein());
        pfcData.setFat(data.getFat());
        pfcData.setCarbohydrate(data.getCarbohydrate());
        pfcData.setAi_advice(aiAdvice);
        pfcData.setCreatedAt(LocalDateTime.now());
        pfcRepository.save(pfcData);
    }
}
