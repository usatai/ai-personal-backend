package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;

@Repository
public interface BodyProgressDataRepository extends JpaRepository<BodyProgressData, Long> {

    @Query(value = "SELECT * FROM progress_body_data WHERE user_id = :userId AND created_at >= :firstDayOfMonth AND created_at < :lastDayOfMonth", nativeQuery = true)
    public List<BodyProgressData> findProgressData(@Param("userId") Long userId,
            @Param("firstDayOfMonth") LocalDateTime firstDayOfMonth,
            @Param("lastDayOfMonth") LocalDateTime lastDayOfMonth);
}
