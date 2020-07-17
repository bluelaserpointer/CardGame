package com.example.myapplicationtest1.game.physics;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {
    private double randomSmallDouble() {
        return 999*(Math.random() - 0.5);
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
}