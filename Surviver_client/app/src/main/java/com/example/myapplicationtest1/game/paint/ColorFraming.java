package com.example.myapplicationtest1.game.paint;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.rect.RectPaint;

/**
 * A minor PaintScript subclass which draw a rectangle's border line with specified color.
 * Useful when debugging.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ColorFraming extends RectPaint {
	private final Paint paint = new Paint();
	public ColorFraming(int color, float width) {
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(width);
		paint.setColor(color);
	}
	public ColorFraming(int color) {
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		GHQ.fillRect(x, y, w, h, paint);
	}
}
