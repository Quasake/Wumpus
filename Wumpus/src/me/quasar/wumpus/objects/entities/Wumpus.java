package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;

public class Wumpus extends Entity {

	public Wumpus (float x, float y, Map map) {
		super(x, y, map);

		currentAnimation = Assets.wumpusIdleAnimation;
	}

	@Override
	public void update ( ) {
		currentAnimation.update( );
		
		move( );

		if (!map.getBoardTile(tileX, tileY).getHidden( )) {
			updateAnimations( );
		}
	}

	@Override
	public void render (Graphics graphics) {
		if (!map.getBoardTile(tileX, tileY).getHidden( )) {
			graphics.drawImage(currentAnimation.getCurrentFrame( ), (int) x, (int) y, null);
		}
	}

	public void moveRandomly ( ) {
		int randomDirection = Constants.RANDOM.nextInt(4);
		switch (randomDirection) {
			case 0 :
				moveUp();
				break;
			case 1 :
				moveRight();
				break;
			case 2 :
				moveDown();
				break;
			case 3 :
				moveLeft();
				break;
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

}
