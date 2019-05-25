package me.quasar.wumpus.objects.tiles;

import me.quasar.wumpus.graphics.Assets;

public class HiddenTile extends Tile {

	public HiddenTile (float x, float y) {
		super(x, y, Assets.hiddenTile, true);
	}

}
