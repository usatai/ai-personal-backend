package com.ai_personal_backend.ai_personal_backend.controller;

import com.ai_personal_backend.ai_personal_backend.validation.UserLogin;

import jakarta.validation.constraints.NotBlank;

@UserLogin
public record LoginForm(
        @NotBlank(message = "ユーザー名を入力してください")
        String userName,
        @NotBlank(message = "パスワードを入力してください")
        String userPassword) {
}
