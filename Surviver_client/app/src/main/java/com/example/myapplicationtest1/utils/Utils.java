package com.example.myapplicationtest1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.Subject;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.page.HomePage;
import com.example.myapplicationtest1.page.Page;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Utils {
    public static final double CLIENT_VERSION = 0.1;
    public static final double MINI_UPDATE_VERSION = 0.01;

    public static SharedPreferences sp;
    public static void setSharedPreference(Context context) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE); //数据自己可用
    }
    public static void doLogin(Context context, String userName, String password) {
        final SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", userName);
        edit.putString("password", password);
        edit.commit();
        Utils.doLogin(context);
    }
    public static void doLogin(Context context) {
        Cache.reloadAll();
        Page.jump(context, HomePage.class);
    }
    public static boolean writeJSONToFile(Context context, String fileName, JSONObject jsonObject) {
        return writeFile(context, fileName, jsonObject.toString());
    }
    public static boolean writeFile(Context context, String fileName, String content) {
        try(final FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static JSONObject readJSONFromFile(Context context, String fileName) {
        final String str = readFile(context, fileName);
        if(str == null)
            return null;
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String readFile(Context context, String fileName) {
        try(final FileInputStream fis = context.openFileInput(fileName);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[fis.available()];
            while ((fis.read(buffer)) != -1) {
                baos.write(buffer);
            }
            return new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 从data.xml中获取用户名称
    public static String getUserName() {
        return sp == null ? "SP_NULL" : sp.getString("userName", "NOT_LOGGED");
    }
    public static String getPassword() {
        return sp == null ? "SP_NULL" : sp.getString("password", "NOT_LOGGED");
    }
    public static boolean identifyUser() {
        return identifyUser(getUserName(), getPassword());
    }
    public static String loginFailReason;
    public static boolean identifyUser(String userName, String password) {
        //判断输入的用户名和密码是否正确
//        final String data = HttpClient.doGetShort("user/identifyUser?"
//                + "userName=" + userName + "&password=" + password);
        String data = null;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.accumulate("userName", userName);
            jsonBody.accumulate("password", password);
            System.out.println("Utils: " + jsonBody.toString());
            data = HttpClient.doPostShort(Urls.login(), jsonBody.toString());
            System.out.println("Utils: data: " + data);
            if(data == null)
                return false;
            jsonBody = new JSONObject(data);
            if(jsonBody.has("failReason")) {
                loginFailReason = jsonBody.getString("failReason");
                return false;
            }
            Cache.token = jsonBody.getString("token");
            Cache.setUserInfo(jsonBody.getJSONObject("user"));
        } catch (JSONException e) {
            System.out.println("Utils: failed.");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Fetching data of the current logged in user
     * @param activity current activity
     */
    public static void setUserTopBarInfo(Activity activity) {
        ((Button)activity.findViewById(R.id.grade_button)).setText(String.valueOf(Cache.grade));
        ((Button)activity.findViewById(R.id.stamina_button)).setText(String.valueOf(Cache.stamina));
        ((Button)activity.findViewById(R.id.money_button)).setText(String.valueOf(Cache.money));
    }
}