package com.ai_personal_backend.ai_personal_backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;
import com.ai_personal_backend.ai_personal_backend.model.Food;
import com.ai_personal_backend.ai_personal_backend.service.BodyDataInputService;
import com.ai_personal_backend.ai_personal_backend.service.ChatGptService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/bodydata")
public class BodyDataController {

    @Autowired
    BodyDataInputService bodyDataInputService;

    @Autowired
    ChatGptService chatGptService;

    @PostMapping("/input")
    public ResponseEntity<?> bodyDataInput(@RequestBody BodyDataForm bodyDataForm, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        // AIへのプロンプト作成
        // String pronpt = generatePromptFromForm(bodyDataForm);
        // String aiAdvice = chatGptService.ask(pronpt);

        // 体情報のDB保存
        bodyDataInputService.bodyDataSave(bodyDataForm, userId);

        return ResponseEntity.ok(Map.of("message", bodyDataForm));
    }

    private String generatePromptFromForm(BodyDataForm form) {
        return String.format("""
                あなたは優秀なパーソナルトレーナー兼管理栄養士です。
                以下のクライアント情報をもとに、具体的かつ実行可能な「1ヶ月分の食事プラン」と「トレーニングメニュー概要」を日本語で提案してください。

                ▼クライアント情報
                - 現在の身長: %scm
                - 現在の体重: %skg
                - 現在の体脂肪率: %s%%
                - 目標体重: %skg
                - 目標体脂肪率: %s%%
                - 目標タイプ: %s
                - 運動タイプ: $s
                - 目標期間: %以内

                出力形式は以下のようにしてください:
                ---
                【食事プラン】
                ・1日の目標摂取カロリー及び目標PFCバランス
                ・朝食:
                ・昼食:
                ・夕食:
                ・間食（任意）:

                【トレーニング概要】
                ・頻度（週◯回）:
                ・主な種目:
                ・筋トレ／有酸素の比率:
                ・備考（フォーム、休養の取り方、栄養補助食品など）:
                ---
                """,
                form.user_height(),
                form.user_weight(),
                form.user_fat(),
                form.user_goal_weight(),
                form.user_goal_fat(),
                form.user_goal_Type(),
                form.user_sport_Type(),
                form.user_target_period());
    }

    @PostMapping("progress")
    public ResponseEntity<?> getMethodName(@RequestBody BodyProgressDataForm bodyProgressDataForm,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        bodyDataInputService.progressDataInput(bodyProgressDataForm, userId);

        return ResponseEntity.ok(Map.of("message", "登録成功"));

    }

    @GetMapping("userinfo")
    public ResponseEntity<?> userInfo(@RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month, HttpSession session) {

        YearMonth ym;

        if (year != null && month != null) {
            ym = YearMonth.of(year, month);
        } else {
            ym = YearMonth.now(); // デフォルトは今月
        }

        LocalDateTime firstDayOfMonth = ym.atDay(1).atStartOfDay();
        LocalDateTime lastDayOfMonth = ym.atEndOfMonth().plusDays(1).atStartOfDay();

        // 体重・体脂肪取得
        List<BodyProgressData> progress = bodyDataInputService.getProgressData(userId, firstDayOfMonth, lastDayOfMonth);
        List<Food> foodData = bodyDataInputService.getFoodDataForMonth(userId, firstDayOfMonth, lastDayOfMonth);

        List<Map<String, Object>> todayMonth = new ArrayList<>();
        for (LocalDate date = ym.atDay(1); !date.isAfter(ym.atEndOfMonth()); date = date.plusDays(1)) {
            final LocalDate copy = date;
            Float weight = progress.stream()
                    .filter(d -> d.getCreatedAt().toLocalDate().equals(copy))
                    .max((d1, d2) -> d1.getCreatedAt().compareTo(d2.getCreatedAt()))
                    .map(d -> d.getProgressWeight())
                    .orElse(0f);

            Float fat = progress.stream()
                    .filter(d -> d.getCreatedAt().toLocalDate().equals(copy))
                    .max((d1, d2) -> d1.getCreatedAt().compareTo(d2.getCreatedAt()))
                    .map(d -> d.getProgressFat())
                    .orElse(0f);

            Float calories = foodData.stream()
                    .filter(d -> d.getCreatedAt().toLocalDate().equals(copy))
                    .map(d -> d.getCalories())
                    .reduce(0f, Float::sum);

            todayMonth.add(Map.of(
                    "name", date.toString(),
                    "weight", weight,
                    "fat", fat,
                    "caloriesIntake", calories,
                    "caloriesBurned", 2200 // 仮に固定値。あとで活動量計算できる
            ));
        }

        return ResponseEntity.ok(Map.of("todayMonth", todayMonth));
    }
}
