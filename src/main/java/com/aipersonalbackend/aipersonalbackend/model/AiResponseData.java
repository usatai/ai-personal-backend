package com.aipersonalbackend.aipersonalbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ai_response_data")
public class AiResponseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private Float protein;

    @Column
    private Float fat;

    @Column
    private Float carbohydrate;

    @Column(name = "ai_advice",columnDefinition = "TEXT")
    private String aiAdvice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "target_calorie")
    private Float targetCalorie;
} 