package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.R;

/**
 * 负责关卡地图的显示与触碰判定
 */

public class StageSelectorCanvas extends View {
    public static int currentStage = 0;
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
                    {new Point(10,200), new Point(800,50)},
                    {new Point(70,100), new Point(100,500)},
            };
    /*小球移动的方向*/
    private boolean direction;
    private Paint paint;
    private Paint whitePaint;

    public StageSelectorCanvas(Context context) {//动态实例化view用到;
        super(context);
        init();
    }
    public StageSelectorCanvas(Context context, @Nullable AttributeSet attrs) {//在xml 用到;
        super(context, attrs);
        init();
    }
    public StageSelectorCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {//不会被系统默认调用，需要自己去显示的调用;
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init() {
        //初始化画笔 参数表示去锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(COLOR);
        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);
        x = RADIUS;
        //bm = BitmapFactory.decodeFile("../res/image/Card0.png");
        touchNotationBM = BitmapFactory.decodeResource(getResources(), R.drawable.titlecover);
        stageSpotBM = BitmapFactory.decodeResource(getResources(), R.drawable.map_site);
        super.setOnTouchListener((v, event) -> {
            v.performClick();
            x = (int)event.getX();
            y = (int)event.getY();
            System.out.println("StageSelectorCanvas: " + x + ", " + y);
            return false;
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.rotate(90, canvas.getWidth()/2, canvas.getWidth()/2);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), whitePaint);
        int mBitWidth = touchNotationBM.getWidth();
        int mBitHeight = touchNotationBM.getHeight();
        //canvas.drawBitmap(bm, new Rect(0, 0, mBitWidth, mBitHeight), new Rect(0, 0, canvas.getHeight(), canvas.getWidth()), paint);
        for(Point point : stageSpotPoints[currentStage]) {
            canvas.drawBitmap(stageSpotBM, point.x, point.y, new Paint());
        }
        //根据x,y 坐标画一个小球
        canvas.drawCircle(x, y, RADIUS, paint);
        x += 10;
        //获取组件的宽度
        int measuredWidth = this.getMeasuredWidth();

        //canvas.rotate(-90, canvas.getWidth()/2, canvas.getWidth()/2);
    }
}
