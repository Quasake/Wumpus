package me.quasar.wumpus.objects.tiles;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;

public class WallTile extends Tile {

	public WallTile (float x, float y, int type, boolean hidden) {
		super(x, y, getImage(type), hidden);
	}

	private static BufferedImage getImage (int type) {
		switch (type) {
			case 0 :
				return Assets.wallTop;
			case 1 :
				return Assets.wallTopRight;
			case 2 :
				return Assets.wallRight;
			case 3 :
				return Assets.wallBottomRight;
			case 4 :
				return Assets.wallBottom;
			case 5 :
				return Assets.wallBottomLeft;
			case 6 :
				return Assets.wallLeft;
			case 7 :
				return Assets.wallTopLeft;
		}
		return Assets.nullTile;
	}

}
