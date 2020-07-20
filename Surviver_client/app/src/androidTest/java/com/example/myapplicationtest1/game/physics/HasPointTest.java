package com.example.myapplicationtest1.game.physics;

import org.junit.Test;

import static org.junit.Assert.*;

public class HasPointTest {
    private double randomSmallDouble() {
        return Integer.MAX_VALUE*(Math.random() - 0.5);
    }
    private final double PRECISION = 2.0;

    @Test
    public void testPointGenerate() {
        double x = randomSmallDouble(), y = randomSmallDouble();
        HasPoint hasPoint = HasPoint.generate((int)x, (int)y);
        assertEquals((int)x, hasPoint.point().intX());
        assertEquals((int)y, hasPoint.point().intY());
    }
}