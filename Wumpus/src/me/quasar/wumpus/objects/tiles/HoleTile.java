package me.quasar.wumpus.objects.tiles;

import me.quasar.wumpus.graphics.Assets;

public class HoleTile extends Tile {

	public HoleTile (float x, float y, boolean hidden) {
		super(x, y, Assets.holeTile, hidden);
	}

}
