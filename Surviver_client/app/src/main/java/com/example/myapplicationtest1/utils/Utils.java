package com.example.myapplicationtest1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplicationtest1.HttpClient;

import org.json.JSONException;

public class Utils {
    public static void saveUserInfo(Context context, String userName,
                                    String password) {
        final SharedPreferences.Editor edit = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();//数据自己可用
        edit.putString("userName", userName);
        edit.putString("password", password);
        edit.commit();
    }

    // 从data.xml中获取用户名称
    public static String getUserName(Context context) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("userName", "NOT_LOGGED");
    }
    public static String getPassword(Context context) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("password", "NOT_LOGGED");
    }

    public static boolean identifyUser(Context context) throws JSONException {
        System.out.println("Inside identifyUser");
        final String getUserName = getUserName(context);
        final String getPassword = getPassword(context);
        if (getUserName != null && getPassword != null) {
            //点击登陆时，获取用户名与密码
            final String data = HttpClient.doGetShort("user/identifyUser?"
                    + "userName=" + getUserName + "&"
                    + "password=" + getPassword);
            return !data.equals("false");
        }else{
            return false;
        }
    }

    public static boolean identifyUserInput(String getUserName, String getPassword) throws JSONException {
        //判断输入的用户名和密码是否正确
        final String data = HttpClient.doGetShort("user/identifyUser?"
                + "userName=" + getUserName + "&"
                + "password=" + getPassword);
        return data.charAt(0)==('t');
    }
}