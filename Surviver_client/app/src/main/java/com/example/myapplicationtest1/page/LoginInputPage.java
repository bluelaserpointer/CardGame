package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
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
        final String userName = ((EditText)findViewById(R.id.usernameText)).getText().toString();
        final String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        if(Utils.identifyUserInput(userName, password)) {
            System.out.println("InputIdentification succeeded!!!!!!!!!!!!!!!!");
            Utils.saveUserInfo(this, userName, password);
//            Map<String,String> userInfo = Utils.getUserInfo(this);
//            String getUserName = userInfo.get("userName");
//            String getPassword = userInfo.get("password");
//            System.out.println(getUserName);
//            System.out.println(getPassword);
            Page.jump(this, HomePage.class);
        }else{
            System.out.println("InputIdentification failed!!!!!!!!!!!!!!!!");
        }
    }
}
