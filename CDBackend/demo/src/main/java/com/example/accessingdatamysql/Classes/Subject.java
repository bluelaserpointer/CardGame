package com.example.accessingdatamysql.Classes;

import java.awt.Color;

public enum Subject {
	CHI, MAT, ENG;

	public String toCHIString() {
		switch (this) {
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

}