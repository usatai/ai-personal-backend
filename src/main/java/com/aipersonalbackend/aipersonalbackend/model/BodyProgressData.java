package com.aipersonalbackend.aipersonalbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "progress_body_data")
@Entity
@Data
public class BodyProgressData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "progress_weight")
    private float progressWeight;

    @Column(name = "progress_fat")
    private float progressFat;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
