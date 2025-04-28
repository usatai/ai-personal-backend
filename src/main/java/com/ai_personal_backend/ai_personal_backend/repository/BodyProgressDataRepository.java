package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;

@Repository
public interface BodyProgressDataRepository extends JpaRepository<BodyProgressData, Long> {

    @Query(value = "SELECT * FROM progress_body_data WHERE user_id = :userId AND DATE(created_at) = DATE(:now)", nativeQuery = true)
    public BodyProgressData findProgressData(@Param("userId") Long userId, @Param("now") LocalDateTime now);

}
