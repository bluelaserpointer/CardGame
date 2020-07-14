package com.example.myapplicationtest1.game.preset.structure;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.physics.Point;
import com.example.myapplicationtest1.game.physics.hitShape.MyPolygon;

import java.util.ArrayList;

public class Terrain extends Structure {
	public static ArrayList<Integer> bppx = new ArrayList<Integer>(), bppy = new ArrayList<Integer>();
	
	public Terrain() {
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(new MyPolygon(this, new Point.IntPoint[0]));
	}
	public Terrain(int[] px, int[] py) {
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(new MyPolygon(this, px, py));
	}
	public Terrain(Point[] points) {
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(new MyPolygon(this, points));
	}
	public Terrain(MyPolygon polygon) {
		physics().setPoint(new Point.IntPoint());
		physics().setHitShape(polygon);
	}

	//role
	@Override
	public void paint() {
		hitShape().draw(GHQ.generatePaint(Color.LTGRAY, 3F, Paint.Style.STROKE));
	}
	public static final void makeGuiding() {
		if(bppx.size() == 0)
			return;
		//TODO: guding
//		//lines
//		g2.setColor(Color.WHITE);
//		g2.setStroke(GHQ.stroke1);
//		for(int i = 0;i < bppx.size() - 1;i++)
//			g2.drawLine(bppx.get(i), bppy.get(i), bppx.get(i + 1), bppy.get(i + 1));
//		//dots
//		g2.setColor(Color.ORANGE);
//		g2.setStroke(GHQ.stroke3);
//		for(int i = 0;i < bppx.size();i++)
//			g2.drawRect(bppx.get(i) - 4, bppy.get(i) - 4, 8, 8);
	}
	
	//information
	@Override
	public String name() {
		return "DefaultTerrain";
	}
	
	//create-with-blueprint
	public static boolean blueprint_isOriginPoint(int x,int y) {
		return bppx.size() >= 3 && x == bppx.get(0) && y == bppy.get(0);
	}
	public static void blueprint_addPoint(int x,int y) {
		bppx.add(x);
		bppy.add(y);
	}
	public static void blueprint_clear() {
		bppx.clear();
		bppy.clear();
	}
	public static Terrain blueprint_flush() {
		final int[] rx = new int[bppx.size()], ry = new int[rx.length];
		for(int i = 0;i < rx.length;i++) {
			rx[i] = bppx.get(i);
			ry[i] = bppy.get(i);
		}
		bppx.clear();
		bppy.clear();
		return new Terrain(rx, ry);
	}
}
