package me.quasar.wumpus.objects.tiles;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;

public class FloorTile extends Tile {
	private boolean isHole = false;
	
	public FloorTile (float x, float y, boolean isHole, boolean hidden) {
		super(x, y, getFloorTexture(isHole), hidden);
		
		this.isHole = isHole;
	}
	
	private static BufferedImage getFloorTexture (boolean isHole) {
		if (isHole) {
			return Assets.holeTile;
		}
		return Assets.floorTile;
	}
	
	public void setHole () {
		isHole = true;
		
		setTexture(Assets.holeTile);
	}
	
	public boolean isHole () {
		return isHole;
	}

}
