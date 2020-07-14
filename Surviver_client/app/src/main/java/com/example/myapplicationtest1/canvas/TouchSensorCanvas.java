package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.R;

/**
 * 只是用来测验触碰坐标获取的View。
 * 使用方法笔记:
 *  final View mapSelecter = findViewById(R.id.stageSelecter);
 *  new Timer().schedule(new TimerTask() {
 *      public void run() {
 *          if(mapSelecter != null)
 *              mapSelecter.invalidate();
 *      }
 *  },200,50);
 */
public class TouchSensorCanvas extends View {
    /*小球的位置*/
    private int x, y;
    /*小球的半径*/
    private static final int RADIUS = 30;
    /*小球的颜色*/
    private static final int COLOR = Color.RED;
    private Bitmap bm;
    /*小球移动的方向*/
    private boolean direction;
    private Paint paint;
    private Paint whitePaint;

    public TouchSensorCanvas(Context context) {//动态实例化view用到;
        super(context);
        init();
    }

    public TouchSensorCanvas(Context context, @Nullable AttributeSet attrs) {//在xml 用到;
        super(context, attrs);
        //初始化画笔 参数表示去锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(COLOR);
        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);
        x = RADIUS;
        init();
    }

    public TouchSensorCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {//不会被系统默认调用，需要自己去显示的调用;
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        //bm = BitmapFactory.decodeFile("../res/image/Card0.png");
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.titlecover);
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = (int)event.getX();
                y = (int)event.getY();
                System.out.println("touch: " + x + ", " + y);
                return false;
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.rotate(90, canvas.getWidth()/2, canvas.getWidth()/2);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), whitePaint);
        int mBitWidth = bm.getWidth();
        int mBitHeight = bm.getHeight();
        //canvas.drawBitmap(bm, new Rect(0, 0, mBitWidth, mBitHeight), new Rect(0, 0, canvas.getHeight(), canvas.getWidth()), paint);
        //根据x,y 坐标画一个小球
        canvas.drawCircle(x, y, RADIUS, paint);
        //获取组件的宽度
        int measuredWidth = this.getMeasuredWidth();

        //canvas.rotate(-90, canvas.getWidth()/2, canvas.getWidth()/2);
    }
}
