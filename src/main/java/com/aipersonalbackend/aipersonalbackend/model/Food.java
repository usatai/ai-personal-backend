package com.aipersonalbackend.aipersonalbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "food_items")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "category")
    private String category;

    @Column(name = "calories")
    private float calories;

    @Column(name = "protein")
    private float protein;

    @Column(name = "fat")
    private float fat;

    @Column(name = "carbohydrates")
    private float carbohydrates;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
