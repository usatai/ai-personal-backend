package com.aipersonalbackend.aipersonalbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ai_response_data")
public class PfcData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "target_calorie")
    private float targetCalorie;

    @Column(name = "protein")
    private float protein;

    @Column(name = "fat")
    private float fat;

    @Column(name = "carbohydrate")
    private float carbohydrate;

    @Column(name = "ai_advice",columnDefinition = "TEXT")
    @Lob
    private String ai_advice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
