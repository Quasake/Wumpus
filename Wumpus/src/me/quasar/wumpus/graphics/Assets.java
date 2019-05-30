package me.quasar.wumpus.graphics;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.graphics.resources.ImageLoader;
import me.quasar.wumpus.graphics.resources.SpriteSheet;
import me.quasar.wumpus.utils.Constants;

public class Assets {
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
	public static BufferedImage inventorySlot;
	public static BufferedImage coveredTile;

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
	public static BufferedImage gameover;
	public static BufferedImage win;

	public static Animation playerIdleAnimation;
	public static Animation playerMoveUpAnimation;
	public static Animation playerMoveRightAnimation;
	public static Animation playerMoveDownAnimation;
	public static Animation playerMoveLeftAnimation;

	public static Animation playerIdleTorchAnimation;
	public static Animation playerMoveUpTorchAnimation;
	public static Animation playerMoveRightTorchAnimation;
	public static Animation playerMoveDownTorchAnimation;
	public static Animation playerMoveLeftTorchAnimation;

	public static Animation wumpusIdleAnimation;
	public static Animation wumpusMoveUpAnimation;
	public static Animation wumpusMoveRightAnimation;
	public static Animation wumpusMoveDownAnimation;
	public static Animation wumpusMoveLeftAnimation;

	public static void init ( ) {
		SpriteSheet items = new SpriteSheet(ImageLoader.loadImage("/textures/wumpus_items.png"));
		torch = items.getSprite(0, 0);
		sword = items.getSprite(0, 1);
		bow = items.getSprite(1, 0);
		compass = items.getSprite(1, 1);

		SpriteSheet player = new SpriteSheet(ImageLoader.loadImage("/textures/wumpus_player.png"));
		playerIdleAnimation = new Animation(player.getAnimation(0, 0));
		playerMoveUpAnimation = new Animation(player.getAnimation(0, 2));
		playerMoveRightAnimation = new Animation(player.getAnimation(0, 4));
		playerMoveDownAnimation = new Animation(player.getAnimation(0, 1));
		playerMoveLeftAnimation = new Animation(player.getAnimation(0, 3));

		playerIdleTorchAnimation = new Animation(player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 0));
		playerMoveUpTorchAnimation = new Animation(player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 2));
		playerMoveRightTorchAnimation = new Animation(player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 4));
		playerMoveDownTorchAnimation = new Animation(player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 1));
		playerMoveLeftTorchAnimation = new Animation(player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 3));

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
		inventorySlot = textures.getSprite(5, 1);
		coveredTile = textures.getSprite(3, 3);

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

		title = SpriteSheet.resize(ImageLoader.loadImage("/textures/wumpus_title.png"), Constants.SPRITE_SCALE * 1.5f);
		gameover = SpriteSheet.resize(ImageLoader.loadImage("/textures/wumpus_gameover.png"), Constants.SPRITE_SCALE * 1.5f);
		win = SpriteSheet.resize(ImageLoader.loadImage("/textures/wumpus_win.png"), Constants.SPRITE_SCALE * 1.5f);

		SpriteSheet wumpus = new SpriteSheet(ImageLoader.loadImage("/textures/wumpus_wumpus.png"));
		wumpusIdleAnimation = new Animation(wumpus.getAnimation(0, 0));
		wumpusMoveUpAnimation = new Animation(wumpus.getAnimation(0, 2));
		wumpusMoveRightAnimation = new Animation(wumpus.getAnimation(0, 4));
		wumpusMoveDownAnimation = new Animation(wumpus.getAnimation(0, 1));
		wumpusMoveLeftAnimation = new Animation(wumpus.getAnimation(0, 3));
	}
}
