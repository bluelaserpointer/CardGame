package com.example.myapplicationtest1.game.paint.text;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;

public class StringPaint extends DotPaint {
	public final String text;
	private final Paint paint = new Paint();

	public StringPaint(String words) {
		text = words;
	}
	public StringPaint(String words, int color) {
		text = words;
		paint.setColor(color);
	}
	@Override
	public void dotPaint(int x, int y) {
		GHQ.drawStringGHQ(text, x, y, paint);
	}
	@Override
	public void dotPaint_capSize(int x, int y, int maxSize) {
		dotPaint(x, y);
	}

	@Override
	public void dotPaint_rate(int x, int y, double rate) {
		dotPaint(x, y);
	}
	@Override
	public int width() {
		return 0;
	}
	@Override
	public int height() {
		return 0;
	}
}
