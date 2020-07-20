package com.example.myapplicationtest1.game.physics.direction;

import com.example.myapplicationtest1.game.physics.Point;

import java.util.Random;

public enum Direction4 implements MonoDirection {
	W, D, S, A;
	public boolean isVert() {
		return this == W || this == S;
	}
	public boolean isHorz() {
		return this == A || this == D;
	}
	public boolean isVH(DirectionVH vh) {
		return vh.isVert() ? isVert() : isHorz();
	}
	public int x() {
		switch(this) {
		case D:
			return 1;
		case A:
			return -1;
		default:
			return 0;
		}
	}
	public int y() {
		switch(this) {
		case S:
			return 1;
		case W:
			return -1;
		default:
			return 0;
		}
	}
	public Direction4 left() {
		switch(this) {
		case W:
			return A;
		case D:
			return W;
		case S:
			return D;
		case A:
			return S;
		default:
			return null;
		}
	}
	public Direction4 right() {
		switch(this) {
		case W:
			return D;
		case D:
			return S;
		case S:
			return A;
		case A:
			return W;
		default:
			return null;
		}
	}
	public Direction4 back() {
		switch(this) {
		case W:
			return S;
		case D:
			return A;
		case S:
			return W;
		case A:
			return D;
		default:
			return null;
		}
	}
	@Override
	public boolean isNone() {
		return false;
	}
	@Override
	public double angle() {
		switch(this) {
		case W:
			return -Math.PI/2;
		case D:
			return 0;
		case S:
			return Math.PI/2;
		case A:
			return Math.PI;
		default:
			return 0;
		}
	}
	@Override
	public double ex() {
		return x();
	}
	@Override
	public double ey() {
		return y();
	}
	public static Direction4 random() {
		return random(new Random());
	}
	public static Direction4 random(Random random) {
		return Direction4.values()[random.nextInt(4)];
	}
}