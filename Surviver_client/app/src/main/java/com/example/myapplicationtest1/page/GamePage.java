package com.example.myapplicationtest1.page;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.MyStage;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.pageParts.ChapterListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class GamePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        final View gameContent = findViewById(R.id.gameCanvas);
        gameContent.setOnTouchListener((view, motionEvent) -> {
            view.performClick();
            final MyStage stage = (MyStage) GHQ.stage();
            if(stage.isGameClear || stage.isGameOver) {
                Page.jump(this, BattleFinishPage.class);
            }
            return false;
        });
        new Timer().schedule(new TimerTask() {
            public void run() {
                if(gameContent != null)
                    gameContent.invalidate();
            }
        },200,50);
    }
}
