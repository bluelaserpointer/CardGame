package com.example.myapplicationtest1.page;

import android.os.Bundle;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.canvas.LotteAnimationCanvas;

public class LottePage extends Page {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotte);
        super.setJump(R.id.return_button, HomePage.class);
        super.setJump(R.id.toShop_button, ShopPage.class);
        super.setTouchEvent(R.id.lotteBet_button1, () -> ++LotteAnimationCanvas.betCHI);
        super.setTouchEvent(R.id.lotteBet_button2, () -> ++LotteAnimationCanvas.betENG);
        super.setTouchEvent(R.id.lotteBet_button3, () -> ++LotteAnimationCanvas.betMAT);
        super.setTouchEvent(R.id.lotteBet_button4, () -> --LotteAnimationCanvas.betCHI);
        super.setTouchEvent(R.id.lotteBet_button5, () -> --LotteAnimationCanvas.betENG);
        super.setTouchEvent(R.id.lotteBet_button6, () -> --LotteAnimationCanvas.betMAT);
    }
}
