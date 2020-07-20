package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.physics.hitShape.HasHitShape;
import com.example.myapplicationtest1.game.physics.hitShape.HitShape;
import com.example.myapplicationtest1.game.physics.hitShape.RectShape;

public interface HitIntractable extends HasHitShape, HasHitGroup {
	public default boolean intersects(HitIntractable target) {
		return hitableGroup(target) && hitShape().intersects(target);
	}
	public default boolean intersectsLine(HitGroup targetGroup, int x1, int y1, int x2, int y2) {
		return hitGroup().hitableWith(targetGroup) && hitShape().intersectsLine(x1, y1, x2, y2);
	}
	public default boolean intersectsRect(HitGroup targetGroup, int x, int y, int w, int h) {
		return hitGroup().hitableWith(targetGroup) && hitShape().intersects(new RectShape(x, y, w, h));
	}
}
