package com.ai_personal_backend.ai_personal_backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_personal_backend.ai_personal_backend.service.BodyDataInputService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bodydata")
public class BodyDataController {

    @Autowired
    BodyDataInputService bodyDataInputService;

    @PostMapping("/input")
    public ResponseEntity<?> bodyDataInput(@RequestBody BodyDataForm bodyDataForm, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        bodyDataInputService.bodyDataSave(bodyDataForm, userId);

        return ResponseEntity.ok(Map.of("message", bodyDataForm));
    }
}
