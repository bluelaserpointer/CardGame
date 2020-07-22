package pureAlgorism;

public class LevelSystem {
	/**
	 * Calculate level up requirement
	 * @param currentLv current level
	 * @return required experiment
	 */
	public static int requiredExp(int currentLv) {
		return 100 + currentLv*5;
	}
	/**
	 * Do level up if experiment is enough
	 * @param currentLv current level
	 * @param currentExp current experiment
	 * @return [0]: result level [1]: result experiment
	 */
	public static int[] doLevelUp(int currentLv, int currentExp) {
		do { //supports multiple level up
			final int requiredExp = requiredExp(currentLv);
			if(currentExp > requiredExp) {
				++currentLv;
				currentExp -= requiredExp;
			} else {
				break;
			}
		} while(true);
		return new int[] {currentLv, currentExp};
	}
	/**
	 * Do level up if experiment is enough and result level is under the cap
	 * @param currentLv current level
	 * @param currentExp current experiment
	 * @param limitLv level cap
	 * @return [0]: result level [1]: result experiment
	 */
	public static int[] doLevelUp(int currentLv, int currentExp, int limitLv) {
		while(currentLv <= limitLv) { //supports multiple level up
			final int requiredExp = requiredExp(currentLv);
			if(currentExp > requiredExp) {
				++currentLv;
				currentExp -= requiredExp;
			} else {
				break;
			}
		};
		return new int[] {currentLv, currentExp};
	}
	/**
	 * Calculate current status from base value, enhanced times, and its grow speed.
	 * @param baseValue the status value when it is level 1
	 * @param enhancedTime the upgraded time
	 * @param growSpeed the grow speed of this status for it
	 * @return result status value
	 */
	public static double getCurrentStatus(int baseValue, int enhancedTime, double growSpeed) {
		return (int)(baseValue*growSpeed*enhancedTime);
	}
}
