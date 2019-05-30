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

		if (!hidden) {
			updateAnimations( );
		}
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
			int randomDirection = Constants.RANDOM.nextInt(4);
			switch (randomDirection) {
				case 0 :
					moved = moveUp( );
					break;
				case 1 :
					moved = moveRight( );
					break;
				case 2 :
					moved = moveDown( );
					break;
				case 3 :
					moved = moveLeft( );
					break;
			}
		}
	}

	@Override
	protected void updateAnimations ( ) {
		if (moveCountX > 0) {
			setAnimation(Assets.wumpusMoveRightAnimation);
		} else if (moveCountX < 0) {
			setAnimation(Assets.wumpusMoveLeftAnimation);
		} else if (moveCountY > 0) {
			setAnimation(Assets.wumpusMoveDownAnimation);
		} else if (moveCountY < 0) {
			setAnimation(Assets.wumpusMoveUpAnimation);
		} else if (moveCountX == 0 && moveCountY == 0) {
			setAnimation(Assets.wumpusIdleAnimation);
		}
	}

	public boolean isHidden ( ) {
		return map.getBoardTile(moveToTileX, moveToTileY).getHidden( );
	}

}
