package com.ai_personal_backend.ai_personal_backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bodydata")
public class BodyDataController {

    @PostMapping("/input")
    public ResponseEntity<?> bodyDataInput(@RequestBody BodyDataForm bodyDataForm) {
        return ResponseEntity.ok(Map.of("message", bodyDataForm));
    }
}
