package me.quasar.wumpus.objects.game;

import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Utils;

public class GameUtils {
	public static void uncoverTiles (int tileX, int tileY, Player player, Map map) {
		for (int x = -player.getTorchCount( ) + tileX; x <= player.getTorchCount( ) + tileX; x++) {
			for (int y = -player.getTorchCount( ) + tileY; y <= player.getTorchCount( ) + tileY; y++) {
				try {
					map.getTile(x, y).setHidden(false);

					if (x == Constants.MAP_BORDER) {
						if (y == Constants.MAP_BORDER) {
							map.getTile(x - 1, y).setHidden(false);
							map.getTile(x - 1, y - 1).setHidden(false);
							map.getTile(x, y - 1).setHidden(false);
						} else if (y == Constants.MAP_BORDER + map.getSize( ) - 1) {
							map.getTile(x - 1, y).setHidden(false);
							map.getTile(x - 1, y + 1).setHidden(false);
							map.getTile(x, y + 1).setHidden(false);
						} else {
							map.getTile(x - 1, y).setHidden(false);
						}
					} else if (x == Constants.MAP_BORDER + map.getSize( ) - 1) {
						if (y == Constants.MAP_BORDER) {
							map.getTile(x, y - 1).setHidden(false);
							map.getTile(x + 1, y - 1).setHidden(false);
							map.getTile(x + 1, y).setHidden(false);
						} else if (y == Constants.MAP_BORDER + map.getSize( ) - 1) {
							map.getTile(x + 1, y).setHidden(false);
							map.getTile(x + 1, y + 1).setHidden(false);
							map.getTile(x, y + 1).setHidden(false);
						} else {
							map.getTile(x + 1, y).setHidden(false);
						}
					} else {
						if (y == Constants.MAP_BORDER) {
							map.getTile(x, y - 1).setHidden(false);
						} else if (y == Constants.MAP_BORDER + map.getSize( ) - 1) {
							map.getTile(x, y + 1).setHidden(false);
						}
					}
				} catch (Exception e) {
				}
			}
		}

		if (player.hasFlashLight( )) {
			switch (player.getLastDirectionMoved( )) {
				case Constants.UP :
					for (int i = 1; i <= player.getTileY( ) + 1; i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER - i).setHidden(false);
					}
					break;
				case Constants.RIGHT :
					for (int i = 1; i <= map.getSize( ) - player.getTileX( ); i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER + i, player.getTileY( ) + Constants.MAP_BORDER).setHidden(false);
					}
					break;
				case Constants.DOWN :
					for (int i = 1; i <= map.getSize( ) - player.getTileY( ); i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER + i).setHidden(false);
					}
					break;
				case Constants.LEFT :
					for (int i = 1; i <= player.getTileX( ) + 1; i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER - i, player.getTileY( ) + Constants.MAP_BORDER).setHidden(false);
					}
					break;
			}

		}
	}

	public static void coverTiles (Player player, Map map) {
		for (int x = Constants.MAP_BORDER - 1; x <= Constants.MAP_BORDER + map.getSize( ); x++) {
			for (int y = Constants.MAP_BORDER - 1; y <= Constants.MAP_BORDER + map.getSize( ); y++) {
				if (!Utils.inTileRange(x, y, player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER, player.getTorchCount( ), map)
					&& !Utils.inTileRange(x, y, player.getMoveTileX( ) + Constants.MAP_BORDER, player.getMoveTileY( ) + Constants.MAP_BORDER, player.getTorchCount( ), map)) {
					map.getTile(x, y).setCovered(true);
				} else {
					map.getTile(x, y).setCovered(false);
				}
			}
		}

		if (player.hasFlashLight( )) {
			switch (player.getLastDirectionMoved( )) {
				case Constants.UP :
					for (int i = 1; i <= player.getTileY( ) + 1; i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER - i).setCovered(false);
					}
					break;
				case Constants.RIGHT :
					for (int i = 1; i <= map.getSize( ) - player.getTileX( ); i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER + i, player.getTileY( ) + Constants.MAP_BORDER).setCovered(false);
					}
					break;
				case Constants.DOWN :
					for (int i = 1; i <= map.getSize( ) - player.getTileY( ); i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER + i).setCovered(false);
					}
					break;
				case Constants.LEFT :
					for (int i = 1; i <= player.getTileX( ) + 1; i++) {
						map.getTile(player.getTileX( ) + Constants.MAP_BORDER - i, player.getTileY( ) + Constants.MAP_BORDER).setCovered(false);
					}
					break;
			}

		}
	}
}
