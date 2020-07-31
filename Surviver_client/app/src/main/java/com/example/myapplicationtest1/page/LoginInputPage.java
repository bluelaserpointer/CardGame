package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Urls;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginInputPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_input);
    }
    public void onRegisterClick(View view) {
        final String userName = ((EditText)findViewById(R.id.usernameText)).getText().toString();
        final String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        final String phoneNumber = ((EditText)findViewById(R.id.phoneNumberText)).getText().toString();
        final String mailAddress = ((EditText)findViewById(R.id.mailAddressText)).getText().toString();
        try {
            final JSONObject registerInfo = new JSONObject();
            registerInfo.put("userName", userName);
            registerInfo.put("password", password);
            registerInfo.put("phoneNumber", phoneNumber);
            registerInfo.put("email", mailAddress);
            HttpClient.doPostShort(Urls.register(), registerInfo.toString());
            if(Utils.identifyUser(userName, password)) {
                ((TextView)findViewById(R.id.loginStatus)).setText("注册成功");
                Utils.saveUserInfo(userName, password);
                Page.jump(this, HomePage.class);
            } else {
                ((TextView)findViewById(R.id.loginStatus)).setText("注册失败");
//                ((TextView)findViewById(R.id.loginStatus)).setText(Utils.loginFailReason);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        } else {
            System.out.println("LoginInputPage: identification failed: " + Utils.loginFailReason);
            ((TextView)findViewById(R.id.loginStatus)).setText(Utils.loginFailReason);
        }
    }
}
