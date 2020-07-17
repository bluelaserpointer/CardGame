package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;

public class BattleFinishPage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_finish_exp);
        findViewById(R.id.battle_finish_exp_base).setOnTouchListener((view, motionEvent) -> {
            view.performClick();
            setContentView(R.layout.battle_finish_item);
            findViewById(R.id.battle_finish_item_base).setOnTouchListener((view2, motionEvent2) -> {
                view.performClick();
                Page.jump(this, MapPage.class);
                return false;
            });
            return false;
        });
    }
}
