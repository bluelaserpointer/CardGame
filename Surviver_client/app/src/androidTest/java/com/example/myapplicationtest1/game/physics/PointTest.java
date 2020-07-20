package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.physics.direction.Direction4;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class PointTest {
    private double randomSmallDouble() {
        return 999*(Math.random() - 0.5);
    }
    private int randomSmallInt() {
        return (int)randomSmallDouble();
    }
    private final double PRECISION = 2.0;
    @Test
    public void testSetterGetter() {
        double x = randomSmallDouble(), y = randomSmallDouble();
        final Point ipoint = new Point.IntPoint((int)x, (int)y);
        final Point dpoint = new Point.DoublePoint(x, y);
        //constructor
        assertEquals((int)x, ipoint.intX());
        assertEquals((int)y, ipoint.intY());
        assertEquals(x, dpoint.doubleX(), PRECISION);
        assertEquals(y, dpoint.doubleY(), PRECISION);
        //XYSetter1
        x = randomSmallDouble(); y = randomSmallDouble();
        ipoint.setX((int)x);
        assertEquals((int)x, ipoint.intX());
        ipoint.setY((int)y);
        assertEquals((int)y, ipoint.intY());
        dpoint.setX(x);
        assertEquals(x, dpoint.intX(), PRECISION);
        dpoint.setY(y);
        assertEquals(y, dpoint.intY(), PRECISION);
        //XYSetter2
        x = randomSmallDouble(); y = randomSmallDouble();
        ipoint.setXY((int)x, (int)y);
        assertEquals((int)x, ipoint.intX());
        assertEquals((int)y, ipoint.intY());
        dpoint.setXY(x, y);
        assertEquals(x, dpoint.doubleX(), PRECISION);
        assertEquals(y, dpoint.doubleY(), PRECISION);
        //XYSetter3
        x = randomSmallDouble(); y = randomSmallDouble();
        ipoint.setAll(new Point.IntPoint((int)x, (int)y));
        assertEquals((int)x, ipoint.intX());
        assertEquals((int)y, ipoint.intY());
        dpoint.setAll(new Point.DoublePoint(x, y));
        assertEquals(x, dpoint.doubleX(), PRECISION);
        assertEquals(y, dpoint.doubleY(), PRECISION);
    }
    @Test
    public void testAdd() {
        double x, y, xAdd, yAdd;
        x = randomSmallDouble(); y = randomSmallDouble();
        xAdd = randomSmallDouble(); yAdd = randomSmallDouble();
        //int add
        Point point = new Point.IntPoint((int)x, (int)y);
        point.addXY((int)xAdd, (int)yAdd);
        assertEquals((int)x + (int)xAdd, point.intX());
        assertEquals((int)y + (int)yAdd, point.intY());
        //double add
        point = new Point.DoublePoint(x, y);
        point.addXY(xAdd, yAdd);
        assertEquals(x + xAdd, point.doubleX(), PRECISION);
        assertEquals(y + yAdd, point.doubleY(), PRECISION);
    }
    @Test
    public void testShift() {
        int x, y, add;
        x = randomSmallInt(); y = randomSmallInt(); add = randomSmallInt();
        Point point = new Point.IntPoint(x, y);
        point.shift(Direction4.A, add);
        assertEquals(x - add, point.intX());
        point.shift(Direction4.D, add);
        assertEquals(x, point.intX());
        point.shift(Direction4.W, add);
        assertEquals(y - add, point.intY());
        point.shift(Direction4.S, add);
        assertEquals(y, point.intY());
    }
    @Test
    public void testDistance() {
        Point point = new Point.IntPoint(-2, -2);
        Point point2 = new Point.IntPoint(2, 2);
        assertFalse(point.inRange(point2, 5));
        assertTrue(point.inRange(point2, 6));
        assertFalse(point2.inRange(point, 5));
        assertTrue(point2.inRange(point, 6));

        assertFalse(point.inRangeXY(point2, 3));
        assertTrue(point.inRangeXY(point2, 4));
        assertFalse(point.inRangeXY(point2, 4, 3));
        assertFalse(point.inRangeXY(point2, 3, 4));
        assertTrue(point.inRangeXY(point2, 4, 4));
    }
    @Test
    public void testDirection() {
        Point point = new Point.IntPoint(0, 0);
        assertTrue(point.checkDirection_just(new Point.IntPoint(-1, 0), Direction4.A));
        assertTrue(point.checkDirection_just(new Point.IntPoint(1, 0), Direction4.D));
        assertTrue(point.checkDirection_just(new Point.IntPoint(0, -1), Direction4.W));
        assertTrue(point.checkDirection_just(new Point.IntPoint(0, 1), Direction4.S));
        assertFalse(point.checkDirection_just(new Point.IntPoint(1, 1), Direction4.A));
    }
    @Test
    public void testAdvancedAdd() {
        Point point = new Dynam(0, 0);
        point.setSpeed_DA(1.0, 0.0);
        point.addXY_allowsMoveAngle(0, 10);
        assertEquals(10, point.intX());
    }
}