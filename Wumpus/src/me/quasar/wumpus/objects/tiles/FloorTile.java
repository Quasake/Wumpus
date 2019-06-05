package me.quasar.wumpus.objects.tiles;

import me.quasar.wumpus.graphics.Assets;

public class FloorTile extends Tile {
	private boolean hole;

	public FloorTile (float x, float y, boolean hidden) {
		super(x, y, Assets.floorTile, hidden);
	}

	public boolean isHole ( ) {
		return hole;
	}

	public void setHole (boolean hole) {
		this.hole = hole;
		
		if (hole) {
			texture = Assets.holeTile;
		} else {
			texture = Assets.floorTile;
		}
	}

}
