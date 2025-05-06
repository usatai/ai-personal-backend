package com.ai_personal_backend.ai_personal_backend.controller;

import com.ai_personal_backend.ai_personal_backend.validation.UserDuplication;

import jakarta.validation.constraints.NotBlank;

public record SignupForm(
        @NotBlank(message = "ユーザー名が入力されていません")
        @UserDuplication
        String userName,
        @NotBlank(message = "メールアドレスが入力されていません") 
        String userEmail,
        @NotBlank(message = "パスワードが入力されていません") 
        String userPassword) {
}
