package me.quasar.wumpus.graphics.components.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.utils.Handler;

public class ImageButton extends Button {

	public ImageButton (float x, float y, int type, Handler handler) {
		super(x, y, getTexture(type), handler);

		disabledTexture = getDisabledTexture(type);
	}

	@Override
	public void update ( ) {
		updateInput( );
	}

	@Override
	public void render (Graphics graphics) {
		drawTexture(graphics);
	}

	private static BufferedImage getTexture (int type) {
		switch (type) {
			case 0 :
				return Assets.buttonUp;
			case 1 :
				return Assets.buttonRight;
			case 2 :
				return Assets.buttonDown;
			case 3 :
				return Assets.buttonLeft;
			case 4 :
				return Assets.buttonEmpty;
		}
		return Assets.nullTile;
	}

	private BufferedImage getDisabledTexture (int type) {
		switch (type) {
			case 0 :
				return Assets.buttonUpDisabled;
			case 1 :
				return Assets.buttonRightDisabled;
			case 2 :
				return Assets.buttonDownDisabled;
			case 3 :
				return Assets.buttonLeftDisabled;
			case 4 :
				return Assets.buttonEmptyDisabled;
		}
		return Assets.nullTile;
	}

}
