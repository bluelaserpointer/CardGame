package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONException;

import java.util.Map;

public class LoginInputPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_input);
    }
    public void onLoginInputClick(View view) throws JSONException {
        EditText userNameText = findViewById(R.id.usernameText);
        String userName = userNameText.getText().toString();
        EditText passwordText = findViewById(R.id.passwordText);
        String password = passwordText.getText().toString();
        System.out.println(userName);
        System.out.println(password);
        if(Utils.identifyUserInput(userName, password))
        {
            System.out.println("InputIdentification succeeded!!!!!!!!!!!!!!!!");
            Utils.saveUserInfo(this, userName, password);
            Map<String,String> userInfo= Utils.getUserInfo(this);
            String getUserName = userInfo.get("userName");
            String getPassword = userInfo.get("password");
            System.out.println(getUserName);
            System.out.println(getPassword);
            Page.jump(this, HomePage.class);
        }else{
            System.out.println("InputIdentification failed!!!!!!!!!!!!!!!!");
        }
    }
}
