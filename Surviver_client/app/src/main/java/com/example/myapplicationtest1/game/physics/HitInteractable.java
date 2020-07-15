package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.physics.hitShape.HasHitShape;
import com.example.myapplicationtest1.game.physics.hitShape.HitShape;
import com.example.myapplicationtest1.game.physics.hitShape.RectShape;

public interface HitInteractable extends HasHitShape, HasHitGroup {
	public static final HitInteractable NULL_HIT_INTERACTABLE = new HitInteractable() {
		@Override
		public Point point() {
			return Point.NULL_POINT;
		}
		@Override
		public HitShape hitShape() {
			return null;
		}
		@Override
		public HitGroup hitGroup() {
			return null;
		}
		@Override
		public int width() {
			return 0;
		}
		@Override
		public int height() {
			return 0;
		}
	};
	public default boolean intersects(HitInteractable target) {
		return hitableGroup(target) && hitShape().intersects(target);
	}
	public default boolean intersects_atNewPoint(double dx, double dy, HitInteractable target) {
		return hitableGroup(target) && hitShape().intersects_atNewPoint(dx, dy, target);
	}
	public default boolean intersectsDot(HitGroup targetGroup, int x, int y) {
		return hitGroup().hitableWith(targetGroup) && hitShape().boundingBoxIntersectsDot(x, y);
	}
	public default boolean intersectsLine(HitGroup targetGroup, int x1, int y1, int x2, int y2) {
		return hitGroup().hitableWith(targetGroup) && hitShape().intersectsLine(x1, y1, x2, y2);
	}
	public default boolean intersectsLine(HitGroup targetGroup, Point p1, Point p2) {
		return intersectsLine(targetGroup, p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public default boolean intersectsLine(HitGroup targetGroup, HasPoint object1, HasPoint object2) {
		return object1 == null || object2 == null ? false : intersectsLine(targetGroup, object1.point(), object2.point());
	}
	public default boolean intersectsRect(HitGroup targetGroup, int x, int y, int w, int h) {
		return hitGroup().hitableWith(targetGroup) && hitShape().intersects(new RectShape(x, y, w, h));
	}
}