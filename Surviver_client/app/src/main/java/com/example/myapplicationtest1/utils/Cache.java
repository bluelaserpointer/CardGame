package com.example.myapplicationtest1.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Cache {
    /////////////
    //authorization token
    /////////////
    public static String token;
    /////////////
    //user
    /////////////
    private static JSONObject userInfo;
    public static int chiKnowledge;
    public static int credits;
    public static int curExpPoint;
    public static String email;
    public static double grade;
    public static int level;
    public static int mathKnowledge;
    public static int money;
    public static String phoneNumber;
    public static int stamina;
    public static int userId;
    public static String userName;
    public static void setUserInfo(JSONObject userInfo) {
        try {
            chiKnowledge = userInfo.getInt("chiKnowledge");
            credits = userInfo.getInt("credits");
            curExpPoint = userInfo.getInt("curExpPoint");
            email = userInfo.getString("email");
            grade = userInfo.getDouble("grade");
            level = userInfo.getInt("level");
            mathKnowledge = userInfo.getInt("mathKnowledge");
            money = userInfo.getInt("money");
            phoneNumber = userInfo.getString("phoneNumber");
            stamina = userInfo.getInt("stamina");
            userId = userInfo.getInt("userId");
            userName = userInfo.getString("userName");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject getUserInfo() {
        return userInfo;
    }
}
