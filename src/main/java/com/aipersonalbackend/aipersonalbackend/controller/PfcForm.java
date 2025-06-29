package com.aipersonalbackend.aipersonalbackend.controller;

import lombok.Data;

@Data
public class PfcForm {

    private int targetCalorie;
    private float protein;
    private float fat;
    private float carbohydrate;

    public PfcForm(int targetCalorie, float protein, float fat, float carbohydrate) {
        this.targetCalorie = targetCalorie;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

}
