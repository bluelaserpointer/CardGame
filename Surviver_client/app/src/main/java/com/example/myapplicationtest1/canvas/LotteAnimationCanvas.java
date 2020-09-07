package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.game.contents.engine.Subject;
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
    private Paint whitePaint, drawCardPaint;
    //data
    public static int betCHI, betENG, betMAT;
    //animations
    private int animationFrame = -1;
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
        drawCardPaint = new Paint();
        drawCardPaint.setColor(Color.WHITE);
        drawCardPaint.setTextSize(40);
    }
    int cardYAdd = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(animationFrame >= 0)
            ++animationFrame;
        GHQ.setTargetCanvas(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), whitePaint);
        GHQ.drawStringGHQ("语文知识:" + betCHI + "/" + Cache.chiKnowledge, 50, 50, Subject.CHI.textPaint);
        GHQ.drawStringGHQ("英语知识:" + betENG + "/" + Cache.mathKnowledge, 50, 100, Subject.ENG.textPaint);
        GHQ.drawStringGHQ("数学知识:" + betMAT + "/" + Cache.engKnowledge, 50, 150, Subject.MAT.textPaint);
        canvas.drawRect(getWidth()/2 - 100, getHeight()/2 - 100, getWidth()/2 + 100, getHeight()/2 + 100, GHQ.generatePaint(Color.GREEN));
        GHQ.drawStringGHQ("总结知识", getWidth()/2 - 85, getHeight()/2, drawCardPaint);
        if(lastDrawnCard != null) { //animation of card drawing
            if(animationFrame == -1) {
                //show card status at the end of animation
                final Paint textPaint = lastDrawnCard.subject().textPaint;
                System.out.println(lastDrawnCard.subject());
                GHQ.drawStringGHQ(lastDrawnCard.cardName, getWidth()/2 - 350, getHeight() - 100, textPaint);
                GHQ.drawStringGHQ(lastDrawnCard.rarity, getWidth()/2 - 350, getHeight() - 60, textPaint);
                GHQ.drawStringGHQ("HP:" + lastDrawnCard.healthPoint, getWidth()/2 + 150, getHeight() - 100, textPaint);
                GHQ.drawStringGHQ("ATK:" + lastDrawnCard.attack, getWidth()/2 + 150, getHeight() - 60, textPaint);
            } else if(animationFrame <= 2) {
                cardYAdd += 7;
            } else if(animationFrame <= 8) {
                if(animationFrame % 2 == 0) {
                    cardYAdd += 7;
                }
            } else if(animationFrame <= 10) {
                cardYAdd += 7;
            } else if(animationFrame <= 16) {
                if (animationFrame % 2 == 0) {
                    cardYAdd += 7;
                }
            } else if(cardYAdd < lastDrawnCardImage.height()) {
                cardYAdd += 7;
            } else {
                animationFrame = -1;
            }
            lastDrawnCardImage.dotPaint(getWidth()/2, getHeight() + lastDrawnCardImage.height()/2 - cardYAdd);
        }
    }
    @Override
    public void touched(int x, int y) {
        if(animationFrame == -1 && (betCHI > 0 || betMAT > 0 || betENG > 0) && Math.abs(x - getWidth()/2) < 100 && Math.abs(y - getHeight()/2) < 100) {
            animationFrame = 0;
            cardYAdd = 0;
            JSONObject drawnCardJson;
            try {
                drawnCardJson = new JSONObject(HttpClient.doGetShort(super.getContext(), Urls.drawCard(betCHI, betMAT, betENG)));
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
