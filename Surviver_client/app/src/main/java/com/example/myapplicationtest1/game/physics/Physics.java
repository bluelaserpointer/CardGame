package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.physics.hitShape.HitShape;

public abstract class Physics implements HitIntractable, HasAngle {
	public static final Physics NULL_PHYSICS = new Physics() {
		@Override
		public HitShape hitShape() {
			return HitShape.NULL_HITSHAPE;
		}
		@Override
		public Point point() {
			return Point.NULL_POINT;
		}
		@Override
		public HitGroup hitGroup() {
			return HitGroup.HIT_NONE;
		}
		@Override
		public Angle angle() {
			return Angle.NULL_ANGLE;
		}
	};
	public Physics setPoint(Point point) {
		return this;
	}
	public Physics setHitShape(HitShape hitShape) {
		return this;
	}
	public Physics setHitRule(HitGroup hitGroup) {
		return this;
	}
}
