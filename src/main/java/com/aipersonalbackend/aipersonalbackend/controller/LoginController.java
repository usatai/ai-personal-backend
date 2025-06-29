package com.aipersonalbackend.aipersonalbackend.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aipersonalbackend.aipersonalbackend.service.UserLoginService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginForm loginForm,BindingResult loginBindingResult, HttpSession session) {
        if (loginBindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : loginBindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            Long userId = userLoginService.login(loginForm);
            LocalDateTime createdAt = userLoginService.getCreatedAt(userId);
            session.setAttribute("userId", userId);
            session.setAttribute("createdAt", createdAt);
            return ResponseEntity.ok(Map.of("message", "ログイン成功", "userId", userId, "createdAt", createdAt));
        }

    }

}
