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
    public static void setSharedPreference(Context context) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE); //数据自己可用
    }
    public static void saveUserInfo(String userName, String password) {
        final SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", userName);
        edit.putString("password", password);
        edit.commit();
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