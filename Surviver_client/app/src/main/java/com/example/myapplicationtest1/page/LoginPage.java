package com.example.myapplicationtest1.page;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationtest1.FullscreenActivity;
import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class LoginPage{
    private EditText etNumber;
    private EditText etPassword;

    public static boolean identifyUser(FullscreenActivity screen) throws JSONException {
        System.out.println("Inside identifyUser");
//        EditText etUsername = screen.findViewById(R.id.usernameText);
//        EditText etPassword = screen.findViewById(R.id.passwordText);
//        System.out.println(etUsername);
//        System.out.println(etPassword);
        Map<String,String> userInfo= Utils.getUserInfo(screen);
        String getUserName = userInfo.get("userName");
        String getPassword = userInfo.get("password");
        if (getUserName != null && getPassword != null){
            //点击登陆时，获取QQ号和密码
            String userName = "userName=" + getUserName;
            String password = "password=" + getPassword;
            String url = "http://192.168.175.1:8080/user/identifyUser?" + userName + "&" + password;

            //判断你输入的QQ号和密码是否正确
            String data = HttpClient.doGet(url);

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

    public static boolean identifyUserInput(FullscreenActivity screen, String getUserName, String getPassword) throws  JSONException{
        String userName = "userName=" + getUserName;
        String password = "password=" + getPassword;
        String url = "http://192.168.175.1:8080/user/identifyUser?" + userName + "&" + password;
        System.out.println(url);
        //判断你输入的QQ号和密码是否正确
        String data = HttpClient.doGet(url);
        return data.charAt(0)==('t');
//        return true;
    }
}
