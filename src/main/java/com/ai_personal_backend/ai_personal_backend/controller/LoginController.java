package com.ai_personal_backend.ai_personal_backend.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_personal_backend.ai_personal_backend.service.UserLoginService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginForm loginForm, HttpSession session) {
        try {
            Long userId = userLoginService.login(loginForm);
            LocalDateTime createdAt = userLoginService.getCreatedAt(userId);
            session.setAttribute("userId", userId);
            session.setAttribute("createdAt", createdAt);
            return ResponseEntity.ok(Map.of("message", "ログイン成功", "userId", userId, "createdAt", createdAt));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "ログイン失敗"));
        }

    }

}
