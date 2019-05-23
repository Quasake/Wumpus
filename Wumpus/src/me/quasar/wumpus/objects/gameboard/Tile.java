package me.quasar.wumpus.objects.gameboard;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage texture;

	public Tile (BufferedImage texture) {
		this.texture = texture;
	}

	public BufferedImage getTexture ( ) {
		return texture;
	}

}
