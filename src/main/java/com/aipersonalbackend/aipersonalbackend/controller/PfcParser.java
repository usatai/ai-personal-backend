package com.aipersonalbackend.aipersonalbackend.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PfcParser {
    public static PfcForm parse(String aiResponse) {
        // 正規表現でPFCとカロリーを抽出
        Pattern caloriePattern = Pattern.compile("目標摂取カロリー[:：]\\s*([\\d,]+)[kK]?[cC]al?");
        Pattern pfcPattern = Pattern.compile("タンパク質\\s*(\\d+(?:\\.\\d+)?)g\\s*/\\s*脂質\\s*(\\d+(?:\\.\\d+)?)g\\s*/\\s*炭水化物\\s*(\\d+(?:\\.\\d+)?)g");

        Matcher calMatcher = caloriePattern.matcher(aiResponse);
        Matcher pfcMatcher = pfcPattern.matcher(aiResponse);

        int calorie = 0;
        float protein = 0, fat = 0, carb = 0;

        if (calMatcher.find()) {
            calorie = Integer.parseInt(calMatcher.group(1).replace(",", ""));
        }
        if (pfcMatcher.find()) {
            protein = Float.parseFloat(pfcMatcher.group(1));
            fat = Float.parseFloat(pfcMatcher.group(2));
            carb = Float.parseFloat(pfcMatcher.group(3));
        }

        return new PfcForm(calorie, protein, fat, carb);
    }

}
