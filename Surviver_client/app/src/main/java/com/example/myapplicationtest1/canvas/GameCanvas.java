package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.MyStage;
import com.example.myapplicationtest1.game.core.GHQ;

public class GameCanvas extends View {
    /*小球的位置*/
    private int x, y;
    /*小球的半径*/
    private static final int RADIUS = 30;
    private static final int COLOR = Color.RED;
    /*bitmaps*/
    private Bitmap touchNotationBM, stageSpotBM;
    /*stage data*/
    private static final Point stageSpotPoints[][] =
            {
                    {new Point(10,200), new Point(400,50)},
                    {new Point(70,100), new Point(100,500)},
            };
    private int currentStage = 0;
    /*小球移动的方向*/
    private boolean direction;
    private Paint paint;
    private Paint whitePaint;

    public GameCanvas(Context context) {//动态实例化view用到;
        super(context);
        init();
    }

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {//在xml 用到;
        super(context, attrs);
        //初始化画笔 参数表示去锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(COLOR);
        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);
        x = RADIUS;
        init();
    }

    public GameCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {//不会被系统默认调用，需要自己去显示的调用;
        super(context, attrs, defStyleAttr);
        init();
    }
    MyStage stage;
    public void init() {
        super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = (int)event.getX();
                y = (int)event.getY();
                return false;
            }
        });
        stage = new MyStage(getWidth(), getHeight());
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GHQ.setTargetCanvas(canvas);
        //canvas.rotate(90, canvas.getWidth()/2, canvas.getWidth()/2);
        stage.idle();
        //canvas.rotate(-90, canvas.getWidth()/2, canvas.getWidth()/2);
    }
}
