package com.example.myapplicationtest1.game.physics;

public interface HasPoint {
	public static final HasPoint NULL_HAS_POINT = new HasPoint() {
		private final Point nullCoordinate = new Point.IntPoint();
		@Override
		public Point point() {
			return nullCoordinate;
		}
	};
	public static HasPoint generate(int x, int y) {
		return new HasPoint() {
			private final Point point = new Point.IntPoint(x, y);
			@Override
			public Point point() {
				return point;
			}
		};
	}
	public static HasPoint generate(Point point) {
		return new HasPoint() {
			@Override
			public Point point() {
				return point;
			}
		};
	}
	public abstract Point point();
}
