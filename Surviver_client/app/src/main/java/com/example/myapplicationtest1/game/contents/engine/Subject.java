package com.example.myapplicationtest1.game.contents.engine;

import android.graphics.Color;

public enum Subject{
	CHI, MAT, ENG;
	public String toCHIString() {
		switch(this) {
		case CHI:
			return "语文";
		case MAT:
			return "数学";
		case ENG:
			return "英语";
		default:
			return "";
		}
	}
	public int color() {
		switch(this) {
		case CHI:
			return Color.RED;
		case MAT:
			return Color.GREEN;
		case ENG:
			return Color.BLUE;
		default:
			return -1;
		}
	}
}