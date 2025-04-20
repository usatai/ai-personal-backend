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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/bodydata")
public class BodyDataController {

    @Autowired
    BodyDataInputService bodyDataInputService;

    @PostMapping("/input")
    public ResponseEntity<?> bodyDataInput(@RequestBody BodyDataForm bodyDataForm, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        // 体情報のDB保存
        bodyDataInputService.bodyDataSave(bodyDataForm, userId);

        // AIへのプロンプト作成
        String pronpt = generatePromptFromForm(bodyDataForm);

        return ResponseEntity.ok(Map.of("message", bodyDataForm));
    }

    private String generatePromptFromForm(BodyDataForm form) {
        return String.format("""
                あなたは優秀なパーソナルトレーナー兼管理栄養士です。
                以下のクライアント情報をもとに、具体的かつ実行可能な「1週間分の食事プラン」と「トレーニングメニュー概要」を日本語で提案してください。

                現在の身長: %scm
                現在の体重: %skg
                現在の体脂肪率: %s%%
                目標体重: %skg
                目標体脂肪率: %s%%
                目標タイプ: %s
                目標期間: %sヶ月以内
                """,
                form.user_height(),
                form.user_weight(),
                form.user_fat(),
                form.user_goal_weight(),
                form.user_goal_fat(),
                form.user_goal_Type(),
                form.user_target_period());
    }

    @GetMapping("userinfo")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

}
