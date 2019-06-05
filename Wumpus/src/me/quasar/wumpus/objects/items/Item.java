package me.quasar.wumpus.objects.items;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.utils.Constants;

public abstract class Item {
	private BufferedImage texture = Assets.nullTile;
	private int id = Constants.ID_NULL;

	public Item (BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
	}

	public BufferedImage getTexture ( ) {
		return texture;
	}

	public int getId ( ) {
		return id;
	}

}
