package com.aipersonalbackend.aipersonalbackend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aipersonalbackend.aipersonalbackend.model.AiResponseData;
import com.aipersonalbackend.aipersonalbackend.model.BodyProgressData;
import com.aipersonalbackend.aipersonalbackend.model.Food;
import com.aipersonalbackend.aipersonalbackend.service.AiResponseDataService;
import com.aipersonalbackend.aipersonalbackend.service.BodyDataInputService;
import com.aipersonalbackend.aipersonalbackend.service.ChatGptService;
import com.aipersonalbackend.aipersonalbackend.service.PfcSaveService;

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
    public ResponseEntity<?> bodyDataInput(@RequestBody @Valid BodyDataForm bodyDataForm,BindingResult bodyDataBindingResult,HttpSession session) {
        if (bodyDataBindingResult.hasErrors()) {
            System.out.println(bodyDataForm);
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bodyDataBindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
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
    }

    private String generatePromptFromForm(BodyDataForm form) {
        return String.format("""
                あなたは経験豊富なパーソナルトレーナーであり、国家資格を持つ管理栄養士でもあります。
                以下の詳細なクライアント情報をもとに、「現実的かつ実行可能な」 1ヶ月間の食事プランとトレーニングメニュー概要を日本語で具体的かつ詳細に提案してください。

                提案の際は、以下の点に特に留意してください:
                ▼指示・前提条件
                    日本人の平均的な生活リズム（会社員を想定。起床7時、昼食12時、夕食19時など）と食文化（和食・コンビニ食など）を考慮してください。
                    目標タイプ（減量 / 健康維持 / 増量）に応じて、1日の摂取カロリーとPFCバランス（g）を数値で明記し、科学的根拠（軽くで良い）に基づいて設計してください。
                    食事内容は家庭でも再現可能な簡単な献立とし、市販食品（コンビニ・冷凍食品・缶詰など）も活用可能としてください。
                    各食事（朝/昼/夜/間食）の例は、3日分ずつ以上のバリエーションを具体的に提示してください。PFCの単位はgで、数値も明記すること

                ▼トレーニングについては以下の点に注意してください:
                    クライアントの運動タイプ（ジム / 自宅）に合わせて内容を調整
                    週単位でトレーニング日・休養日を明示し、1週間のスケジュールとして提示
                    部位別メニューや種目（例：スクワット、プランクなど）を具体的に記載
                    自重トレ・チューブ・軽いダンベルなど家庭でも使える器具を活用可

                補足事項として、食事制限のコツ・空腹対策・プロテイン活用・サプリの使い方・継続の工夫などの助言を含めてください。

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
                ・朝食例（3日分以上）:
                - 例1:
                - 例2:
                - 例3:

                ・昼食例（3日分以上）:
                - 例1:
                - 例2:
                - 例3:

                ・夕食例（3日分以上）:
                - 例1:
                - 例2:
                - 例3:

                ・間食例（任意・推奨される場合）:
                - 例1:
                - 例2:

                【トレーニング概要】
                ・月:
                ・火:
                ・水:
                ・木:
                ・金:
                ・土:
                ・日:
                ・主なトレーニングメニュー（胸、腕、背中、肩、脚の5分割でトレーニング時間1時間を想定して提案して）:
                ・備考
                - トレーニング時の注意点
                - 食事制限中の空腹対策
                - サプリメントの活用方法（例：プロテイン摂取タイミング）
                - モチベーション維持の工夫
                ---
                """,
                form.user_height(),
                form.user_weight(),
                form.user_fat(),
                form.user_goal_weight(),
                form.user_goal_fat(),
                form.user_goal_type(),
                form.user_sport_type(),
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

    /**
     * ユーザー情報取得
     * @param userId
     * @param year
     * @param month
     * @param session
     * @return
     */
    @GetMapping("userinfo")
    public ResponseEntity<?> userInfo(@RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month, HttpSession session) {

        YearMonth ym;
        LocalDate today = LocalDate.now();
        YearMonth thisMonth = YearMonth.from(today);

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
        Optional<AiResponseData> aiResponseData = aiResponseDataService.getAiResponseDataByUserId(userId);
        String aiAdvice = aiResponseData.map(AiResponseData::getAiAdvice).orElse("");
        float targetCalories = aiResponseData.map(AiResponseData::getTargetCalorie).orElse(0.0f);
        float targetWeight = bodyDataInputService.getTargetWeight(userId);

        LocalDate lastDay = ym.equals(thisMonth) ? today : ym.atEndOfMonth();

        List<Map<String, Object>> todayMonthData = new ArrayList<>();
        for (LocalDate date = ym.atDay(1); !date.isAfter(lastDay); date = date.plusDays(1)) {
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

            todayMonthData.add(Map.of(
                    "name", date.toString(),
                    "weight", weight,
                    "fat", fat,
                    "caloriesIntake", calories,
                    "caloriesBurned", targetCalories,// 仮に固定値。あとで活動量計算できる
                    "targetWeight",targetWeight
            ));
        }

        return ResponseEntity.ok(Map.of("todayMonth", todayMonthData,"aiAdvice",aiAdvice));
    }
}
