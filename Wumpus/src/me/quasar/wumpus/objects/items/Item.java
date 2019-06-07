package me.quasar.wumpus.objects.items;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.utils.Constants;

public abstract class Item {
	private BufferedImage texture = Assets.nullTile;
	private int id = Constants.ID_NULL;
	private String name;

	public Item (BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
	}

	public BufferedImage getTexture ( ) {
		return texture;
	}

	public String getName ( ) {
		return name;
	}

	public int getId ( ) {
		return id;
	}

}
