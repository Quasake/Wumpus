package me.quasar.wumpus.graphics.resources;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Renderer;
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
		return Renderer.resizeImage(crop(x * Constants.SPRITE_WIDTH, y * Constants.SPRITE_HEIGHT, width, height), Constants.SPRITE_SCALE);
	}

	public BufferedImage getSprite (int x, int y) {
		return Renderer.resizeImage(crop(x * Constants.SPRITE_WIDTH, y * Constants.SPRITE_HEIGHT, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT), Constants.SPRITE_SCALE);
	}

	public Animation getAnimation (int x, int y) {
		BufferedImage[ ] frames = new BufferedImage[Constants.SPRITE_ANIMATION_LENGTH];

		for (int i = 0; i < frames.length; i++) {
			frames[i] = getSprite(x + i, y);
		}

		return new Animation(frames);
	}
}
