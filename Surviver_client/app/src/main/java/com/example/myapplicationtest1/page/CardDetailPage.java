package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;

public class CardDetailPage extends Page {
    private int sp;
    private int hpUpg, atkUpg, defUpg, atkRangeUpg, cdUpg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);
        super.setJump(R.id.return_button, CardStoragePage.class);
        super.setTouchEvent(R.id.addHP, () -> { if(drawSp()) ++hpUpg; });
        super.setTouchEvent(R.id.addATK, () -> { if(drawSp()) ++atkUpg; });
        super.setTouchEvent(R.id.addDEF, () -> { if(drawSp()) ++defUpg; });
        super.setTouchEvent(R.id.addATKRange, () -> { if(drawSp()) ++atkRangeUpg; });
        super.setTouchEvent(R.id.addCD, () -> { if(drawSp()) ++cdUpg; });
        //TODO: check if it possible to revert skill up points.
        super.setTouchEvent(R.id.subHP, () -> {});
        super.setTouchEvent(R.id.subATK, () -> {});
        super.setTouchEvent(R.id.subDEF, () -> {});
        super.setTouchEvent(R.id.subATKRange, () -> {});
        super.setTouchEvent(R.id.subCD, () -> {});
        }
    private boolean drawSp() {
        if(sp <= 0)
            return false;
        --sp;
        return true;
    }
}
