package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.input.GameManager;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Constants;

public class Player extends Entity {
	private Item[ ] inventory;

	private int torchCount = 0;
	
	private boolean hasWeapon = false;

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
	}

	private void updateSurroundingTiles ( ) {
		for (int i = -torchCount; i <= torchCount; i++) {
			for (int j = -torchCount; j <= torchCount; j++) {
				try {
					map.getTile(tileX + i, tileY + j).setHidden(false);

					if (tileX + i == 0) {
						if (tileY + j == 0) {
							map.getTile(tileX + i - 1, tileY + j).setHidden(false);
							map.getTile(tileX + i - 1, tileY + j - 1).setHidden(false);
							map.getTile(tileX + i, tileY + j - 1).setHidden(false);
						} else if (tileY + j == map.getHeight( ) - 1) {
							map.getTile(tileX + i - 1, tileY + j).setHidden(false);
							map.getTile(tileX + i - 1, tileY + j + 1).setHidden(false);
							map.getTile(tileX + i, tileY + j + 1).setHidden(false);
						} else {
							map.getTile(tileX + i - 1, tileY + j).setHidden(false);
						}
					} else if (tileX + i == map.getWidth( ) - 1) {
						if (tileY + j == 0) {
							map.getTile(tileX + i, tileY + j - 1).setHidden(false);
							map.getTile(tileX + i + 1, tileY + j - 1).setHidden(false);
							map.getTile(tileX + i + 1, tileY + j).setHidden(false);
						} else if (tileY + j == map.getHeight( ) - 1) {
							map.getTile(tileX + i + 1, tileY + j).setHidden(false);
							map.getTile(tileX + i + 1, tileY + j + 1).setHidden(false);
							map.getTile(tileX + i, tileY + j + 1).setHidden(false);
						} else {
							map.getTile(tileX + i + 1, tileY + j).setHidden(false);
						}
					} else {
						if (tileY + j == 0) {
							map.getTile(tileX + i, tileY + j - 1).setHidden(false);
						} else if (tileY + j == map.getHeight( ) - 1) {
							map.getTile(tileX + i, tileY + j + 1).setHidden(false);
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	protected void updateAnimations ( ) {
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
		Item item = map.getBoardTile(tileX, tileY).getItem( );
		
		if (item != null) {
			GameManager.addEvent("You found a " + item.getName( ) + "!");
			
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] == null) {
					if (item.getName( ).equals("torch")) {
						giveTorch( );
					} else {
						inventory[i] = item;
						
						if (item.getName( ).equals("bow") || item.getName( ).equals("sword")) {
							hasWeapon = true;
						}
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
	
	public boolean hasWeapon () {
		return hasWeapon;
	}

	public int getTorchNumber ( ) {
		return torchCount;
	}

}
