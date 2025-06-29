package com.aipersonalbackend.aipersonalbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "nutrition_data")
@Data
public class NutritionData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "食品名")
    private String foodName;

    @Column(name = "エネルギー")
    private float calories;

    @Column(name = "たんぱく質")
    private float protein;

    @Column(name = "脂質")
    private float fat;

    @Column(name = "炭水化物")
    private float carbohydrates;
    
}
