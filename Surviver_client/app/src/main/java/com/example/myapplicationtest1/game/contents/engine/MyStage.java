package com.example.myapplicationtest1.game.contents.engine;

import android.graphics.Color;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.game.physics.stage.GHQStage;
import com.example.myapplicationtest1.game.preset.unit.Unit;
import com.example.myapplicationtest1.game.storage.TableStorage;

public class MyStage extends GHQStage {
    private ImageFrame battleBGIF = ImageFrame.create(R.drawable.battlebg);
    //formationScreen
    public static TableStorage<Knowledge> formationTS = new TableStorage<>(4, 4, null);
    public static int formationCount;
    //garrageScreen
    public static TableStorage<Knowledge> garrageTS = new TableStorage<>(8, 5, null);
    public static int garrageCount;
    //enmeyFormation
    private static final Enemy.EnemyParameter[][][] enemyFormation = new Enemy.EnemyParameter[10][4][4];
    public MyStage(int width, int height) {
        super(width, height);
    }
    boolean isGameClear;
    boolean isGameOver;
    public void init() {
        super.clear();
        //friend
        for(int x = 0;x < 4;x++) {
            for(int y = 0;y < 4;y++) {
                final Knowledge unit = formationTS.get(x + y*4);
                if(unit != null) {
                    GHQ.stage().addUnit(unit).respawn(100 + x*100, 100 + y*100);
                }
            }
        }
        for(int x = 0;x < enemyFormation[0].length;x++) {
            for(int y = 0;y < enemyFormation[0][x].length;y++) {
                final Enemy.EnemyParameter PARAM = enemyFormation[0][x][y];
                if(PARAM != null)
                    GHQ.stage().addUnit(PARAM.generate()).respawn(500 + x*100, 100 + y*100);
            }
        }
        Enemy.chiCount = Enemy.matCount = Enemy.engCount = 0;
        for(Knowledge unit : formationTS) {
            if(unit == null)
                continue;
            switch(unit.PARAM.SUBJECT) {
                case CHI:
                    ++Enemy.chiCount;
                    break;
                case MAT:
                    ++Enemy.matCount;
                    break;
                case ENG:
                    ++Enemy.engCount;
                    break;
                default:
            }
        }
    }
    @Override
    public void idle() {
        battleBGIF.rectPaint(0, 0, width, height);
        super.idle();
        isGameClear = isGameOver = true;
        for(Unit unit : GHQ.stage().units) {
            if(!isGameClear && !isGameOver)
                break;
            if(isGameClear && unit instanceof Enemy) {
                isGameClear = false;
            }
            if(isGameOver && unit instanceof Knowledge) {
                isGameOver = false;
            }
        }
        //count lest enemy
        if(isGameClear) {
            GHQ.drawStringGHQ("EXAM FINISH", 500, 300, GHQ.generatePaint(Color.RED));
        } else if(isGameOver) {
            GHQ.drawStringGHQ("FAILED...", 500, 300, GHQ.generatePaint(Color.RED));
        }
    }
}
