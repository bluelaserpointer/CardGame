package lotte;

import subject.Subject;

/**
 * 运算抽卡的逻辑
 * @author bluelaserpointer
 */
public final class Lotte {
	/**
	 * 卡牌的稀有度，目前总共为N、R、SR、SSR、UR的五种
	 */
	static final int RARITY_AMOUNT = 5;
	/**
	 * 抽卡池，需从数据库获取卡牌信息
	 */
	static final int[][][] lotteSpring
		= new int[Subject.values().length][RARITY_AMOUNT][]; //subject, rarity
	/**
	 * 根据投入的资源数量，返回抽到的卡片。
	 * @param chi 投入的语文知识点数
	 * @param mat 投入的数学知识点数
	 * @param eng 投入的英文知识点数
	 * @return 抽到的卡片ID
	 * @author JunHanaizumi
	 */
	public static final int lotte(int chi, int mat, int eng) {
		final int SUM = (chi + mat + eng)/100;
		final int RAND = (int)(Math.random()*1000.0);
		int rarity;
		if(SUM <= 5) {
			if(RAND < 900 - 10*SUM)
				rarity = 0;
			else
				rarity = 1;
		}else if(SUM <= 20) {
			if(RAND < 700 - 10*SUM)
				rarity = 0;
			else if(RAND < 900 - 3*SUM)
				rarity = 1;
			else
				rarity = 2;
		}else if(SUM <= 50) {
			if(RAND < 600 - 10*SUM)
				rarity = 0;
			else if(RAND < 800 - 4*SUM)
				rarity = 1;
			else if(RAND < 950 - 1*SUM)
				rarity = 2;
			else
				rarity = 3;
		}else if(SUM <= 100) {
			if(RAND < 700 - 6*SUM)
				rarity = 1;
			else if(RAND < 1500 - 10*SUM)
				rarity = 2;
			else if(RAND < 1150 - 3*SUM)
				rarity = 3;
			else
				rarity = 4;
		}else {
			System.out.println("<!>too much bet bug");
			rarity = 4;
		}
		final int RAND2 = (int)(Math.random()*SUM);
		int subject;
		if(RAND2 < chi)
			subject = Subject.CHI.ordinal();
		else if(RAND2 < chi + mat)
			subject = Subject.MAT.ordinal();
		else
			subject = Subject.ENG.ordinal();
		final int[] group = lotteSpring[subject][rarity];
		return group[(int)(Math.random()*group.length)];
	}
}