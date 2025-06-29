package com.aipersonalbackend.aipersonalbackend.controller;

import com.aipersonalbackend.aipersonalbackend.validation.UserLogin;

import jakarta.validation.constraints.NotBlank;

@UserLogin
public record LoginForm(
        @NotBlank(message = "ユーザー名を入力してください")
        String userName,
        @NotBlank(message = "パスワードを入力してください")
        String userPassword) {
}
