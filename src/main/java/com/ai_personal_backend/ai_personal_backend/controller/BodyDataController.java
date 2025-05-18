package com.ai_personal_backend.ai_personal_backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_personal_backend.ai_personal_backend.model.AiResponseData;
import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;
import com.ai_personal_backend.ai_personal_backend.model.Food;
import com.ai_personal_backend.ai_personal_backend.service.AiResponseDataService;
import com.ai_personal_backend.ai_personal_backend.service.BodyDataInputService;
import com.ai_personal_backend.ai_personal_backend.service.ChatGptService;
import com.ai_personal_backend.ai_personal_backend.service.PfcSaveService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/bodydata")
public class BodyDataController {

    @Autowired
    BodyDataInputService bodyDataInputService;

    @Autowired
    ChatGptService chatGptService;

    @Autowired
    PfcSaveService pfcSaveService;

    @Autowired
    AiResponseDataService aiResponseDataService;

    @PostMapping("/input")
    public ResponseEntity<?> bodyDataInput(@RequestBody BodyDataForm bodyDataForm, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        // AIへのプロンプト作成
        String pronpt = generatePromptFromForm(bodyDataForm);

        // ChatGPTへの問い合わせ
        String aiAdvice = chatGptService.ask(pronpt);

        //PFC、カロリー抽出
        PfcForm pfcForm = PfcParser.parse(aiAdvice);

        // 体情報のDB保存
        bodyDataInputService.bodyDataSave(bodyDataForm, userId);

        // カロリー、PFC保存
        pfcSaveService.savePfc(pfcForm,aiAdvice,userId);

        return ResponseEntity.ok(Map.of("message", bodyDataForm,"aiAdvice", aiAdvice));
    }

    private String generatePromptFromForm(BodyDataForm form) {
        return String.format("""
                あなたは経験豊富なパーソナルトレーナーかつ管理栄養士です。
                以下のクライアント情報をもとに、目標達成に向けて実行可能で現実的な「1ヶ月分の食事プラン」と「トレーニングメニュー概要」を日本語で詳細に提案してください。

                提案の際は、以下の点に特に留意してください:
                - 日本人の平均的な食生活・生活リズムを考慮すること
                - クライアントの目標タイプに合わせて、減量・健康・増量のいずれかに適切なカロリー収支とPFCバランスを設計すること
                - 食事は家庭でも再現可能な簡単な献立にすること（市販食品も可）
                - トレーニングはジム未利用であれば自重中心、自宅でできるよう工夫すること
                - PFCの単位はgで、数値も明記すること
                - 朝/昼/夕/間食の例は複数日分（例: 各3日分）提示すると望ましい
                - クライアントの運動タイプによってトレーニングメニューを設計、提案すること
                - トレーニングメニューは1週間で見て休養日も含めた設計にしてください

                ▼クライアント情報
                - 現在の身長: %scm
                - 現在の体重: %skg
                - 現在の体脂肪率: %s%%
                - 目標体重: %skg
                - 目標体脂肪率: %s%%
                - 目標タイプ: %s
                - 運動タイプ: %s
                - 目標期間: %s以内

                出力フォーマットは以下を厳守してください:
                ---
                【食事プラン】
                ・1日の目標摂取カロリー:
                ・目標PFCバランス（g）: タンパク質〇g / 脂質〇g / 炭水化物〇g
                ・朝食例（複数日）:
                ・昼食例（複数日）:
                ・夕食例（複数日）:
                ・間食例（任意）:

                【トレーニング概要】
                ・月:
                ・火:
                ・水:
                ・木:
                ・金:
                ・土:
                ・日:
                ・主なトレーニングメニュー（部位ごとに分類可）:
                ・備考（トレーニングの注意点、休養日、プロテインなどのサプリ活用など）:
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
    public ResponseEntity<?> getMethodName(@RequestBody @Valid BodyProgressDataForm bodyProgressDataForm,BindingResult bodyBindingResult,
            HttpSession session) {
        
        if (bodyBindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();
            for (FieldError fieldError : bodyBindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);

        } else {
            Long userId = (Long) session.getAttribute("userId");

            bodyDataInputService.progressDataInput(bodyProgressDataForm, userId);

            return ResponseEntity.ok(Map.of("message", "登録成功"));
        }
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

        // AIアドバイス取得
        AiResponseData aiResponseData = aiResponseDataService.getAiResponseDataByUserId(userId);
        String aiAdvice = aiResponseData.getAiAdvice();
        float TargetCalories = aiResponseData.getTargetCalorie();

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
                    "caloriesBurned", TargetCalories// 仮に固定値。あとで活動量計算できる
            ));
        }

        return ResponseEntity.ok(Map.of("todayMonth", todayMonth,"aiAdvice",aiAdvice));
    }
}
