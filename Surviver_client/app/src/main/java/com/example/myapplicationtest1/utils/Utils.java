package com.example.myapplicationtest1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.myapplicationtest1.HttpClient;

import org.json.JSONException;

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

    public static boolean identifyUser(Context context) throws JSONException {
        System.out.println("Inside identifyUser");
//        EditText etUsername = screen.findViewById(R.id.usernameText);
//        EditText etPassword = screen.findViewById(R.id.passwordText);
//        System.out.println(etUsername);
//        System.out.println(etPassword);
        final Map<String,String> userInfo= Utils.getUserInfo(context);
        final String getUserName = userInfo.get("userName");
        final String getPassword = userInfo.get("password");
        if (getUserName != null && getPassword != null){
            //点击登陆时，获取QQ号和密码
            final String data = HttpClient.doGetShort("user/identifyUser?"
                    + "userName=" + getUserName + "&"
                    + "password=" + getPassword);
            System.out.println("identifyUserInput's data: " + data);
            return !data.equals("false");
        }else{
            return false;
        }

//        if(TextUtils.isEmpty(number)){
//            Toast.makeText(screen,"请输入QQ号",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(screen,"请输入密码",Toast.LENGTH_SHORT).show();
//            return;
//        }

//        //登陆成功
//        Toast.makeText(screen,"登陆成功",Toast.LENGTH_SHORT).show();
//        Log.i("MainActivity","记住密码：" + number + ","+password);
//        //保存用户信息
//        boolean isSaveSuccess=Utils.saveUserInfo(screen, number, password);
//        if (isSaveSuccess){
//            Toast.makeText(screen,"保存成功",Toast.LENGTH_SHORT).show();
//
//        }else {
//            Toast.makeText(screen,"保存失败", Toast.LENGTH_SHORT).show();
//        }
    }

    public static boolean identifyUserInput(String getUserName, String getPassword) throws JSONException {
        //判断你输入的QQ号和密码是否正确
        final String data = HttpClient.doGetShort("user/identifyUser?"
                + "userName=" + getUserName + "&"
                + "password=" + getPassword);
        System.out.println("identifyUserInput's data: " + data);
        return data.charAt(0)==('t');
//        return true;
    }
}