package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.stereotype.Repository;
import com.ai_personal_backend.ai_personal_backend.model.BodyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BodyDataInputRepository extends JpaRepository<BodyData, Long> {

    @Query("SELECT b.goal_weight FROM BodyData b WHERE b.user_id = :userId ORDER BY b.created_at DESC LIMIT 1")
    public Float findByUserWeight(@Param("userId")Long userId);

}
