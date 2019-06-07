package me.quasar.wumpus.graphics.components.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class TextButton extends Button {
	private String text;

	public TextButton (float x, float y, String text, Handler handler) {
		super(x, y, getImage(text), handler);

		this.text = text;

		disabledTexture = getDisabledTexture(text);
	}

	@Override
	public void update ( ) {
		updateInput( );
	}

	@Override
	public void render (Graphics graphics) {
		drawTexture(graphics);

		Renderer.drawText(text, x, y, Constants.GAME_TEXT_SIZE, true, Color.BLACK, graphics);
	}

	private static BufferedImage getImage (String text) {
		int buttonLength = (int) Math.ceil((float) ((text.length( ) * Constants.GAME_TEXT_SIZE) + (Constants.IMAGE_WIDTH / 2)) / Constants.IMAGE_WIDTH);
		if (buttonLength < 2) {
			buttonLength = 2;
		}

		BufferedImage buttonTexture = new BufferedImage(Constants.IMAGE_WIDTH * buttonLength, Constants.IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) buttonTexture.getGraphics( );

		for (int i = 0; i < buttonLength; i++) {
			if (i == 0) {
				g2d.drawImage(Assets.buttonBoxLeft, 0, 0, null);
			} else if (i == buttonLength - 1) {
				g2d.drawImage(Assets.buttonBoxRight, buttonTexture.getWidth( ) - Constants.IMAGE_WIDTH, 0, null);
			} else {
				g2d.drawImage(Assets.buttonBoxMiddle, Constants.IMAGE_WIDTH * i, 0, null);
			}
		}

		g2d.dispose( );

		return buttonTexture;
	}

	public String getText ( ) {
		return text;
	}

	private BufferedImage getDisabledTexture (String text) {
		int buttonLength = (int) Math.ceil((float) ((text.length( ) * Constants.GAME_TEXT_SIZE) + (Constants.IMAGE_WIDTH / 2)) / Constants.IMAGE_WIDTH);
		if (buttonLength < 2) {
			buttonLength = 2;
		}

		BufferedImage buttonTexture = new BufferedImage(Constants.IMAGE_WIDTH * buttonLength, Constants.IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) buttonTexture.getGraphics( );

		for (int i = 0; i < buttonLength; i++) {
			if (i == 0) {
				g2d.drawImage(Assets.buttonBoxLeftDisabled, 0, 0, null);
			} else if (i == buttonLength - 1) {
				g2d.drawImage(Assets.buttonBoxRightDisabled, buttonTexture.getWidth( ) - Constants.IMAGE_WIDTH, 0, null);
			} else {
				g2d.drawImage(Assets.buttonBoxMiddleDisabled, Constants.IMAGE_WIDTH * i, 0, null);
			}
		}

		g2d.dispose( );

		return buttonTexture;
	}

	public void setText (String text) {
		this.text = text;
	}

}
