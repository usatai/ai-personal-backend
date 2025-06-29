package com.aipersonalbackend.aipersonalbackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aipersonalbackend.aipersonalbackend.service.UserInputService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : signupBindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            userInputService.userInput(signupForm);

            Long userId = userInputService.getUserId(signupForm);
            session.setAttribute("userId", userId);

            return ResponseEntity.ok(Map.of("message", "ユーザー登録成功", "userId", userId));
        }
    }

}
