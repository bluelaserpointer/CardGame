package com.example.myapplicationtest1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.game.contents.unit.Enemy;
import com.example.myapplicationtest1.game.contents.engine.MyStage;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.game.contents.unit.MyUnit;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.page.BattleFinishPage;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.pageParts.ChapterListAdapter;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.GameState;
import com.example.myapplicationtest1.utils.MapStringUtil;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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
            HttpClient.doPostShort(
                    Urls.phaseClear(ChapterListAdapter.selectedChapter + 1, ChapterListAdapter.selectedPhase + 1, stage.isGameClear ? GameState.WIN.ordinal() : GameState.LOSE.ordinal())
                    , MapStringUtil.mapToString(Cache.formation));
            Page.jump(context, BattleFinishPage.class);
        }
    }
    public void fetch() {
        try {
            //load enemy formation
            final JSONArray arr = new JSONArray(HttpClient.doGetShort(Urls.getChapterPhaseDetails(ChapterListAdapter.selectedChapter + 1, ChapterListAdapter.selectedPhase + 1)));
            for(int i = 0; i < arr.length(); ++i) {
                final JSONObject posInfo = arr.getJSONObject(i);
                final int pos = posInfo.getInt("positionId");
                final int x = 500 + pos%5*100;
                final int y = 100 + pos/5*100;
                stage.addUnit(new Enemy(MyUnit.loadAsEnemy(Urls.getCard(posInfo.getInt("cardId")))).respawn(x, y));
            }
            //load friend formation
            for(Map.Entry<Integer, Integer> posAndOwnCardId : Cache.formation.entrySet()) {
                final int pos = posAndOwnCardId.getKey();
                final Cache.OwnCard ownCard = Cache.ownCards.get(posAndOwnCardId.getValue());
                stage.addUnit(new Knowledge(ownCard).respawn(100 + pos%5*100, 100 + pos/5*100));
            }
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
