package me.quasar.wumpus.objects.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.items.Item;

public abstract class Tile {
	protected float x;
	protected float y;

	protected BufferedImage texture = Assets.nullTile;
	protected Item item = null;

	protected boolean hidden;
	protected boolean hasItem;

	public Tile (float x, float y, BufferedImage texture, boolean hidden) {
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.hidden = hidden;
		setItem(item);
	}

	public void render (Graphics graphics) {
		if (!hidden) {
			graphics.drawImage(texture, (int) x, (int) y, null);

			if (hasItem) {
				graphics.drawImage(item.getTexture( ), (int) x, (int) y, null);
			}
		} else {
			graphics.drawImage(Assets.hiddenTile, (int) x, (int) y, null);
		}
	}

	public BufferedImage getTexture ( ) {
		if (hidden) {
			return Assets.hiddenTile;
		}
		return texture;
	}

	public void setItem (Item item) {
		this.item = item;

		if (this.item != null) {
			hasItem = true;
		} else {
			hasItem = false;
		}
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

	public Item getItem ( ) {
		return item;
	}

	public boolean hasItem ( ) {
		return hasItem;
	}

	public void setHidden (boolean hidden) {
		this.hidden = hidden;
	}

	public boolean getHidden ( ) {
		return hidden;
	}

}
