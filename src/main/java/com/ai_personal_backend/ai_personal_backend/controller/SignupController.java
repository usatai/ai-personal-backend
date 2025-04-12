package com.ai_personal_backend.ai_personal_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class SignupController {

    @PostMapping("/signup")
    public ResponseEntity<?> signuuUser(@RequestBody @Valid SignupForm signupForm) {
        return ResponseEntity.ok(Map.of("message", "ユーザー登録成功"));
    }

}
