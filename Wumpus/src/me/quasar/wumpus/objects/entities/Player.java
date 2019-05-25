package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.objects.Map;

public class Player extends Entity {
	private Animation playerIdle;
	private Animation playerMoveUp;
	private Animation playerMoveRight;
	private Animation playerMoveDown;
	private Animation playerMoveLeft;

	private Animation playerIdleTorch;
	private Animation playerMoveUpTorch;
	private Animation playerMoveRightTorch;
	private Animation playerMoveDownTorch;
	private Animation playerMoveLeftTorch;
	
	private Animation currentAnimation;

	private boolean hasTorch = false;

	public Player (float x, float y, Map map) {
		super(x, y, map);
		
		playerIdle = new Animation(Assets.playerIdle);
		playerMoveUp = new Animation(Assets.playerMoveUp);
		playerMoveRight = new Animation(Assets.playerMoveRight);
		playerMoveDown = new Animation(Assets.playerMoveDown);
		playerMoveLeft = new Animation(Assets.playerMoveLeft);
		
		playerIdleTorch = new Animation(Assets.playerIdleTorch);
		playerMoveUpTorch = new Animation(Assets.playerMoveUpTorch);
		playerMoveRightTorch = new Animation(Assets.playerMoveRightTorch);
		playerMoveDownTorch = new Animation(Assets.playerMoveDownTorch);
		playerMoveLeftTorch = new Animation(Assets.playerMoveLeftTorch);
		
		currentAnimation = playerIdle;
	}

	@Override
	public void update ( ) {
		if (moveCountX > 0) {
			if (hasTorch) {
				setAnimation(playerMoveRightTorch);
			} else {
				setAnimation(playerMoveRight);
			}
		} else if (moveCountX < 0) {
			if (hasTorch) {
				setAnimation(playerMoveLeftTorch);
			} else {
				setAnimation(playerMoveLeft);
			}
		}
		if (moveCountY > 0) {
			if (hasTorch) {
				setAnimation(playerMoveDownTorch);
			} else {
				setAnimation(playerMoveDown);
			}
		} else if (moveCountY < 0) {
			if (hasTorch) {
				setAnimation(playerMoveUpTorch);
			} else {
				setAnimation(playerMoveUp);
			}
		}
		if (moveCountX == 0 && moveCountY == 0) {
			if (hasTorch) {
				setAnimation(playerIdleTorch);
			} else {
				setAnimation(playerIdle);
			}
		}
		
		currentAnimation.update( );
		
		move();
	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(currentAnimation.getCurrentFrame( ), (int) x, (int) y, null);
	}
	
	private void setAnimation (Animation animation) {
		currentAnimation = animation;
	}

	public void giveTorch ( ) {
		hasTorch = true;
	}

	public boolean hasTorch ( ) {
		return hasTorch;
	}

}
