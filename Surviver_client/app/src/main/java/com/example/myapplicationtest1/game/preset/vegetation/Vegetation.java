package com.example.myapplicationtest1.game.preset.vegetation;

import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.paint.dot.HasDotPaint;
import com.example.myapplicationtest1.game.physics.HasBoundingBox;
import com.example.myapplicationtest1.game.physics.Point;

/**
 * A primal class for managing vegetation object.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Vegetation extends GHQObject implements HasBoundingBox, HasDotPaint {
	protected final DotPaint paintScript;

	//init-physics parameter
	@Override
	protected Point predefine_point() {
		return new Point.IntPoint();
	}
	//init-constructor
	public Vegetation(DotPaint paintScript, int x, int y) {
		point().setXY(x, y);
		this.paintScript = paintScript;
	}
	public Vegetation(DotPaint paintScript, Point point) {
		point().setXY(point);
		this.paintScript = paintScript;
	}
	public Vegetation(Vegetation sample) {
		point().setXY(sample);
		this.paintScript = sample.paintScript;
	}
	//idle
	@Override
	public void paint() {
		super.paint();
		paintScript.dotPaint(point());
	}
	//control
	@Override
	public Vegetation clone() {
		return new Vegetation(this);
	}
	
	//information
	@Override
	public int width() {
		return paintScript.width();
	}
	@Override
	public int height() {
		return paintScript.height();
	}
	@Override
	public final DotPaint getDotPaint() {
		return paintScript;
	}
}
