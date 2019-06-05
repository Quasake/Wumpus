package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.input.GameManager;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.items.Compass;
import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Constants;

public class Player extends Entity {
	private Item[ ] inventory;

	private int torchCount = 0;

	private String weapon = "";
	private Arrow arrow;

	public Player (float x, float y, Map map) {
		super(x, y, map);

		currentAnimation = Assets.playerIdleAnimation;

		inventory = new Item[4];
		inventory[0] = new Compass( );
	}

	@Override
	public void update ( ) {
		currentAnimation.update( );

		if (arrow != null) {
			arrow.update( );

			if (arrow.isDestroyed( )) {
				arrow = null;
			}
		}

		move( );

		if (!updated && !moving) {
			updateSurroundingTiles( );
			checkItem( );

			updated = true;
		}
	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(currentAnimation.getCurrentFrame( ), (int) x, (int) y, null);

		if (arrow != null) {
			arrow.render(graphics);
		}

		for (int i = 0; i < inventory.length; i++) {
			graphics.drawImage(Assets.inventorySlot, (int) (Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (inventory.length / 2.0)) + (Constants.IMAGE_WIDTH * i)),
				(Constants.GAME_HEIGHT / 10) * 1, null);
			if (inventory[i] != null) {
				graphics.drawImage(inventory[i].getTexture( ), (int) (Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (inventory.length / 2.0)) + (Constants.IMAGE_WIDTH * i)),
					(Constants.GAME_HEIGHT / 10) * 1, null);
			}
		}
	}

	private void updateSurroundingTiles ( ) {
		for (int i = -torchCount; i <= torchCount; i++) {
			for (int j = -torchCount; j <= torchCount; j++) {
				try {
					map.getGameTile(tileX + i, tileY + j).setHidden(false);

					if (tileX + i == 0) {
						if (tileY + j == 0) {
							map.getGameTile(tileX + i - 1, tileY + j).setHidden(false);
							map.getGameTile(tileX + i - 1, tileY + j - 1).setHidden(false);
							map.getGameTile(tileX + i, tileY + j - 1).setHidden(false);
						} else if (tileY + j == map.getHeight( ) - 1) {
							map.getGameTile(tileX + i - 1, tileY + j).setHidden(false);
							map.getGameTile(tileX + i - 1, tileY + j + 1).setHidden(false);
							map.getGameTile(tileX + i, tileY + j + 1).setHidden(false);
						} else {
							map.getGameTile(tileX + i - 1, tileY + j).setHidden(false);
						}
					} else if (tileX + i == map.getWidth( ) - 1) {
						if (tileY + j == 0) {
							map.getGameTile(tileX + i, tileY + j - 1).setHidden(false);
							map.getGameTile(tileX + i + 1, tileY + j - 1).setHidden(false);
							map.getGameTile(tileX + i + 1, tileY + j).setHidden(false);
						} else if (tileY + j == map.getHeight( ) - 1) {
							map.getGameTile(tileX + i + 1, tileY + j).setHidden(false);
							map.getGameTile(tileX + i + 1, tileY + j + 1).setHidden(false);
							map.getGameTile(tileX + i, tileY + j + 1).setHidden(false);
						} else {
							map.getGameTile(tileX + i + 1, tileY + j).setHidden(false);
						}
					} else {
						if (tileY + j == 0) {
							map.getGameTile(tileX + i, tileY + j - 1).setHidden(false);
						} else if (tileY + j == map.getHeight( ) - 1) {
							map.getGameTile(tileX + i, tileY + j + 1).setHidden(false);
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	protected void updateAnimations ( ) {
		if (moveToTileX > tileX) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveRightTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveRightAnimation);
			}
		} else if (moveToTileX < tileX) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveLeftTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveLeftAnimation);
			}
		} else if (moveToTileY > tileY) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveDownTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveDownAnimation);
			}
		} else if (moveToTileY < tileY) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveUpTorchAnimation);
			} else {
				setAnimation(Assets.playerMoveUpAnimation);
			}
		} else {
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
							weapon = item.getName( );
						}
					}

					map.getBoardTile(tileX, tileY).setItem(null);
					break;
				}
			}
		}
	}

	public boolean searchInventory (String itemName) {
		for (Item item : inventory) {
			if (item != null && item.getName( ).equals(itemName)) {
				return true;
			}
		}
		return false;
	}

	public int getIndexOfItem (String itemName) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null && inventory[i].getName( ).equals(itemName)) {
				return i;
			}
		}
		return -1;
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

	public void shootArrow (Arrow arrow) {
		this.arrow = arrow;
	}

	public Arrow getArrow ( ) {
		return arrow;
	}

	public String getWeapon ( ) {
		return weapon;
	}

	public boolean hasWeapon ( ) {
		return !weapon.equals("");
	}

	public int getTorchNumber ( ) {
		return torchCount;
	}

}
