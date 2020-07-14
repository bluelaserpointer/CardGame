package com.example.myapplicationtest1.game.physics;

public interface HasGridPoint extends HasPoint {
	public abstract GridPoint gridPoint();
	@Override
	public default Point point() {
		return gridPoint();
	}
}
