package com.example.myapplicationtest1.game.physics.hitShape;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.physics.HasBoundingBox;
import com.example.myapplicationtest1.game.physics.HasPoint;
import com.example.myapplicationtest1.game.physics.Point;

import java.io.Serializable;

/**
 * A primal class for managing object hit area.
 * 
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class HitShape implements HasBoundingBox, Serializable {
	private static final long serialVersionUID = -4544439590281786292L;
	public static final HitShape NULL_HITSHAPE = new HitShape() {
		private static final long serialVersionUID = -4252823940204998087L;
		@Override
		public int width() {
			return 0;
		}
		@Override
		public int height() {
			return 0;
		}
		@Override
		public boolean intersects(HitShape shape) {
			return false;
		}
		@Override
		public boolean boundingBoxIntersectsDot(int x, int y) {
			return false;
		}
		@Override
		public boolean intersectsDot(int x, int y) {
			return false;
		}
		@Override
		public boolean intersectsLine(int x1, int y1, int x2, int y2) {
			return false;
		}
		@Override
		public HitShape clone(HasPoint newOwner) {
			return this;
		}
		@Override
		public void draw(Paint paint) {}
	};

	protected HasPoint owner;
	public HitShape() {
		owner = null; //if no owner has set and intersect judges are executed, NullPointerException will be thrown.
	}
	public HitShape(HasPoint owner) {
		this.owner = owner;
	}
	
	//control
	public HitShape setOwner(HasPoint owner) {
		this.owner = owner;
		return this;
	}
	
	//information
	public abstract boolean intersects(HitShape shape);
	public final boolean intersects(HasHitShape target) {
		final HitShape SHAPE = target.hitShape();
		return SHAPE == null ? false : intersects(SHAPE);
	}
	public final boolean intersects_atNewPoint(double dx, double dy, HitShape shape) {
		point().addXY(dx, dy);
		final boolean JUDGE = intersects(shape);
		point().addXY(-dx, -dy);
		return JUDGE;
	}
	public final boolean intersects_atNewPoint(double dx, double dy, HasHitShape target) {
		final HitShape SHAPE = target.hitShape();
		return SHAPE == null ? false : intersects_atNewPoint(dx, dy, SHAPE);
	}
	public abstract boolean intersectsDot(int x, int y);
	public abstract boolean intersectsLine(int x1, int y1, int x2, int y2);
	public final boolean intersectsLine(Point p1, Point p2) {
		return intersectsLine(p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	
	//tool
	public abstract HitShape clone(HasPoint newOwner);
	public abstract void draw(Paint paint);
	
	//information
	@Override
	public Point point() {
		return owner.point();
	}
	public HasPoint owner() {
		return owner;
	}
	public boolean hasOwner() {
		return owner() != null;
	}
}
