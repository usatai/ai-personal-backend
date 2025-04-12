package com.ai_personal_backend.ai_personal_backend.controller;

import jakarta.validation.constraints.NotBlank;

public record SignupForm(
        @NotBlank(message = "ユーザー名が入力されていません") String user_name,
        @NotBlank(message = "メールアドレスが入力されていません") String user_email,
        @NotBlank(message = "パスワードが入力されていません") String user_password) {
}
