package me.quasar.wumpus.graphics.components.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.utils.Handler;
import me.quasar.wumpus.utils.Utils;

public abstract class Button {
	protected float x;
	protected float y;
	protected int width;
	protected int height;

	protected boolean clicked;
	protected boolean pressed;
	protected boolean disabled = false;
	protected BufferedImage texture = Assets.nullTile;
	protected BufferedImage disabledTexture = Assets.nullTile;

	protected Handler handler;

	public Button (float x, float y, BufferedImage texture, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = texture.getWidth( );
		this.height = texture.getHeight( );
		this.texture = texture;
		this.handler = handler;
	}

	public abstract void update ( );

	public abstract void render (Graphics graphics);

	protected void updateInput ( ) {
		if (!pressed) {
			if (handler.isLeftPressed( )) {
				if (Utils.inRange(handler.getMouseX( ), handler.getMouseY( ), x - (width / 2), y - (height / 2), x + (width / 2), y + (height / 2))) {
					clicked = true;
					pressed = true;
				}
			}
		} else {
			if (clicked) {
				clicked = false;
			}

			if (!handler.isLeftPressed( )) {
				pressed = false;
			}
		}
	}

	protected void drawTexture (Graphics graphics) {
		graphics.drawImage((disabled) ? disabledTexture : texture, (int) x - (width / 2), (int) y - (height / 2), null);
	}

	public boolean isClicked ( ) {
		return clicked;
	}

	public boolean isPressed ( ) {
		return pressed;
	}

	public boolean isDisabled ( ) {
		return disabled;
	}

	public void disable ( ) {
		disabled = true;
	}

	public void enable ( ) {
		disabled = false;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

	public int getWidth ( ) {
		return width;
	}

	public int getHeight ( ) {
		return height;
	}

}
