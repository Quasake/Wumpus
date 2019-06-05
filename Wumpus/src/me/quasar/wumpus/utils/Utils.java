package me.quasar.wumpus.utils;

public class Utils {

	public static boolean inRange (float x, float y, float boxX1, float boxY1, float boxX2, float boxY2) {
		if ((x >= boxX1 && x <= boxX2) && (y >= boxY1 && y <= boxY2)) {
			return true;
		}

		return false;
	}

	public static boolean chance (float percent) {
		return Math.random( ) < percent;
	}

}
