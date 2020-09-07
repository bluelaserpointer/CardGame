package com.example.myapplicationtest1.game.contents.engine;

import android.graphics.Color;
import android.graphics.Paint;

public enum Subject {
	CHI("语文", Color.RED),
	MAT("数学", Color.GREEN),
	ENG("英语", Color.BLUE);
	public final String name;
	public final int color;
	public final Paint textPaint = new Paint();
	Subject(String name, int color) {
		this.name = name;
		this.color = color;
		textPaint.setColor(color);
		textPaint.setTextSize(40);
	}
}