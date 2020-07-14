package com.example.myapplicationtest1.game.physics;

public interface HasHitGroup {
	public abstract HitGroup hitGroup();
	public default boolean hitableGroup(HitGroup hitGroup) {
		return hitGroup().hitableWith(hitGroup);
	}
	public default boolean hitableGroup(HasHitGroup target) {
		return hitableGroup(target.hitGroup());
	}
}
