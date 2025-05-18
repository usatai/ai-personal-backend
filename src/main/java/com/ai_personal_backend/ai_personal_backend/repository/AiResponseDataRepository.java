package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai_personal_backend.ai_personal_backend.model.AiResponseData;

@Repository
public interface AiResponseDataRepository extends JpaRepository<AiResponseData, Long> {
    AiResponseData findByUserId(Long userId);
} 