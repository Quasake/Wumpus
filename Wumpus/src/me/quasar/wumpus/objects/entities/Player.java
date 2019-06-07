package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.inventory.Inventory;
import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class Player extends Entity {
	private int torchCount;

	private Inventory inventory;
	private Arrow arrow;
	private Handler handler;

	private Item weapon;

	public Player (float x, float y, Map map, Handler handler) {
		super(x, y, Constants.ENTITY_SPEED, map);

		this.handler = handler;

		inventory = new Inventory(4, handler);

		animation = Assets.playerIdle;
	}

	@Override
	public void update ( ) {
		animation.update( );

		inventory.update( );

		if (arrow != null && arrow.isDestroyed( )) {
			arrow = null;
		}

		move( );
	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(animation.getCurrentFrame( ), (int) x, (int) y, null);

		inventory.render(graphics);

		if (arrow != null) {
			arrow.render(graphics);
		}
	}

	@Override
	protected void updateAnimations ( ) {
		if (moveTileX > tileX) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveRightTorch);
			} else {
				setAnimation(Assets.playerMoveRight);
			}
		} else if (moveTileX < tileX) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveLeftTorch);
			} else {
				setAnimation(Assets.playerMoveLeft);
			}
		} else if (moveTileY > tileY) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveDownTorch);
			} else {
				setAnimation(Assets.playerMoveDown);
			}
		} else if (moveTileY < tileY) {
			if (hasTorch( )) {
				setAnimation(Assets.playerMoveUpTorch);
			} else {
				setAnimation(Assets.playerMoveUp);
			}
		} else {
			if (hasTorch( )) {
				setAnimation(Assets.playerIdleTorch);
			} else {
				setAnimation(Assets.playerIdle);
			}
		}
	}

	public boolean hasTorch ( ) {
		return torchCount > 0;
	}

	public int getTorchCount ( ) {
		return torchCount;
	}

	public boolean shootArrow (int direction) {
		arrow = new Arrow(x, y, direction, map);

		return true;
	}

	public boolean swingSword (int objectTileX, int objectTileY, int direction) {
		switch (direction) {
			case 0 :
				return (this.tileX - 1 == objectTileX || this.tileX == objectTileX || this.tileX + 1 == objectTileX) && this.tileY - 1 == objectTileY;
			case 1 :
				return (this.tileY - 1 == objectTileY || this.tileY == objectTileY || this.tileY + 1 == objectTileY) && this.tileX + 1 == objectTileX;
			case 2 :
				return (this.tileX - 1 == objectTileX || this.tileX == objectTileX || this.tileX + 1 == objectTileX) && this.tileY + 1 == objectTileY;
			case 3 :
				return (this.tileY - 1 == objectTileY || this.tileY == objectTileY || this.tileY + 1 == objectTileY) && this.tileX - 1 == objectTileX;
		}

		return false;
	}

	public boolean arrowIsDestroyed ( ) {
		return arrow == null;
	}

	public void addItem (Item item) {
		if (item.getId( ) == Constants.ID_BOW || item.getId( ) == Constants.ID_SWORD) {
			weapon = item;
		}

		if (item.getId( ) == Constants.ID_TORCH) {
			torchCount++;
		} else {
			inventory.addItem(item);
		}
	}

	public Inventory getInventory ( ) {
		return inventory;
	}

	public boolean hasWeapon ( ) {
		return weapon != null;
	}

	public Item getWeapon ( ) {
		return weapon;
	}

	public Arrow getArrow ( ) {
		return arrow;
	}

}
