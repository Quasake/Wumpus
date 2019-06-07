package me.quasar.wumpus.objects.inventory;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.ImageButton;
import me.quasar.wumpus.objects.items.Item;
import me.quasar.wumpus.utils.Handler;

public class InventorySlot {
	private float x;
	private float y;
	private Handler handler;

	private Item item;
	private Button slot;

	public InventorySlot (float x, float y, Handler handler) {
		this.x = x;
		this.y = y;

		slot = new ImageButton(x, y, 4, handler);
	}

	public void update ( ) {
		slot.update( );
	}

	public void render (Graphics graphics) {
		slot.render(graphics);

		if (item != null) {
			graphics.drawImage(item.getTexture( ), (int) x - (slot.getWidth( ) / 2), (int) y - (slot.getHeight( ) / 2), null);
		}
	}

	public boolean isClicked ( ) {
		return slot.isClicked( );
	}

	public Item getItem ( ) {
		return item;
	}

	public void setItem (Item item) {
		this.item = item;
	}

	public Button getSlot ( ) {
		return slot;
	}
}
