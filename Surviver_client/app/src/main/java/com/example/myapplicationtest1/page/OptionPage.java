package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;

public class OptionPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Waiting to turn it to "R.layout.option"
        setContentView(R.layout.friend_list);
        super.setJump(R.id.return_button, HomePage.class);
        super.setJump(R.id.friendList_button, OptionPage.class);
        super.setJump(R.id.friendAdd_button, FriendAddPage.class);
    }
}
