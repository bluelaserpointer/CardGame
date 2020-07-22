package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.canvas.LotteAnimationCanvas;
import com.example.myapplicationtest1.utils.Utils;

public class LottePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotte);
        super.setJump(R.id.return_button, HomePage.class);
        super.setJump(R.id.toShop_button, ShopPage.class);
        super.setTouchEvent(R.id.lotteBet_button1, LotteAnimationCanvas::addCHI);
        super.setTouchEvent(R.id.lotteBet_button2, LotteAnimationCanvas::addENG);
        super.setTouchEvent(R.id.lotteBet_button3, LotteAnimationCanvas::addMAT);
        super.setTouchEvent(R.id.lotteBet_button4, LotteAnimationCanvas::removeCHI);
        super.setTouchEvent(R.id.lotteBet_button5, LotteAnimationCanvas::removeENG);
        super.setTouchEvent(R.id.lotteBet_button6, LotteAnimationCanvas::removeMAT);
        Utils.setUserTopBarInfo(this);
    }
}
