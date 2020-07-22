package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.StartPage;

public class UserInfoPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        super.setJump(R.id.return_button, HomePage.class);
        super.setJump(R.id.logout_button, StartPage.class);
    }
}
