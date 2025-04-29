package com.ai_personal_backend.ai_personal_backend.repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai_personal_backend.ai_personal_backend.model.Food;

@Repository
public interface FoodDataRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT * FROM food_items WHERE user_id = :userId AND DATE(created_at) = :ym", nativeQuery = true)
    public List<Food> findFoodData(@Param("userId") Long userId, @Param("ym") YearMonth ym);
}
