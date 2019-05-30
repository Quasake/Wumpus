package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.resources.Animation;
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
	protected boolean moving = false;

	protected Animation currentAnimation;

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
		} else if (tileY < moveToTileY) {
			y += Constants.ENTITY_SPEED;
			moveCountY++;
		} else if (tileY > moveToTileY) {
			y -= Constants.ENTITY_SPEED;
			moveCountY--;
		}

		if (moving) {
			if (Math.abs(moveCountX) * Constants.ENTITY_SPEED == Constants.IMAGE_WIDTH) {
				if (moveCountX > 0) {
					tileX++;
				} else if (moveCountX < 0) {
					tileX--;
				}
				moving = false;
				moveCountX = 0;
			} else if (Math.abs(moveCountY) * Constants.ENTITY_SPEED == Constants.IMAGE_HEIGHT) {
				if (moveCountY > 0) {
					tileY++;
				} else if (moveCountY < 0) {
					tileY--;
				}
				moving = false;
				moveCountY = 0;
			}
		}
	}

	protected void setAnimation (Animation animation) {
		currentAnimation = animation;
	}

	protected abstract void updateAnimations ( );

	public boolean moveUp ( ) {
		if (moveToTileY > 0) {
			moveToTileY--;
			moving = true;
			return true;
		}
		return false;
	}

	public boolean moveRight ( ) {
		if (moveToTileX < map.getWidth( ) - 1) {
			moveToTileX++;
			moving = true;
			return true;
		}
		return false;
	}

	public boolean moveDown ( ) {
		if (moveToTileY < map.getHeight( ) - 1) {
			moveToTileY++;
			moving = true;
			return true;
		}
		return false;
	}

	public boolean moveLeft ( ) {
		if (moveToTileX > 0) {
			moveToTileX--;
			moving = true;
			return true;
		}
		return false;
	}

	public boolean getMoving ( ) {
		return moving;
	}

	public Map getMap ( ) {
		return map;
	}

	public int getTileX ( ) {
		return tileX;
	}

	public int getTileY ( ) {
		return tileY;
	}

	public int getMoveToTileX ( ) {
		return moveToTileX;
	}

	public int getMoveToTileY ( ) {
		return moveToTileY;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

}
