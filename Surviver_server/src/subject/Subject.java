package subject;

import java.awt.Color;

/**
 * 卡片的派系（科目）
 * @author bluelaserpointer
 */
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
	public Color color() {
		switch(this) {
		case CHI:
			return Color.RED;
		case MAT:
			return Color.GREEN;
		case ENG:
			return Color.BLUE;
		default:
			return null;
		}
	}
}