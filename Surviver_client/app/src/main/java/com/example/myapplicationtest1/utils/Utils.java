package com.example.myapplicationtest1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.Subject;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Utils {
    public static final double CLIENT_VERSION = 0.1;

    public static SharedPreferences sp;
    public static int userId;
    public static void setSharedPreference(Context context) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE); //数据自己可用
    }
    public static void saveUserInfo(String userName, String password) {
        final SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", userName);
        edit.putString("password", password);
        edit.commit();
    }
    public static void saveUserId(int id) {
//        final SharedPreferences.Editor edit = sp.edit();
//        edit.putString("userId", String.valueOf(id));
//        edit.commit();
        userId = id;
    }

    // 从data.xml中获取用户名称
    public static String getUserName() {
        return sp == null ? "SP_NULL" : sp.getString("userName", "NOT_LOGGED");
    }
    public static String getPassword() {
        return sp == null ? "SP_NULL" : sp.getString("password", "NOT_LOGGED");
    }
    public static Integer getUserId() {
        //return sp.getString("userId", "-1");
        return userId;
    }
    public static boolean identifyUser() {
        return identifyUser(getUserName(), getPassword());
    }
    public static int loginFailReason;
    public static boolean identifyUser(String userName, String password) {
        //判断输入的用户名和密码是否正确
//        final String data = HttpClient.doGetShort("user/identifyUser?"
//                + "userName=" + userName + "&password=" + password);
        String data = null;
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.accumulate("userName", userName);
            requestBody.accumulate("password", password);
            System.out.println("Utils: " + requestBody.toString());
            data = HttpClient.doPostShort(Urls.identifyUser(), requestBody.toString());
            System.out.println("Utils: data: " + data);
            if(data == null)
                return false;
            Urls.token = data;
//            requestBody.remove("userName");
//            requestBody.remove("password");
            requestBody = new JSONObject();
            requestBody.accumulate("userId", 0);
            System.out.println("Utils2: " + requestBody.toString());
            data = HttpClient.doPostShort(Urls.identifyUser(), requestBody.toString());
            System.out.println("Utils: data2: " + data);
        } catch (JSONException e) {
            System.out.println("Utils: failed.");
            e.printStackTrace();
            return false;
        }
        //TODO: waiting backend make userId provider
//        final int userId = Integer.parseInt(data.substring(0, data.length() - 2));
        final int usrId = 0;
        if(userId >= 0) {
            Utils.saveUserId(userId);
            return true;
        } else {
            loginFailReason = userId;
            System.out.println("Utils: identification result: " + userId);
        }
        return false;
    }
    public static void setUserTopBarInfo(Activity activity, JSONObject userInfo) {
        try {
            ((Button)activity.findViewById(R.id.grade_button)).setText(userInfo.get("grade").toString());
            ((Button)activity.findViewById(R.id.stamina_button)).setText(userInfo.get("stamina").toString());
            ((Button)activity.findViewById(R.id.money_button)).setText(userInfo.get("money").toString());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Fetching data of the current logged in user
     * @param activity current activity
     */
    public static void setUserTopBarInfo(Activity activity) {
        try{
            setUserTopBarInfo(activity, getUserInfo());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject getUserInfo() throws JSONException {
        final String data = HttpClient.doGetShort(Urls.getUser());
        System.out.println("Utils::getUerInfo: " + data + ", " + Urls.token);
        return new JSONObject(data);
    }
    public static HashMap<Integer, Knowledge.KnowledgeParameter> ownCardRemoteRecords = new HashMap<>();
    public static HashMap<Integer, Knowledge.KnowledgeParameter> cardsInfoRemoteRecords = new HashMap<>();
    static {
        //TODO: This is dummy! Switch this to downloaded data.
        ownCardRemoteRecords.put(0, new Knowledge.KnowledgeParameter(
                0,
                Subject.MAT,
                "testFriendUnit",
                R.drawable.tongyongm,
                100,
                100,
                100,
                100,
                100,
                100,
                "",
                ""
        ));
    }
    public static void loadAllCardsInfo() {

    }
    public static Knowledge.KnowledgeParameter getKnowledgeParameter(int ownCardId) {
        return ownCardRemoteRecords.get(ownCardId);
    }
}