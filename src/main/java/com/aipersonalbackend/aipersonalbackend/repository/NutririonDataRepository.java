package com.aipersonalbackend.aipersonalbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipersonalbackend.aipersonalbackend.model.NutritionData;

@Repository
public interface NutririonDataRepository extends JpaRepository<NutritionData,Long>{
    List<NutritionData> findByFoodNameContaining(String query);
}
