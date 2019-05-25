package me.quasar.wumpus.graphics;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.resources.ImageLoader;
import me.quasar.wumpus.graphics.resources.SpriteSheet;
import me.quasar.wumpus.utils.Constants;

public class Assets {
	public static BufferedImage[ ] playerIdle;
	public static BufferedImage[ ] playerMoveUp;
	public static BufferedImage[ ] playerMoveRight;
	public static BufferedImage[ ] playerMoveDown;
	public static BufferedImage[ ] playerMoveLeft;

	public static BufferedImage[ ] playerIdleTorch;
	public static BufferedImage[ ] playerMoveUpTorch;
	public static BufferedImage[ ] playerMoveRightTorch;
	public static BufferedImage[ ] playerMoveDownTorch;
	public static BufferedImage[ ] playerMoveLeftTorch;

	public static BufferedImage torch;
	public static BufferedImage sword;
	public static BufferedImage bow;
	public static BufferedImage compass;

	public static BufferedImage wallTop;
	public static BufferedImage wallTopRight;
	public static BufferedImage wallRight;
	public static BufferedImage wallBottomRight;
	public static BufferedImage wallBottom;
	public static BufferedImage wallBottomLeft;
	public static BufferedImage wallLeft;
	public static BufferedImage wallTopLeft;
	
	public static BufferedImage floorTile;
	public static BufferedImage hiddenTile;
	public static BufferedImage nullTile;

	public static BufferedImage poleTop;
	public static BufferedImage poleMiddle;
	public static BufferedImage poleBottom;

	public static BufferedImage buttonBoxLeft;
	public static BufferedImage buttonBoxMiddle;
	public static BufferedImage buttonBoxRight;
	public static BufferedImage buttonUp;
	public static BufferedImage buttonRight;
	public static BufferedImage buttonDown;
	public static BufferedImage buttonLeft;

	public static BufferedImage title;

	public static void init ( ) {
		SpriteSheet items = new SpriteSheet(ImageLoader.loadImage("/textures/wumpus_items.png"));
		torch = items.getSprite(0, 0);
		sword = items.getSprite(0, 1);
		bow = items.getSprite(1, 0);
		compass = items.getSprite(1, 1);

		SpriteSheet player = new SpriteSheet(ImageLoader.loadImage("/textures/wumpus_player.png"));
		playerIdle = player.getAnimation(0, 0);
		playerMoveUp = player.getAnimation(0, 1);
		playerMoveRight = player.getAnimation(0, 2);
		playerMoveDown = player.getAnimation(0, 3);
		playerMoveLeft = player.getAnimation(0, 4);

		playerIdleTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 0);
		playerMoveUpTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 1);
		playerMoveRightTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 2);
		playerMoveDownTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 3);
		playerMoveLeftTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 4);
		
		SpriteSheet textures = new SpriteSheet(ImageLoader.loadImage("/textures/wumpus_textures.png"));
		wallTop = textures.getSprite(1, 0);
		wallTopRight = textures.getSprite(2, 0);
		wallRight = textures.getSprite(2, 1);
		wallBottomRight = textures.getSprite(2, 2);
		wallBottom = textures.getSprite(1, 2);
		wallBottomLeft = textures.getSprite(0, 2);
		wallLeft = textures.getSprite(0, 1);
		wallTopLeft = textures.getSprite(0, 0);
		
		floorTile = textures.getSprite(1, 1);
		hiddenTile = textures.getSprite(5, 0);
		nullTile = textures.getSprite(4, 1);

		poleTop = textures.getSprite(3, 0);
		poleMiddle = textures.getSprite(3, 1);
		poleBottom = textures.getSprite(3, 2);

		buttonBoxLeft = textures.getSprite(0, 3);
		buttonBoxMiddle = textures.getSprite(1, 3);
		buttonBoxRight = textures.getSprite(2, 3);
		buttonUp = textures.getSprite(4, 2);
		buttonRight = textures.getSprite(4, 3);
		buttonDown = textures.getSprite(5, 2);
		buttonLeft = textures.getSprite(5, 3);

		title = SpriteSheet.resize(ImageLoader.loadImage("/textures/wumpus_title.png"), Constants.SPRITE_SCALE * 2);
	}
}
