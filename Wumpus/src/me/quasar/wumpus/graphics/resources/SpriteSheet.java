package me.quasar.wumpus.graphics.resources;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.utils.Constants;

public class SpriteSheet {
	private BufferedImage sheet;

	public SpriteSheet (BufferedImage sheet) {
		this.sheet = sheet;
	}

	public BufferedImage crop (int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}

	public BufferedImage getSprite (int x, int y, int width, int height) {
		return resize(crop(x, y, width, height), Constants.SPRITE_SCALE);
	}

	public BufferedImage getSprite (int x, int y) {
		return resize(crop(x, y, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT), Constants.SPRITE_SCALE);
	}

	private BufferedImage resize (BufferedImage image, int scale) {
		BufferedImage resizedImage = new BufferedImage(image.getWidth( ) * scale, image.getHeight( ) * scale, image.getType( ));
		Graphics2D g = resizedImage.createGraphics( );

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, image.getWidth( ) * scale, image.getHeight( ) * scale, 0, 0, image.getWidth( ), image.getHeight( ), null);
		g.dispose( );

		return resizedImage;
	}

}
