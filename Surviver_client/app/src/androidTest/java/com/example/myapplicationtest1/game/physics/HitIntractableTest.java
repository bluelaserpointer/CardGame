package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.physics.hitShape.Circle;
import com.example.myapplicationtest1.game.physics.hitShape.HitShape;
import com.example.myapplicationtest1.game.physics.hitShape.Square;

import org.junit.Test;

import static org.junit.Assert.*;

public class HitIntractableTest {

    @Test
    public void intersects() {
        HitIntractable object1 = new HitIntractable() {
            @Override
            public HitShape hitShape() {
                return new Square(this, 10);
            }
            @Override
            public Point point() {
                return new Point.IntPoint(0, 0);
            }
            @Override
            public HitGroup hitGroup() {
                return HitGroup.HIT_ALL;
            }
        };
        HitIntractable object2 = new HitIntractable() {
            @Override
            public HitShape hitShape() {
                return new Circle(this, 10);
            }
            @Override
            public Point point() {
                return new Point.IntPoint(0, 0);
            }
            @Override
            public HitGroup hitGroup() {
                return HitGroup.HIT_ALL;
            }
        };
        assertTrue(object1.intersects(object2));
    }
}