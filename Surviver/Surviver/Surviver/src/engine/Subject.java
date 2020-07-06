package engine;

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
		case ENG:
			return "English";
		case MAT:
			return "Mathematics";
		default:
			return "";
		}
	}
}