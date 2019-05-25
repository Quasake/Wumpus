package me.quasar.wumpus.objects.tiles;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;

public class PoleTile extends Tile {

	public PoleTile (float x, float y, int type) {
		super(x, y, getPoleTexture(type), false);
	}
	
	private static BufferedImage getPoleTexture (int type) {
		switch(type) {
			case 0:
				return Assets.poleTop;
			case 1:
				return Assets.poleMiddle;
			case 2:
				return Assets.poleBottom;
		}
		return Assets.nullTile;
	}

}
