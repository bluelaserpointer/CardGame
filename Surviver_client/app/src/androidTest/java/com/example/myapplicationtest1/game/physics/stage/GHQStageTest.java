package com.example.myapplicationtest1.game.physics.stage;

import android.graphics.Canvas;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.physics.HitGroup;
import com.example.myapplicationtest1.game.physics.hitShape.Square;
import com.example.myapplicationtest1.game.preset.bullet.Bullet;
import com.example.myapplicationtest1.game.preset.structure.Structure;
import com.example.myapplicationtest1.game.preset.structure.Tile;
import com.example.myapplicationtest1.game.preset.unit.Unit;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class GHQStageTest {
    class TestUnit extends Unit {
        String tag;
        public TestUnit(String tag, int group) {
            this.tag = tag;
            physics().setHitShape(new Square(this, 10));
            physics().setHitRule(new HitGroup(group));
        }
        @Override
        public DotPaint getDotPaint() {
            return DotPaint.BLANK_SCRIPT;
        }
        @Override
        public Unit respawn(int spawnX, int spawnY) {
            return null;
        }
        @Override
        public boolean isAlive() {
            return true;
        }
    }
    class TestBullet extends Bullet {
        public TestBullet(GHQObject shooter) {
            super(shooter);
            physics().setHitShape(new Square(this, 10));
        }
    }
    @Test
    public void testDistanceCompare() {
        GHQStage stage = new GHQStage(1000, 1000);
        GHQ.setStage(stage);
        GHQ.setTargetCanvas(new Canvas());
        TestUnit
                unitA = new TestUnit("A", 0),
                unitB = new TestUnit("B", 0),
                unitC = new TestUnit("C", 1);
        stage.addUnit(unitA).point().setXY(0, 50);
        stage.addUnit(unitB).point().setXY(100, 50);
        stage.addUnit(unitC).point().setXY(200, 50);
        assertEquals("C", ((TestUnit)stage.getNearstEnemy(unitA)).tag);
        assertEquals("B", ((TestUnit)stage.getNearstEnemy(unitC)).tag);
        //bullets
        TestBullet
                bulletA = new TestBullet(unitA),
                bulletB = new TestBullet(bulletA),
                bulletC = new TestBullet(unitC);
        stage.addBullet(bulletA).point().setXY(5,50);
        stage.addBullet(bulletB).point().setXY(105,50);
        stage.addBullet(bulletC).point().setXY(205,50);
        assertEquals("C", ((TestUnit)stage.getNearstVisibleEnemy(bulletB)).tag);
        final List<Unit> enemySearchList = stage.getVisibleEnemies(unitC);
        assertTrue(enemySearchList.contains(unitA));
        assertTrue(enemySearchList.contains(unitB));
        //wall
        assertEquals("C", ((TestUnit)stage.getNearstVisibleEnemy(unitA)).tag);
        Structure structureA = new Tile(100, 0, 5, 5);
        stage.addStructure(structureA).physics().setHitRule(HitGroup.HIT_ALL);
        assertNull(stage.getNearstVisibleEnemy(unitA));
        assertTrue(structureA.intersects(unitC));
        //bullet brake
        bulletA.point().setSpeed_DA(120, 0);
        bulletA.idle();
        assertTrue(bulletA.intersects(structureA));
        assertTrue(bulletA.point().isBlocked());
        assertTrue(bulletA.hasDeleteClaimFromStage());
        //unit approach
        assertFalse(GHQ.stage().hitObstacle_atNewPoint(unitA, (int)40, (int)0));
        unitA.point().approachIfNoObstacles(unitA, unitC.point(), 40);
        assertEquals(40, unitA.point().intX());
        unitA.point().approachIfNoObstacles(unitA, unitC.point(), 60);
        assertEquals(40, unitA.point().intX());
    }
}