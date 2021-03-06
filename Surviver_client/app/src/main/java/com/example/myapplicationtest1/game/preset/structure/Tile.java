package com.example.myapplicationtest1.game.preset.structure;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.physics.HasBoundingBox;
import com.example.myapplicationtest1.game.physics.HasPoint;
import com.example.myapplicationtest1.game.physics.Point;
import com.example.myapplicationtest1.game.physics.hitShape.HitShape;

import java.util.BitSet;

public class Tile extends Structure {
	public static final int TILE_SIZE = 25;
	public static int bp_ox = GHQ.NONE, bp_oy,bp_tileSize = TILE_SIZE;
	public class TileHitShape extends HitShape {
		protected final int
			X_TILES,
			Y_TILES;
		protected final BitSet aliveTiles;
		public TileHitShape(HasPoint owner, int x_tiles, int y_tiles) {
			super(owner);
			X_TILES = x_tiles;
			Y_TILES = y_tiles;
			aliveTiles = new BitSet(X_TILES*Y_TILES);
			aliveTiles.set(0, aliveTiles.size());
		}
		public TileHitShape(TileHitShape sample) {
			super(sample.owner);
			X_TILES = sample.X_TILES;
			Y_TILES = sample.Y_TILES;
			aliveTiles = new BitSet(sample.aliveTiles.size());
			aliveTiles.or(sample.aliveTiles);
		}
		private static final long serialVersionUID = -8708171125934571442L;
		@Override
		public boolean intersects(HitShape shape) {
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)) {
						Point point = new Point.IntPoint(point().intX() + xi*TILE_SIZE + TILE_SIZE/2, point().intY() + yi*TILE_SIZE + TILE_SIZE/2);
						if(shape.point().inRangeXY(point, (TILE_SIZE + shape.width())/2, (TILE_SIZE + shape.height())/2))
							return true;
					}
				}
			}
			return false;
		}
		@Override
		public boolean intersectsDot(int x, int y) {
			x -= point().intX();
			y -= point().intY();
			return 0 < x && x < width() && 0 < y && y < height() && aliveTiles.get(x/TILE_SIZE + y/TILE_SIZE*X_TILES);
		}
		@Override
		public boolean intersectsLine(int x1, int y1, int x2, int y2) {
			if(!HasBoundingBox.isLineIntersectRectangle(x1, y1, x2, y2,
					point().intX(), point().intY(),
					point().intX() + X_TILES*TILE_SIZE,
					point().intY() + Y_TILES*TILE_SIZE))
				return false;
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)){
						final int tileX = point().intX() + xi*TILE_SIZE;
						final int tileY = point().intY() + yi*TILE_SIZE;
						if(HasBoundingBox.isLineIntersectRectangle(x1, y1, x2, y2, tileX, tileY, tileX + TILE_SIZE, tileY + TILE_SIZE))
							return true;
					}
				}
			}
			return false;
		}
		@Override
		public void draw(Paint paint) {
			final int x = point().intX(), y = point().intY();
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)) {
						GHQ.fillRect(x + xi*TILE_SIZE, y + yi*TILE_SIZE, TILE_SIZE, TILE_SIZE, paint);
					}
				}
			}
		}
		@Override
		public HitShape clone(HasPoint newOwner) {
			return new TileHitShape(newOwner, X_TILES, Y_TILES);
		}
		public void paint() {
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)){
						final int PX = point().intX() + xi*TILE_SIZE, PY = point().intY() + yi*TILE_SIZE;
						paintCell(PX, PY, TILE_SIZE, TILE_SIZE);
					}
				}
			}
		}
		@Override
		public int width() {
			return X_TILES*TILE_SIZE;
		}
		@Override
		public int height() {
			return Y_TILES*TILE_SIZE;
		}
	}
	public Tile(int ox, int oy, int x_tiles, int y_tiles) {
		physics().setPoint(new Point.IntPoint(ox, oy));
		physics().setHitShape(new TileHitShape(this, x_tiles, y_tiles));
	}
	//role
	@Override
	public void paint() {
		((TileHitShape)hitShape()).paint();
	}
	protected void paintCell(int x, int y, int w, int h) {
		GHQ.fillRect(x, y, w, h, GHQ.generatePaint(Color.WHITE));
		GHQ.fillRect(x, y, w, h, GHQ.generatePaint(Color.GRAY, 3F, Paint.Style.STROKE));
	}
	
	//information
	public int xTiles() {
		return ((TileHitShape)hitShape()).X_TILES;
	}
	public int yTiles() {
		return ((TileHitShape)hitShape()).Y_TILES;
	}
	@Override
	public String name() {
		return "DefaultTile";
	}
	
	public static void blueprint_addOriginPoint(int x, int y) {
		bp_ox = x;
		bp_oy = y;
	}
	public static Tile blueprint_addEndPointAndFlush(int x, int y) {
		//TODO: tile blueprint_addEndPointAndFlush
//		final int x_tiles = Math.abs((x - bp_ox)/bp_tileSize) + 1, y_tiles = Math.abs((y - bp_oy)/bp_tileSize) + 1;
//		final Tile tile = new Tile(bp_ox < GHQ.mouseX() ? bp_ox : bp_ox - x_tiles*bp_tileSize, bp_oy < GHQ.mouseY() ? bp_oy : bp_oy - y_tiles*bp_tileSize, x_tiles, y_tiles);
//		blueprint_clear();
//		return tile;
		return null;
	}
	public static boolean blueprint_hasOriginPoint() {
		return bp_ox != GHQ.NONE;
	}
	public static void blueprint_clear() {
		bp_ox = GHQ.NONE;
		bp_tileSize = TILE_SIZE;
	}
	public static void makeGuiding() {
		//TODO: tile makeGuiding
//		if(bp_ox != GHQ.NONE) {
//			g2.setColor(Color.WHITE);
//			g2.setStroke(GHQ.stroke1);
//			final int MOUSE_X = GHQ.mouseX(), MOUSE_Y = GHQ.mouseY();
//			final int x_tiles = Math.abs((MOUSE_X - bp_ox)/bp_tileSize) + 1, y_tiles = Math.abs((MOUSE_Y - bp_oy)/bp_tileSize) + 1;
//			final int TX = bp_ox < MOUSE_X ? 0 : -x_tiles*bp_tileSize, TY = bp_oy < MOUSE_Y ? 0 : -y_tiles*bp_tileSize;
//			g2.translate(TX, TY);
//			for(int xi = 0;xi < x_tiles;xi++) {
//				for(int yi = 0;yi < y_tiles;yi++)
//					g2.drawRect(bp_ox + xi*bp_tileSize, bp_oy + yi*bp_tileSize, bp_tileSize, bp_tileSize);
//			}
//			g2.translate(-TX, -TY);
//			g2.setColor(Color.ORANGE);
//			g2.setStroke(GHQ.stroke3);
//			g2.drawLine(bp_ox, bp_oy, MOUSE_X, MOUSE_Y);
//		}
	}
}
