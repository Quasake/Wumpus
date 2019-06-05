package me.quasar.wumpus.objects.items;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;

public abstract class Item {
	protected BufferedImage texture = Assets.nullTile;

	protected String name = "null";

	public Item (BufferedImage texture, String name) {
		this.texture = texture;
		this.name = name;
	}

	public BufferedImage getTexture ( ) {
		return texture;
	}

	public String getName ( ) {
		return name;
	}

}
