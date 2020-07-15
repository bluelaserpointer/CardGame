package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;

public class FriendAddPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_add);
        super.setJump(R.id.return_button, HomePage.class);
        super.setJump(R.id.friendList_button, OptionPage.class);
        super.setJump(R.id.friendAdd_button, FriendAddPage.class);
    }
}
