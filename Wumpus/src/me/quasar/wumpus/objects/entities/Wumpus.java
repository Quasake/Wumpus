package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;

public class Wumpus extends Entity {
	private boolean hidden;

	public Wumpus (float x, float y, Map map) {
		super(x, y, map);

		currentAnimation = Assets.wumpusIdleAnimation;
	}

	@Override
	public void update ( ) {
		currentAnimation.update( );

		hidden = map.getBoardTile(moveToTileX, moveToTileY).getHidden( ) && map.getBoardTile(tileX, tileY).getHidden( );

		move( );
	}

	@Override
	public void render (Graphics graphics) {
		if (!hidden) {
			graphics.drawImage(currentAnimation.getCurrentFrame( ), (int) x, (int) y, null);
		}
	}

	public void moveRandomly ( ) {
		boolean moved = false;

		while (!moved) {
			int randomDirection = Constants.RANDOM.nextInt(5);
			switch (randomDirection) {
				case 0 :
					moved = moveUp(true);
					break;
				case 1 :
					moved = moveRight(true);
					break;
				case 2 :
					moved = moveDown(true);
					break;
				case 3 :
					moved = moveLeft(true);
					break;
				case 4 :
					moved = true;
					break;
			}
		}
	}

	@Override
	protected void updateAnimations ( ) {
		if (moveToTileX > tileX) {
			setAnimation(Assets.wumpusMoveRightAnimation);
		} else if (moveToTileX < tileX) {
			setAnimation(Assets.wumpusMoveLeftAnimation);
		} else if (moveToTileY > tileY) {
			setAnimation(Assets.wumpusMoveDownAnimation);
		} else if (moveToTileY < tileY) {
			setAnimation(Assets.wumpusMoveUpAnimation);
		} else {
			setAnimation(Assets.wumpusIdleAnimation);
		}
	}

	public boolean isHidden ( ) {
		return map.getBoardTile(moveToTileX, moveToTileY).getHidden( );
	}

}
