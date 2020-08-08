package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class LotteAnimationCanvas extends MyCanvas {
    /*bitmaps*/
    //private Bitmap touchNotationBM, stageSpotBM;
    //paints
    private Paint whitePaint, chiPaint, engPaint, matPaint;
    //data
    public static int betCHI, betENG, betMAT;
    //animations
    private int animationFrame;
    private Cache.Card lastDrawnCard;
    private ImageFrame lastDrawnCardImage;

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
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ++animationFrame;
        GHQ.setTargetCanvas(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), whitePaint);
        GHQ.drawStringGHQ("语文知识:" + betCHI + "/" + Cache.chiKnowledge, 50, 50, chiPaint);
        GHQ.drawStringGHQ("英语知识:" + betENG + "/" + Cache.mathKnowledge, 50, 100, engPaint);
        GHQ.drawStringGHQ("数学知识:" + betMAT + "/" + Cache.engKnowledge, 50, 150, matPaint);
        canvas.drawRect(getWidth()/2 - 100, getHeight()/2 - 100, getWidth()/2 + 100, getHeight()/2 + 100, GHQ.generatePaint(Color.GREEN));
        if(lastDrawnCard != null) {
            lastDrawnCardImage.dotPaint(getWidth()/2, getHeight() - lastDrawnCardImage.height()/2);
        }
    }
    @Override
    public void touched(int x, int y) {
        if((betCHI > 0 || betMAT > 0 || betENG > 0) && Math.abs(x - getWidth()/2) < 100 && Math.abs(y - getHeight()/2) < 100) {
            //TODO: waiting for backend making drawCard process
            animationFrame = 0;
            JSONObject drawnCardJson;
            try {
                drawnCardJson = new JSONObject(HttpClient.doGetShort(Urls.drawCard(betCHI, betMAT, betENG)));
                lastDrawnCard = Cache.cards.get(drawnCardJson.getInt("drawnCardId"));
                lastDrawnCardImage = ImageFrame.create(lastDrawnCard.drawableId);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            Cache.chiKnowledge -= betCHI;
            Cache.mathKnowledge -= betMAT;
            Cache.engKnowledge -= betENG;
            betCHI = betENG = betMAT = 0;
        }
    }
    public static void addCHI() {
        if(betCHI < Cache.chiKnowledge)
            ++betCHI;
    }
    public static void addENG() {
        if(betENG < Cache.engKnowledge)
            ++betENG;
    }
    public static void addMAT() {
        if(betMAT < Cache.mathKnowledge)
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
