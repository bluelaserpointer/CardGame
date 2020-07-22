package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.Subject;
import com.example.myapplicationtest1.game.contents.unit.Enemy;
import com.example.myapplicationtest1.game.contents.engine.MyStage;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.game.contents.unit.MyUnit;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.page.BattleFinishPage;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.pageParts.ChapterListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameCanvas extends MyCanvas {
    final Context context;
    public GameCanvas(Context context) { //动态实例化view用到;
        super(context);
        this.context = context;
    }
    public GameCanvas(Context context, @Nullable AttributeSet attrs) { //在xml 用到;
        super(context, attrs);
        this.context = context;
    }
    public GameCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { //不会被系统默认调用，需要自己去显示的调用;
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    MyStage stage;
    @Override
    public void init() {
        GHQ.setStage(stage = new MyStage(getWidth(), getHeight()));
        stage.init();
        //load stage data
        fetch();
    }
    @Override
    public void touched(int x, int y) {
        if(stage.isGameClear || stage.isGameOver) {
            Page.jump(context, BattleFinishPage.class);
        }
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
                stage.addUnit(new Enemy(MyUnit.loadAsEnemy("card/getCard?cardId=" + posInfo.getInt("cardId"))).respawn(x, y));
            }
            //load friend formation
            //TODO: this is dummy!
            stage.addUnit(new Knowledge(new Knowledge.KnowledgeParameter(
                    0,
                            Subject.MAT,
                    "testFriendUnit",
                    R.drawable.tongyongm,
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
        stage.setSize(getWidth(), getHeight());
        GHQ.setTargetCanvas(canvas);
        stage.idle();
        GHQ.progressGameFrame();
        GHQ.fillCircle(getWidth(), getHeight(), 100, GHQ.generatePaint(Color.WHITE));
    }
}
