package me.quasar.wumpus.graphics.resources;

import java.awt.Graphics2D;
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
		return resize(crop(x * Constants.SPRITE_WIDTH, y * Constants.SPRITE_HEIGHT, width, height), Constants.SPRITE_SCALE);
	}

	public BufferedImage getSprite (int x, int y) {
		return resize(crop(x * Constants.SPRITE_WIDTH, y * Constants.SPRITE_HEIGHT, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT), Constants.SPRITE_SCALE);
	}

	public static BufferedImage resize (BufferedImage image, float scale) {
		BufferedImage resizedImage = new BufferedImage((int) (scale * image.getWidth( )), (int) (scale * image.getHeight( )), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) resizedImage.getGraphics( );
		g2d.scale(scale, scale);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose( );
		
		return resizedImage;
	}

}
