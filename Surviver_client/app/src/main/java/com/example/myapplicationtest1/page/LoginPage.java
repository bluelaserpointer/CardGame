package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.widget.EditText;

import com.example.myapplicationtest1.FullscreenActivity;
import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONException;

import java.util.Map;

public class LoginPage extends Page {
    private EditText etNumber;
    private EditText etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if(identifyUser())
            {
                System.out.println("Identification succeeded!!!!!!!!!!!!!!!!");
                Page.jump(this, HomePage.class);
            }else{
                System.out.println("Identification failed!!!!!!!!!!!!!!!");
                setContentView(R.layout.login_input);
                System.out.println("Identification done!!!!!!!!!!!!!!!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean identifyUser() throws JSONException {
        System.out.println("Inside identifyUser");
//        EditText etUsername = screen.findViewById(R.id.usernameText);
//        EditText etPassword = screen.findViewById(R.id.passwordText);
//        System.out.println(etUsername);
//        System.out.println(etPassword);
        Map<String,String> userInfo= Utils.getUserInfo(this);
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

    public boolean identifyUserInput(String getUserName, String getPassword) throws JSONException {
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
