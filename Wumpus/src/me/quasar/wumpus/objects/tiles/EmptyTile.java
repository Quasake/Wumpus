package me.quasar.wumpus.objects.tiles;

import me.quasar.wumpus.graphics.Assets;

public class EmptyTile extends Tile {

	public EmptyTile (float x, float y) {
		super(x, y, Assets.hiddenTile, false);
	}
	
}
