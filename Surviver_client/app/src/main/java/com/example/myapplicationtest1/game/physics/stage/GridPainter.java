package com.example.myapplicationtest1.game.physics.stage;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.calculate.HasGrid;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.rect.RectPaint;
import com.example.myapplicationtest1.game.physics.HasBoundingBox;
import com.example.myapplicationtest1.game.physics.Point;

public class GridPainter implements HasGrid {
	protected final int xGrids, yGrids;
	protected final int gridSize;
	
	public GridPainter(HasBoundingBox stage, int gridSize) {
		this.gridSize = gridSize;
		xGrids = stage.width()/gridSize + 1;
		yGrids = stage.height()/gridSize + 1;
	}
	//control
	public void drawGrid(int xPos, int yPos, Paint paint) {
		GHQ.fillRect(xPos*gridSize, yPos*gridSize, gridSize, gridSize, paint);
	}
	public void paintGrid(RectPaint rectPaint, int xPos, int yPos) {
		rectPaint.rectPaint(xPos*gridSize, yPos*gridSize, gridSize, gridSize);
	}
	//information
	public Point getPosPoint(int xPos, int yPos) {
		return new Point.DoublePoint(xPos*gridSize, yPos*gridSize);
	}
	public int screenLeftXPos() {
		return GHQ.getScreenLeftX_stageCod()/gridSize;
	}
	public int screenTopYPos() {
		return GHQ.getScreenTopY_stageCod()/gridSize;
	}
	public int gridsToFillScreenWidth() {
		return GHQ.getScreenW_stageCod()/gridSize + 2;
	}
	public int gridsToFillScreenHeight() {
		return GHQ.getScreenH_stageCod()/gridSize + 2;
	}
	@Override
	public int gridSize() {
		return gridSize;
	}
	@Override
	public int xGrids() {
		return xGrids;
	}
	@Override
	public int yGrids() {
		return yGrids;
	}
}
