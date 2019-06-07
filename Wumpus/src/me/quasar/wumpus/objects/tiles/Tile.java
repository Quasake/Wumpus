package me.quasar.wumpus.objects.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.items.Item;

public abstract class Tile {
	private float x;
	private float y;
	protected BufferedImage texture = Assets.nullTile;

	private Item item;

	private boolean hidden;
	private boolean covered = false;

	public Tile (float x, float y, BufferedImage texture, boolean hidden) {
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.hidden = hidden;
	}

	public void render (Graphics graphics) {
		graphics.drawImage((hidden) ? Assets.hiddenTile : texture, (int) x, (int) y, null);

		if (!hidden && item != null) {
			graphics.drawImage(item.getTexture( ), (int) x, (int) y, null);
		}

		if (covered) {
			graphics.drawImage(Assets.coveredTile, (int) x, (int) y, null);
		}
	}

	public boolean isHidden ( ) {
		return hidden;
	}

	public void setHidden (boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isCovered ( ) {
		return covered;
	}

	public void setCovered (boolean covered) {
		this.covered = covered;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

	public boolean hasItem ( ) {
		return item != null;
	}

	public Item getItem ( ) {
		return item;
	}

	public void setItem (Item item) {
		this.item = item;
	}

}
