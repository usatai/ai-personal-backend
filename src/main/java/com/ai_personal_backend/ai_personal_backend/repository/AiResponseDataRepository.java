package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai_personal_backend.ai_personal_backend.model.AiResponseData;
import java.util.Optional;

@Repository
public interface AiResponseDataRepository extends JpaRepository<AiResponseData, Long> {
    AiResponseData findByUserId(Long userId);

    @Query("SELECT a FROM AiResponseData a WHERE a.userId = :userId ORDER BY a.createdAt DESC LIMIT 1")
    Optional<AiResponseData> findFirstByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
} 