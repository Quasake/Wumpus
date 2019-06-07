package me.quasar.wumpus.objects.inventory;

import java.awt.Graphics;

import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class Inventory {
	private InventorySlot[ ] inventory;
	private Handler handler;

	public Inventory (int size, Handler handler) {
		this.handler = handler;

		inventory = new InventorySlot[size];

		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = new InventorySlot((float) (Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH * (i - (inventory.length / 2.0))) + (Constants.IMAGE_WIDTH / 2)),
				Constants.GAME_HEIGHT / 8, handler);
		}
	}

	public void update ( ) {
		for (int i = 0; i < inventory.length; i++) {
			inventory[i].update( );
		}
	}

	public void render (Graphics graphics) {
		for (int i = 0; i < inventory.length; i++) {
			inventory[i].render(graphics);
		}
	}

	public void addItem (Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].getItem( ) == null) {
				inventory[i].setItem(item);
				return;
			}
		}
	}

	public int getSlotClicked ( ) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].isClicked( ) && inventory[i].getItem( ) != null) {
				return i;
			}
		}

		return -1;
	}

	public Item getItem (int index) {
		return inventory[index].getItem( );
	}

	public int getSize ( ) {
		return inventory.length;
	}

	public boolean hasItem (int id) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].getItem( ).getId( ) == id) {
				return true;
			}
		}
		return false;
	}

	public int getItemIndex (int id) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].getItem( ).getId( ) == id) {
				return i;
			}
		}
		return -1;
	}

}
