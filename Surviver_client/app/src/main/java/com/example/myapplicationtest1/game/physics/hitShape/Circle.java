package com.example.myapplicationtest1.game.physics.hitShape;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.physics.HasPoint;
import com.example.myapplicationtest1.game.preset.structure.Tile;

public class Circle extends HitShape {
	private static final long serialVersionUID = -1809578801160258098L;
	public int radius;
	
	public Circle(HasPoint owner, int radius) {
		super(owner);
		this.radius = radius;
	}
	//init
	public Circle setRadius(int radius) {
		this.radius = radius;
		return this;
	}
	@Override
	public boolean intersects(HitShape shape) {
		if(shape instanceof Circle) {
			final double DR = radius + ((Circle)shape).radius;
			return point().distanceSq(shape.point()) < DR*DR;
		}else if(shape instanceof Square) {
			// TODO lacking strictness
			return point().inRangeXY(shape.point(), radius + ((Square)shape).side/2);
		}else if(shape instanceof RectShape) {
			// TODO lacking strictness
			return point().inRangeXY(shape.point(), radius + ((RectShape)shape).width/2, radius + ((RectShape)shape).height/2);
		}else if(shape instanceof Tile.TileHitShape) {
			// TODO lacking strictness
			return ((Tile.TileHitShape)shape).intersects(this);
		}else {
			System.out.println("unhandled type: " + this.getClass().getName() + " against " + shape.getClass().getName());
		}
		return false;
	}
	@Override
	public boolean intersectsDot(int x, int y) {
		return point().inRange(x, y, radius);
	}
	@Override
	public boolean intersectsLine(int x1, int y1, int x2, int y2) {
		//TODO: circle with line intersects
		//return Line2D.ptLineDistSq(x1, y1, x2, y2, point().intX(), point().intY()) < radius*radius;
		return false;
	}
	@Override
	public void draw(Paint paint) {
		GHQ.fillCircle(point(), radius, paint);
	}

	//tool
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new Circle(newOwner, radius);
	}
	
	//information
	public int radius() {
		return radius;
	}
	@Override
	public int width() {
		return radius*2;
	}
	@Override
	public int height() {
		return radius*2;
	}
}
