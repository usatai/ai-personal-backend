package com.aipersonalbackend.aipersonalbackend.controller;

import com.aipersonalbackend.aipersonalbackend.enumFile.UserGoalType;
import com.aipersonalbackend.aipersonalbackend.enumFile.UserSportType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BodyDataForm(
        @NotNull(message = "身長を入力してください")
        Float user_height,
        @NotNull(message = "体重を入力してください")
        Float user_weight,
        @NotNull(message = "体脂肪を入力してください")
        Float user_fat,
        @NotNull(message = "目標体重を入力してください")
        Float user_goal_weight,
        @NotNull(message = "目標体脂肪を入力してください")
        Float user_goal_fat,
        @NotNull(message = "目標タイプを選択してください")
        UserGoalType user_goal_type,
        @NotNull(message = "運動タイプを選択してください")
        UserSportType user_sport_type,
        @NotBlank(message = "目標の期間を入力してください")
        String user_target_period) {
}
