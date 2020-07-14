package com.example.myapplicationtest1.game.physics;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.preset.effect.Effect;
import com.example.myapplicationtest1.game.preset.effect.debugEffect.DebugEffect;

import java.util.LinkedList;

public class Route {
	final LinkedList<Point> points;
	final LinkedList<Effect> debugEffects = new LinkedList<Effect>();
	public Route(LinkedList<Point> points) {
		this.points = points;
	}
	public Point removeNext() {
		return points.isEmpty() ? null : points.removeLast();
	}
	public Point peek() {
		return points.isEmpty() ? null : points.peekLast();
	}
	public boolean isEmpty() {
		return points.isEmpty();
	}
	public void clear() {
		points.clear();
	}
	public void setDebugEffect(int color, float width) {
		Point prevPoint = null;
		for(Point nowPoint : points) {
			if(prevPoint == null) {
				debugEffects.add(DebugEffect.setLine(color, width, nowPoint.intX(), nowPoint.intY(), nowPoint.intX(), nowPoint.intY() - 10));
			}else {
				debugEffects.add(DebugEffect.setLine(color, width, prevPoint, nowPoint));
			}
			prevPoint = nowPoint;
		}
	}
	public void removeDebugEffect() {
		while(!debugEffects.isEmpty())
			debugEffects.remove().claimDeleteFromStage();
	}
	public void debugPaint(int color, float width) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(width);
		Point prevPoint = null;
		for(Point nowPoint : points) {
			if(prevPoint == null) {
				GHQ.getTargetCanvas().drawLine(nowPoint.intX(), nowPoint.intY(), nowPoint.intX(), nowPoint.intY() - 10, paint);
			}else {
				GHQ.getTargetCanvas().drawLine(prevPoint.intX(), prevPoint.intY(), nowPoint.intX(), nowPoint.intY(), paint);
			}
			prevPoint = nowPoint;
		}
	}
}
