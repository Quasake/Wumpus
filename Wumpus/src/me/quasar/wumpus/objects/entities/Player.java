package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.objects.Map;

public class Player extends Entity {
	private Animation currentAnimation;

	private boolean hasTorch = false;

	public Player (float x, float y, Map map) {
		super(x, y, map);

		currentAnimation = Assets.playerIdleAnimation;
	}

	@Override
	public void update ( ) {
		updateAnimations( );

		currentAnimation.update( );

		move( );

		updateSurroundingTiles( );
	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(currentAnimation.getCurrentFrame( ), (int) x, (int) y, null);
	}

	private void setAnimation (Animation animation) {
		currentAnimation = animation;
	}

	private void updateSurroundingTiles ( ) {
		map.getBoardTile(tileX, tileY).setHidden(false);
		if (tileX == 0) {
			if (tileY == 0) {
				map.getTile(tileX - 1, tileY).setHidden(false);
				map.getTile(tileX - 1, tileY - 1).setHidden(false);
				map.getTile(tileX, tileY - 1).setHidden(false);
			} else if (tileY == map.getHeight( ) - 1) {
				map.getTile(tileX - 1, tileY).setHidden(false);
				map.getTile(tileX - 1, tileY + 1).setHidden(false);
				map.getTile(tileX, tileY + 1).setHidden(false);
			} else {
				map.getTile(tileX - 1, tileY).setHidden(false);
			}
		} else if (tileX == map.getWidth( ) - 1) {
			if (tileY == 0) {
				map.getTile(tileX, tileY - 1).setHidden(false);
				map.getTile(tileX + 1, tileY - 1).setHidden(false);
				map.getTile(tileX + 1, tileY).setHidden(false);
			} else if (tileY == map.getHeight( ) - 1) {
				map.getTile(tileX + 1, tileY).setHidden(false);
				map.getTile(tileX + 1, tileY + 1).setHidden(false);
				map.getTile(tileX, tileY + 1).setHidden(false);
			} else {
				map.getTile(tileX + 1, tileY).setHidden(false);
			}
		} else {
			if (tileY == 0) {
				map.getTile(tileX, tileY - 1).setHidden(false);
			} else if (tileY == map.getHeight( ) - 1) {
				map.getTile(tileX, tileY + 1).setHidden(false);
			}
		}
	}

	private void updateAnimations ( ) {
		if (moveCountX > 0) {
			if (hasTorch) {
				setAnimation(Assets.playerMoveRightTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveRightAnimation);
			}
		} else if (moveCountX < 0) {
			if (hasTorch) {
				setAnimation(Assets.playerMoveLeftTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveLeftAnimation);
			}
		} else if (moveCountY > 0) {
			if (hasTorch) {
				setAnimation(Assets.playerMoveDownTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveDownAnimation);
			}
		} else if (moveCountY < 0) {
			if (hasTorch) {
				setAnimation(Assets.playerMoveUpTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveUpAnimation);
			}
		} else if (moveCountX == 0 && moveCountY == 0) {
			if (hasTorch) {
				setAnimation(Assets.playerIdleTorchAnimation);
			} else {
				setAnimation(Assets.playerIdleAnimation);
			}
		}
	}

	public void giveTorch ( ) {
		hasTorch = true;
	}

	public boolean hasTorch ( ) {
		return hasTorch;
	}

}
