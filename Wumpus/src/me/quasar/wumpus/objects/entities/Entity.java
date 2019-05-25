package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;

public abstract class Entity {
	protected float x;
	protected float y;

	protected int tileX;
	protected int tileY;
	
	protected int moveToTileX;
	protected int moveToTileY;
	protected int moveCountX = 0;
	protected int moveCountY = 0;

	protected Map map;

	public Entity (float x, float y, Map map) {
		this.x = x;
		this.y = y;
		this.map = map;
		
		tileX = (int) ((x / Constants.IMAGE_WIDTH) - Constants.MAP_BORDER);
		tileY = (int) ((y / Constants.IMAGE_HEIGHT) - Constants.MAP_BORDER);
		moveToTileX = tileX;
		moveToTileY = tileY;
	}

	public abstract void update ( );

	public abstract void render (Graphics graphics);

	public void move ( ) {
		if (tileX < moveToTileX) {
			x += Constants.ENTITY_SPEED;
			moveCountX++;
		} else if (tileX > moveToTileX) {
			x -= Constants.ENTITY_SPEED;
			moveCountX--;
		}
		if (tileY < moveToTileY) {
			y -= Constants.ENTITY_SPEED;
			moveCountY--;
		} else if (tileY > moveToTileY) {
			y += Constants.ENTITY_SPEED;
			moveCountY++;
		}
		
		if (Math.abs(moveCountX) * Constants.ENTITY_SPEED == Constants.IMAGE_WIDTH) {
			if (moveCountX > 0) {
				tileX++;
			} else {
				tileX--;
			}
			moveCountX = 0;
		}
		if (Math.abs(moveCountY) * Constants.ENTITY_SPEED == Constants.IMAGE_HEIGHT) {
			if (moveCountY > 0) {
				tileY++;
			} else {
				tileY--;
			}
			moveCountY = 0;
		}
	}

	public void moveUp ( ) {
		if (moveToTileY > 0) {
			moveToTileY--;
		}
	}

	public void moveRight ( ) {
		if (moveToTileX < map.getWidth( ) - 1) {
			moveToTileX++;
		}
	}

	public void moveDown ( ) {
		if (moveToTileY < map.getHeight( ) - 1) {
			moveToTileY++;
		}
	}

	public void moveLeft ( ) {
		if (moveToTileX > 0) {
			moveToTileX--;
		}
	}

	public int getTileX ( ) {
		return tileX;
	}

	public int getTileY ( ) {
		return tileY;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

}
