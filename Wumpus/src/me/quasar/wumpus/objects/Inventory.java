package me.quasar.wumpus.objects;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Constants;

public class Inventory {
	private Item[ ] inventory;

	public Inventory (int size) {
		inventory = new Item[size];
	}

	public void update ( ) {

	}

	public void render (Graphics graphics) {
		for (int i = 0; i < inventory.length; i++) {
			int x = (int) (Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH * (i - (inventory.length / 2.0))) + (Constants.IMAGE_WIDTH / 2));

			Renderer.drawImage((inventory[i] == null) ? Assets.inventorySlotDisabled : Assets.inventorySlot, x, Constants.GAME_HEIGHT / 8, true, graphics);
			if (inventory[i] != null) {
				Renderer.drawImage(inventory[i].getTexture( ), x, Constants.GAME_HEIGHT / 8, true, graphics);
			}
		}
	}

	public void addItem (Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
				return;
			}
		}
	}

	public Item getItem (int index) {
		return inventory[index];
	}

	public int getSize ( ) {
		return inventory.length;
	}

	public boolean hasItem (int id) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].getId( ) == id) {
				return true;
			}
		}
		return false;
	}

	public int getItemIndex (int id) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].getId( ) == id) {
				return i;
			}
		}
		return -1;
	}

}
