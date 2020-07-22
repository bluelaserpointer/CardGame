package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.view.View;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.MyStage;
import com.example.myapplicationtest1.game.core.GHQ;

import java.util.Timer;
import java.util.TimerTask;

public class GamePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
    }
}
