package com.ai_personal_backend.ai_personal_backend.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PfcParser {
    public static PfcForm parse(String aiResponse) {
        // 正規表現でPFCとカロリーを抽出
        Pattern caloriePattern = Pattern.compile("目標摂取カロリー[:：]\\s*(\\d+)\\s*k?cal?");
        Pattern pfcPattern = Pattern.compile("目標摂取PFC[:：]\\s*P\\s*(\\d+)g[^\\d]*F\\s*(\\d+)g[^\\d]*C\\s*(\\d+)g");

        Matcher calMatcher = caloriePattern.matcher(aiResponse);
        Matcher pfcMatcher = pfcPattern.matcher(aiResponse);

        int calorie = 0;
        float protein = 0, fat = 0, carb = 0;

        if (calMatcher.find()) {
            calorie = Integer.parseInt(calMatcher.group(1));
        }
        if (pfcMatcher.find()) {
            protein = Float.parseFloat(pfcMatcher.group(1));
            fat = Float.parseFloat(pfcMatcher.group(2));
            carb = Float.parseFloat(pfcMatcher.group(3));
        }

        return new PfcForm(calorie, protein, fat, carb);
    }

}
