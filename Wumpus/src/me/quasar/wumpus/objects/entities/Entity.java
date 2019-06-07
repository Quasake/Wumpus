package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.tiles.FloorTile;
import me.quasar.wumpus.utils.Constants;

public abstract class Entity {
	protected float x;
	protected float y;

	protected int tileX;
	protected int tileY;

	protected int moveTileX;
	protected int moveTileY;
	protected int moveCountX;
	protected int moveCountY;
	protected float moveSpeed;
	protected boolean moving;

	protected Animation animation;

	protected Map map;

	public Entity (float x, float y, float moveSpeed, Map map) {
		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed;
		this.map = map;

		tileX = (int) ((x / Constants.IMAGE_WIDTH) - Constants.MAP_BORDER);
		tileY = (int) ((y / Constants.IMAGE_HEIGHT) - Constants.MAP_BORDER);

		moveTileX = tileX;
		moveTileY = tileY;
	}

	public abstract void update ( );

	public abstract void render (Graphics graphics);

	protected abstract void updateAnimations ( );

	protected void move ( ) {
		if (moving) {
			if (moveTileX != tileX) {
				if (tileX < moveTileX) {
					x += moveSpeed;
					moveCountX++;
				} else if (tileX > moveTileX) {
					x -= moveSpeed;
					moveCountX--;
				}

				if (Math.abs(moveCountX) * moveSpeed >= Constants.IMAGE_WIDTH) {
					if (moveCountX > 0) {
						tileX++;
					} else if (moveCountX < 0) {
						tileX--;
					}

					x = Constants.IMAGE_WIDTH * (tileX + Constants.MAP_BORDER);

					updateAnimations( );

					moving = false;
					moveCountX = 0;
				}
			} else if (moveTileY != tileY) {
				if (tileY < moveTileY) {
					y += moveSpeed;
					moveCountY++;
				} else if (tileY > moveTileY) {
					y -= moveSpeed;
					moveCountY--;
				}

				if (Math.abs(moveCountY) * moveSpeed >= Constants.IMAGE_HEIGHT) {
					if (moveCountY > 0) {
						tileY++;
					} else if (moveCountY < 0) {
						tileY--;
					}

					y = Constants.IMAGE_HEIGHT * (tileY + Constants.MAP_BORDER);

					updateAnimations( );

					moving = false;
					moveCountY = 0;
				}
			}
		}
	}

	public boolean moveUp (boolean checkBounds, boolean ignoreHazards) {
		if (!checkBounds || moveTileY > 0) {
			try {
				if (!ignoreHazards && ((FloorTile) map.getTile(tileX + Constants.MAP_BORDER, tileY + Constants.MAP_BORDER - 1)).isHole( )) {
					return false;
				}
			} catch (Exception e) {
			}

			moveTileY--;

			updateAnimations( );

			moving = true;
			return true;
		}
		return false;
	}

	public boolean moveRight (boolean checkBounds, boolean ignoreHazards) {
		if (!checkBounds || moveTileX < map.getSize( ) - 1) {
			try {
				if (!ignoreHazards && ((FloorTile) map.getTile(tileX + Constants.MAP_BORDER + 1, tileY + Constants.MAP_BORDER)).isHole( )) {
					return false;
				}
			} catch (Exception e) {
			}

			moveTileX++;

			updateAnimations( );

			moving = true;
			return true;
		}
		return false;
	}

	public boolean moveDown (boolean checkBounds, boolean ignoreHazards) {
		if (!checkBounds || moveTileY < map.getSize( ) - 1) {
			try {
				if (!ignoreHazards && ((FloorTile) map.getTile(tileX + Constants.MAP_BORDER, tileY + Constants.MAP_BORDER + 1)).isHole( )) {
					return false;
				}
			} catch (Exception e) {
			}

			moveTileY++;

			updateAnimations( );

			moving = true;
			return true;
		}
		return false;
	}

	public boolean moveLeft (boolean checkBounds, boolean ignoreHazards) {
		if (!checkBounds || moveTileX > 0) {
			try {
				if (!ignoreHazards && ((FloorTile) map.getTile(tileX + Constants.MAP_BORDER + 1, tileY + Constants.MAP_BORDER)).isHole( )) {
					return false;
				}
			} catch (Exception e) {
			}

			moveTileX--;

			updateAnimations( );

			moving = true;
			return true;
		}
		return false;
	}

	protected void setAnimation (Animation animation) {
		this.animation = animation;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

	public void setMoveSpeed (float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public float getMoveSpeed ( ) {
		return moveSpeed;
	}

	public int getTileX ( ) {
		return tileX;
	}

	public int getTileY ( ) {
		return tileY;
	}

	public int getMoveTileX ( ) {
		return moveTileX;
	}

	public int getMoveTileY ( ) {
		return moveTileY;
	}

	public boolean isMoving ( ) {
		return moving;
	}

}
