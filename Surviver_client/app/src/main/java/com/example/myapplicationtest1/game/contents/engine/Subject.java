package com.example.myapplicationtest1.game.contents.engine;

import android.graphics.Color;

public enum Subject{
	CHI, MAT, ENG;
	public Subject valueOf(int i) {
		switch(i) {
		case 0:
			return CHI;
		case 1:
			return MAT;
		case 2:
			return ENG;
		default:
			return null;
		}
	}
	public String toCHIString() {
		switch(this) {
		case CHI:
			return "Chinese";
		case MAT:
			return "Mathematics";
		case ENG:
			return "English";
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
	public String imageURL() {
		switch(this) {
		case CHI:
			return "image/CHI_BET.png";
		case MAT:
			return "image/MAT_BET.png";
		case ENG:
			return "image/ENG_BET.png";
		default:
			return null;
		}
	}
}