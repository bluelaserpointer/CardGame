package com.example.myapplicationtest1.game.physics.hitShape;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.physics.HasPoint;

public class Square extends HitShape {
	private static final long serialVersionUID = 8168254451812660305L;
	public static final Square SQUARE_10 = new Square(10);
	protected int side;
	public Square(HasPoint owner, int side) {
		super(owner);
		this.side = side;
	}
	public Square(HasPoint owner) {
		super(owner);
	}
	public Square(int side) {
		this.side = side;
	}
	public Square() {}
	//init
	public Square setSide(int side) {
		this.side = side;
		return this;
	}
	@Override
	public boolean intersects(HitShape shape) {
		if(shape instanceof Square) {
			return point().inRangeXY(shape.point(), (side + ((Square)shape).side)/2);
		}else if(shape instanceof RectShape) {
			return point().inRangeXY(shape.point(), (side + ((RectShape)shape).width)/2, (side + ((RectShape)shape).height)/2);
		}else if(shape instanceof Circle) {
			// TODO lacking strictness
			return point().inRangeXY(shape.point(), (side + ((Circle)shape).radius)/2);
		}else {
			System.out.println("unhandled type: " + this.getClass().getName() + " against " + shape.getClass().getName());
		}
		return false;
	}
	@Override
	public boolean boundingBoxIntersectsDot(int x, int y) {
		return point().intAbsDX(x) < side/2 && point().intAbsDY(y) < side/2;
	}
	@Override
	public boolean intersectsDot(int x, int y) {
		return super.boundingBoxIntersectsDot(x, y);
	}
	@Override
	public boolean intersectsLine(int x1, int y1, int x2, int y2) {
		return super.boundingBoxIntersectsLine(x1, y1, x2, y2);
	}
	@Override
	public void draw(Paint paint) {
		GHQ.fillRect(point().intX() - side/2, point().intY() - side/2, side, side, paint);
	}
	
	//tool
	@Override
	public HitShape clone(HasPoint newOwner) {
		return new Square(newOwner, side);
	}
	
	//information
	public int side() {
		return side;
	}
	@Override
	public int width() {
		return side;
	}
	@Override
	public int height() {
		return side;
	}
}
