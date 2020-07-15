package com.example.myapplicationtest1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static boolean saveUserInfo(Context context, String userName,
                                       String password) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);//数据自己可用
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", userName);
        edit.putString("password", password);
        edit.commit();
        return true;//存储成功
    }

    // 从data.xml中获取QQ号码和密码
    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);

        String userName = sp.getString("userName", null);
        String password = sp.getString("password", null);
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userName", userName);
        userMap.put("password", password);

        return userMap;
    }
}