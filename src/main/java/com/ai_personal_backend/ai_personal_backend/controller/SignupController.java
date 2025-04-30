package com.ai_personal_backend.ai_personal_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_personal_backend.ai_personal_backend.service.UserInputService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class SignupController {

    @Autowired
    UserInputService userInputService;

    @PostMapping("/signup")
    public ResponseEntity<?> signuuUser(@RequestBody @Valid SignupForm signupForm, BindingResult signupBindingResult,
            HttpSession session) {
        if (signupBindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(signupBindingResult.getAllErrors());
        } else {
            userInputService.userInput(signupForm);

            Long userId = userInputService.getUserId(signupForm);
            session.setAttribute("userId", userId);

            return ResponseEntity.ok(Map.of("message", "ユーザー登録成功", "userId", userId));
        }
    }

}
