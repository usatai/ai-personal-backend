package com.aipersonalbackend.aipersonalbackend.repository;

import org.springframework.stereotype.Repository;

import com.aipersonalbackend.aipersonalbackend.model.BodyData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BodyDataInputRepository extends JpaRepository<BodyData, Long> {

    @Query("SELECT b.goal_weight FROM BodyData b WHERE b.user_id = :userId ORDER BY b.created_at DESC LIMIT 1")
    public Float findByUserWeight(@Param("userId")Long userId);

}
