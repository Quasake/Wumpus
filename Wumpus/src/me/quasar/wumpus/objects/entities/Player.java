package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Constants;

public class Player extends Entity {
	private Animation currentAnimation;
	private Item[ ] inventory;

	private int torchCount = 0;

	public Player (float x, float y, Map map) {
		super(x, y, map);

		currentAnimation = Assets.playerIdleAnimation;

		inventory = new Item[4];
	}

	@Override
	public void update ( ) {
		updateAnimations( );

		currentAnimation.update( );

		move( );

		if (!moving) {
			updateSurroundingTiles( );

			checkItem( );
		}
	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(currentAnimation.getCurrentFrame( ), (int) x, (int) y, null);

		for (int i = 0; i < inventory.length; i++) {
			graphics.drawImage(Assets.inventorySlot, (int) (Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (inventory.length / 2.0)) + (Constants.IMAGE_WIDTH * i)),
				(Constants.GAME_HEIGHT / 10) * 1, null);
			if (inventory[i] != null) {
				graphics.drawImage(inventory[i].getTexture( ), (int) (Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (inventory.length / 2.0)) + (Constants.IMAGE_WIDTH * i)),
					(Constants.GAME_HEIGHT / 10) * 1, null);
			}
		}
	}

	private void setAnimation (Animation animation) {
		currentAnimation = animation;
	}

	private void updateSurroundingTiles ( ) {
		for (int i = -torchCount; i <= torchCount; i++) {
			for (int j = -torchCount; j <= torchCount; j++) {
				try {
					map.getTile(tileX + i, tileY + j).setHidden(false);
				} catch (Exception e) {
				}
			}
		}

		if (torchCount == 0) {
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
	}

	private void updateAnimations ( ) {
		if (moveCountX > 0) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveRightTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveRightAnimation);
			}
		} else if (moveCountX < 0) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveLeftTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveLeftAnimation);
			}
		} else if (moveCountY > 0) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveDownTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveDownAnimation);
			}
		} else if (moveCountY < 0) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveUpTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveUpAnimation);
			}
		} else if (moveCountX == 0 && moveCountY == 0) {
			if (hasTorch( )) {
				setAnimation(Assets.playerIdleTorchAnimation);
			} else {
				setAnimation(Assets.playerIdleAnimation);
			}
		}
	}

	public void checkItem ( ) {
		if (map.getBoardTile(tileX, tileY).hasItem( )) {
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] == null) {
					if (map.getBoardTile(tileX, tileY).getItem( ).getName( ).equals("torch")) {
						giveTorch( );
					} else {
						inventory[i] = map.getBoardTile(tileX, tileY).getItem( );
					}

					map.getBoardTile(tileX, tileY).setItem(null);
					break;
				}
			}
		}
	}

	public Item[ ] getInventory ( ) {
		return inventory;
	}

	public void giveTorch ( ) {
		torchCount++;
	}

	public boolean hasTorch ( ) {
		return torchCount > 0;
	}

}
