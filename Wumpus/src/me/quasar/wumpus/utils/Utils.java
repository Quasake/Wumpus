package me.quasar.wumpus.utils;

import me.quasar.wumpus.objects.Map;

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

	public static boolean inTileRange (int tileX1, int tileY1, int tileX2, int tileY2, int range, Map map) {
		if ((Math.abs(tileX1 - tileX2) <= range && Math.abs(tileX1 - tileX2) >= -range) && (Math.abs(tileY1 - tileY2) <= range && Math.abs(tileY1 - tileY2) >= -range)) {
			return true;
		}
		return false;
	}

}
