package me.quasar.wumpus.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class Button {
	private float x;
	private float y;
	private String text;
	private Handler handler;

	private boolean clicked;
	private boolean pressed;
	private boolean disabled = false;
	private BufferedImage[ ] buttonParts;

	public Button (float x, float y, String text, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		setText(text);
	}

	public Button (float x, float y, int type, Handler handler) {
		this.x = x;
		this.y = y;
		this.text = "";
		this.handler = handler;

		buttonParts = new BufferedImage[1];
		switch (type) {
			case 0 :
				buttonParts[0] = Assets.buttonUp;
				break;
			case 1 :
				buttonParts[0] = Assets.buttonRight;
				break;
			case 2 :
				buttonParts[0] = Assets.buttonDown;
				break;
			case 3 :
				buttonParts[0] = Assets.buttonLeft;
				break;
			default :
				buttonParts[0] = Assets.nullTile;
				break;
		}
	}

	public void update ( ) {
		updateButton( );
	}

	public void render (Graphics graphics) {
		for (int i = 0; i < buttonParts.length; i++) {
			BufferedImage image = (disabled) ? Renderer.tint(buttonParts[i], Color.BLACK) : buttonParts[i];
			graphics.drawImage(image, (int) (x - (Constants.IMAGE_WIDTH * (buttonParts.length / 2.0f)) + (Constants.IMAGE_WIDTH * i)), (int) (y - (Constants.IMAGE_HEIGHT / 2)),
				null);

			Renderer.drawText(text, x, y, Constants.GAME_TEXT_SIZE, Color.BLACK, graphics);
		}
	}

	public void setText (String text) {
		this.text = text;

		int buttonLength = (int) Math.ceil((float) ((text.length( ) * Constants.GAME_TEXT_SIZE) + (Constants.IMAGE_WIDTH / 2)) / Constants.IMAGE_WIDTH);
		if (buttonLength <= 2) {
			buttonLength = 2;
		}

		buttonParts = new BufferedImage[buttonLength];
		buttonParts[0] = Assets.buttonBoxLeft;
		buttonParts[buttonParts.length - 1] = Assets.buttonBoxRight;
		for (int i = 1; i < buttonParts.length - 1; i++) {
			buttonParts[i] = Assets.buttonBoxMiddle;
		}
	}

	private void updateButton ( ) {
		if (!pressed) {
			if (handler.getLeftPressed( )) {
				if (handler.getMouseX( ) > x - (Constants.IMAGE_WIDTH * (buttonParts.length / 2.0f))
					&& handler.getMouseX( ) < x + (Constants.IMAGE_WIDTH * (buttonParts.length / 2.0f)) && handler.getMouseY( ) > y - (Constants.IMAGE_HEIGHT / 2)
					&& handler.getMouseY( ) < y + (Constants.IMAGE_HEIGHT / 2)) {
					pressed = true;
					clicked = true;
				}
			}
		} else {
			clicked = false;
			if (!handler.getLeftPressed( )) {
				pressed = false;
			}
		}
	}

	public boolean getPressed ( ) {
		if (!disabled) {
			return pressed;
		}
		return false;
	}

	public boolean getClicked ( ) {
		if (!disabled) {
			return clicked;
		}
		return false;
	}

	public boolean getDisabled ( ) {
		return disabled;
	}

	public void setDisabled (boolean disabled) {
		this.disabled = disabled;
	}

	public String getText ( ) {
		return text;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

}
