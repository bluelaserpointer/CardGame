package com.example.myapplicationtest1.game.physics.direction;

import org.junit.Test;

import static org.junit.Assert.*;

public class Direction4Test {
    @Test
    public void testDirectionJudge() {
        assertTrue(Direction4.A.isHorz());
        assertTrue(Direction4.D.isHorz());
        assertTrue(Direction4.W.isVert());
        assertTrue(Direction4.S.isVert());
        assertFalse(Direction4.A.isVert());
        assertFalse(Direction4.D.isVert());
        assertFalse(Direction4.W.isHorz());
        assertFalse(Direction4.S.isHorz());
        assertTrue(Direction4.A.isVH(DirectionVH.HORZ));
        assertTrue(Direction4.D.isVH(DirectionVH.HORZ));
        assertTrue(Direction4.W.isVH(DirectionVH.VERT));
        assertTrue(Direction4.S.isVH(DirectionVH.VERT));
        assertFalse(Direction4.A.isVH(DirectionVH.VERT));
        assertFalse(Direction4.D.isVH(DirectionVH.VERT));
        assertFalse(Direction4.W.isVH(DirectionVH.HORZ));
        assertFalse(Direction4.S.isVH(DirectionVH.HORZ));
    }
    @Test
    public void testUnitVectorXY() {
        assertEquals(-1, Direction4.A.x());
        assertEquals(+1, Direction4.D.x());
        assertEquals(-1, Direction4.W.y());
        assertEquals(+1, Direction4.S.y());
        assertEquals(0, Direction4.A.y());
        assertEquals(0, Direction4.D.y());
        assertEquals(0, Direction4.W.x());
        assertEquals(0, Direction4.S.x());
    }
}