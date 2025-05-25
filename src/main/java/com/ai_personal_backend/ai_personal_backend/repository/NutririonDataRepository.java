package com.ai_personal_backend.ai_personal_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai_personal_backend.ai_personal_backend.model.NutritionData;

public interface NutririonDataRepository extends JpaRepository<NutritionData,Long>{
    List<NutritionData> findByNameContaining(String query);
}
