package com.example.myapplicationtest1.game.paint;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.rect.RectPaint;

/**
 * A minor PaintScript subclass which fill a rectangle with specified color.
 * Useful when debugging.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ColorFilling extends RectPaint {
	private final Paint paint = new Paint();
	/**
	 * @param color The rectangle color.
	 */
	public ColorFilling(int color) {
		paint.setColor(color);
	}
	@Override
	public void rectPaint(int x, int y, int w, int h) {
		GHQ.fillRect(x, y, w, h, paint);
	}
}
