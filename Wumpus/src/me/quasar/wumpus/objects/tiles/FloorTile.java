package me.quasar.wumpus.objects.tiles;

import me.quasar.wumpus.graphics.Assets;

public class FloorTile extends Tile {

	public FloorTile (float x, float y, boolean hidden) {
		super(x, y, Assets.floorTile, hidden);
	}

}
