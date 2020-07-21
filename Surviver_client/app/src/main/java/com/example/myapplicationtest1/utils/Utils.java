package com.example.myapplicationtest1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.AccessibleObject;

public class Utils {
    public static void saveUserInfo(Context context, String userName,
                                    String password) {
        final SharedPreferences.Editor edit = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();//数据自己可用
        edit.putString("userName", userName);
        edit.putString("password", password);
        edit.commit();
    }
    public static void saveUserId(Context context, int id) {
        final SharedPreferences.Editor edit = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();//数据自己可用
        edit.putString("userId", String.valueOf(id));
        edit.commit();
    }

    // 从data.xml中获取用户名称
    public static SharedPreferences sp;
    public static void setSharedPreference(Context context) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }
    public static String getUserName() {
        return sp.getString("userName", "NOT_LOGGED");
    }
    public static String getPassword() {
        return sp.getString("password", "NOT_LOGGED");
    }
    public static String getUserId() {
        return sp.getString("userId", "-1");
    }
//    public static String getUserName(SharedPreferences sp, Context context) {
//        return sp.getString("userName", "NOT_LOGGED");
//    }
//    public static String getPassword(SharedPreferences sp, Context context) {
//        return sp.getString("password", "NOT_LOGGED");
//    }
//    public static String getUserId(SharedPreferences sp, Context context) {
//        return sp.getString("userId", "-1");
//    }
//    public static String getUserName(Context context) {
//        return getUserName(context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("userName", "NOT_LOGGED");
//    }
//    public static String getPassword(Context context) {
//        return context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("password", "NOT_LOGGED");
//    }
//    public static String getUserId(Context context) {
//        return context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("userId", "-1");
//    }
    public static boolean identifyUser(Context context) {
        return identifyUser(context, getUserName(), getPassword());
    }

    public static boolean identifyUser(Context context, String getUserName, String getPassword) {
        //判断输入的用户名和密码是否正确
        final String data = HttpClient.doGetShort("user/identifyUser?"
                + "userName=" + getUserName + "&"
                + "password=" + getPassword);
        final int userId = Integer.parseInt(data.substring(0, data.length() - 2));
        if(userId >= 0) {
            Utils.saveUserId(context, userId);
            return true;
        }
        return false;
    }
    public static void setUserTopBarInfo(Activity activity){
        // Fetching data of the current logged in user
        try{
            //TODO: switch that to get userId from Utils.getUserId
            //final JSONArray arr = new JSONArray(HttpClient.doGetShort("user/getUser?userId=" + Utils.getUserId(this)));
            final JSONObject userInfo = new JSONObject(HttpClient.doGetShort("user/getUser?userId=0"));
            // Sets the text of corresponding values
            ((Button)activity.findViewById(R.id.grade_button)).setText(userInfo.get("grade").toString());
            ((Button)activity.findViewById(R.id.stamina_button)).setText(userInfo.get("stamina").toString());
            ((Button)activity.findViewById(R.id.money_button)).setText(userInfo.get("money").toString());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}