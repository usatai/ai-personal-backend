package com.aipersonalbackend.aipersonalbackend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aipersonalbackend.aipersonalbackend.model.Food;

@Repository
public interface FoodDataRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT * FROM food_items WHERE user_id = :userId AND created_at >= :firstDayOfMonth AND created_at < :lastDayOfMonth", nativeQuery = true)
    public List<Food> findFoodData(@Param("userId") Long userId,
            @Param("firstDayOfMonth") LocalDateTime firstDayOfMonth,
            @Param("lastDayOfMonth") LocalDateTime lastDayOfMonth);
}
