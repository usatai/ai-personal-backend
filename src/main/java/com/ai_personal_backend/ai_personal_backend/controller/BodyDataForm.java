package com.ai_personal_backend.ai_personal_backend.controller;

import com.ai_personal_backend.ai_personal_backend.enumFile.UserGoalType;
import com.ai_personal_backend.ai_personal_backend.enumFile.UserSportType; // この行を追加

public record BodyDataForm(
        Float user_height,
        Float user_weight,
        Float user_fat,
        Float user_goal_weight,
        Float user_goal_fat,
        UserGoalType user_goal_Type,
        UserSportType user_sport_Type,
        String user_target_period) {
}
