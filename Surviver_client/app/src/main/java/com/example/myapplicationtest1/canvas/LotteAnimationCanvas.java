package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class LotteAnimationCanvas extends MyCanvas {
    /*bitmaps*/
    //private Bitmap touchNotationBM, stageSpotBM;
    //paints
    private Paint whitePaint, chiPaint, engPaint, matPaint;
    //data
    public static int stockCHI, stockENG, stockMAT;
    public static int betCHI, betENG, betMAT;

    public LotteAnimationCanvas(Context context)  {//动态实例化view用到;
        super(context);
    }

    public LotteAnimationCanvas(Context context, @Nullable AttributeSet attrs) { //在xml 用到;
        super(context, attrs);
    }

    public LotteAnimationCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { //不会被系统默认调用，需要自己去显示的调用;
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void init() {
        //touchNotationBM = BitmapFactory.decodeResource(getResources(), R.drawable.titlecover);
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        chiPaint = new Paint();
        chiPaint.setColor(Color.GREEN);
        chiPaint.setTextSize(40);
        engPaint = new Paint();
        engPaint.setColor(Color.RED);
        engPaint.setTextSize(40);
        matPaint = new Paint();
        matPaint.setColor(Color.BLUE);
        matPaint.setTextSize(40);
        try {
            JSONObject data = new JSONObject(HttpClient.doGetShort(Urls.getUser()));
            stockCHI = data.getInt("chiKnowledge");
            stockENG = data.getInt("engKnowledge");
            stockMAT = data.getInt("mathKnowledge");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GHQ.setTargetCanvas(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), whitePaint);
        GHQ.drawStringGHQ("语文知识:" + betCHI + "/" + stockCHI, 50, 50, chiPaint);
        GHQ.drawStringGHQ("英语知识:" + betENG + "/" + stockENG, 50, 100, engPaint);
        GHQ.drawStringGHQ("数学知识:" + betMAT + "/" + stockMAT, 50, 150, matPaint);
        canvas.drawRect(getWidth()/2 - 100, getHeight()/2 - 100, getWidth()/2 + 100, getHeight()/2 + 100, GHQ.generatePaint(Color.GREEN));
    }
    @Override
    public void touched(int x, int y) {
        System.out.println("LotteAnimationCanvas: " + x + ", " + y);
        if(Math.abs(x - getWidth()/2) < 100 && Math.abs(y - getHeight()/2) < 100) {
            //TODO: waiting for backend making drawCard process
            //String data = HttpClient.doGetShort("mechanism/drawCard?chi=" + betCHI + "&mat=" + betMAT + "&eng=" + betENG);
            //int id = Integer.parseInt(data.substring(data.length() - 2));
            betCHI = betENG = betMAT = 0;
        }
    }
    public static void addCHI() {
        if(betCHI < stockCHI)
            ++betCHI;
    }
    public static void addENG() {
        if(betENG < stockENG)
            ++betENG;
    }
    public static void addMAT() {
        if(betMAT < stockMAT)
            ++betMAT;
    }
    public static void removeCHI() {
        if(betCHI > 0)
            --betCHI;
    }
    public static void removeENG() {
        if(betENG > 0)
            --betENG;
    }
    public static void removeMAT() {
        if(betMAT > 0)
            --betMAT;
    }
}
