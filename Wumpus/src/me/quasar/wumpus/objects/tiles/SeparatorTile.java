package me.quasar.wumpus.objects.tiles;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;

public class SeparatorTile extends Tile {

	public SeparatorTile (float x, float y, int type) {
		super(x, y, getImage(type), false);
	}

	private static BufferedImage getImage (int type) {
		switch (type) {
			case 0 :
				return Assets.poleTop;
			case 1 :
				return Assets.poleMiddle;
			case 2 :
				return Assets.poleBottom;
		}
		return Assets.nullTile;
	}

}
