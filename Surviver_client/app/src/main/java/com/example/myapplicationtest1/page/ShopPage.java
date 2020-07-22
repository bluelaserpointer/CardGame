package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Utils;

public class ShopPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        super.setJump(R.id.return_button, HomePage.class);
        super.setJump(R.id.toLotte_button, LottePage.class);
        Utils.setUserTopBarInfo(this);
    }
}
