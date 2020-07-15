package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;

public class MapPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        super.setJump(R.id.return_button, HomePage.class);
    }
}
