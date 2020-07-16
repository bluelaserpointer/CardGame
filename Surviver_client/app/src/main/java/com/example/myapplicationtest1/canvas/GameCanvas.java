package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.Subject;
import com.example.myapplicationtest1.game.contents.unit.Enemy;
import com.example.myapplicationtest1.game.contents.engine.MyStage;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.pageParts.ChapterListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        GHQ.setResourceProvider(this);
        GHQ.setStage(stage = new MyStage(getWidth(), getHeight()));
        stage.init();
        //load stage data
        fetch();
    }
    public void fetch() {
        try {
            //load enemy formation
            final JSONArray arr = new JSONArray(HttpClient.doGetShort("chapter/getChapterDetailsByChapter?chapterId=" + ChapterListAdapter.selectedPhase));
            for(int i = 0; i < arr.length(); ++i) {
                final JSONObject posInfo = arr.getJSONObject(i);
                final int pos = posInfo.getInt("positionId");
                final int x = 500 + pos%5*100;
                final int y = 100 + pos/5*100;
                final JSONObject enemyInfo = new JSONObject(HttpClient.doGetShort("card/getCard?cardId=" + posInfo.getInt("cardId")));
                stage.addUnit(new Enemy(new Enemy.EnemyParameter(
                        enemyInfo.getString("cardName"),
                        ImageFrame.create(R.drawable.tongyongc),
                        enemyInfo.getInt("healthPoint"),
                        enemyInfo.getInt("attack"),
                        enemyInfo.getInt("defense"),
                        enemyInfo.getInt("attackRange"),
                        enemyInfo.getInt("cd"),
                        enemyInfo.getInt("speed"),
                        enemyInfo.getString("cardDetails")
                )).respawn(x, y));
            }
            //load friend formation
            //TODO: this is dummy!
            stage.addUnit(new Knowledge(new Knowledge.KnowledgeParameter(
                            Subject.MAT,
                    "testFriendUnit",
                    ImageFrame.create(R.drawable.tongyongm),
                    100,
                    100,
                    100,
                    100,
                    100,
                    100,
                    "",
                    ""
            )).respawn(100, 800));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        stage.setSize(canvas.getWidth(), canvas.getHeight());
        GHQ.setTargetCanvas(canvas);
        stage.idle();
        GHQ.progressGameFrame();
        GHQ.fillCircle(canvas.getWidth(), canvas.getHeight(), 100, GHQ.generatePaint(Color.WHITE));
    }
}
