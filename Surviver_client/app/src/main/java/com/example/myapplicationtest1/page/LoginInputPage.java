package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONException;

public class LoginInputPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_input);
    }
    public void onLoginInputClick(View view) {
        final String userName = ((EditText)findViewById(R.id.usernameText)).getText().toString();
        final String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        if(userName.isEmpty()) {
            ((TextView)findViewById(R.id.loginStatus)).setText("用户名未输入");
            return;
        }
        if(password.isEmpty()) {
            ((TextView)findViewById(R.id.loginStatus)).setText("密码未输入");
            return;
        }
        if(Utils.identifyUser(userName, password)) {
            System.out.println("InputIdentification succeeded!!!!!!!!!!!!!!!!");
            Utils.saveUserInfo(userName, password);
            Page.jump(this, HomePage.class);
        }else{
            System.out.println("LoginInputPage: identification failed: " + Utils.loginFailReason);
            switch(Utils.loginFailReason) {
                case -1:
                    ((TextView)findViewById(R.id.loginStatus)).setText("用户名不存在");
                    break;
                case -2:
                    ((TextView)findViewById(R.id.loginStatus)).setText("密码错误");
                    break;
                case -3:
                    ((TextView)findViewById(R.id.loginStatus)).setText("该用户被禁用！");
                    break;
            }
        }
    }
}
