package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public abstract class MyCanvas extends View {
    public MyCanvas(Context context) { //动态实例化view用到;
        super(context);
        start();
    }
    public MyCanvas(Context context, @Nullable AttributeSet attrs) { //在xml 用到;
        super(context, attrs);
        start();
    }
    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { //不会被系统默认调用，需要自己去显示的调用;
        super(context, attrs, defStyleAttr);
        start();
    }
    private void start() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                invalidate();
            }
        }, 200, 50);
        init();
    }
    public abstract void init();
}
