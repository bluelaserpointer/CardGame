package com.example.myapplicationtest1.game.paint;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.line.LinePaint;

public class ColorLine extends LinePaint {
	private final Paint paint = new Paint();
	
	public ColorLine(int color, float width) {
		paint.setColor(color);
		paint.setStrokeWidth(width);
	}
	@Override
	public void linePaint(int x1, int y1, int x2, int y2) {
		GHQ.getTargetCanvas().drawLine(x1, y1, x2, y2, paint);
	}
}
