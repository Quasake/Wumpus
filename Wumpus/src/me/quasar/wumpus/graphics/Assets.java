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
	public static BufferedImage arrow;
	public static BufferedImage flashlight;
	public static BufferedImage trap;
	public static BufferedImage bomb;

	public static BufferedImage leverLeft;
	public static BufferedImage leverRight;

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
	public static BufferedImage coveredTile;
	public static BufferedImage holeTile;
	public static BufferedImage trapTile;

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

	public static BufferedImage buttonEmpty;
	public static BufferedImage buttonEmptyDisabled;
	public static BufferedImage buttonPause;

	public static BufferedImage buttonBoxLeftDisabled;
	public static BufferedImage buttonBoxMiddleDisabled;
	public static BufferedImage buttonBoxRightDisabled;
	public static BufferedImage buttonUpDisabled;
	public static BufferedImage buttonRightDisabled;
	public static BufferedImage buttonDownDisabled;
	public static BufferedImage buttonLeftDisabled;

	public static BufferedImage title;
	public static BufferedImage gameover;
	public static BufferedImage win;

	public static Animation playerIdle;
	public static Animation playerMoveUp;
	public static Animation playerMoveRight;
	public static Animation playerMoveDown;
	public static Animation playerMoveLeft;

	public static Animation playerIdleTorch;
	public static Animation playerMoveUpTorch;
	public static Animation playerMoveRightTorch;
	public static Animation playerMoveDownTorch;
	public static Animation playerMoveLeftTorch;

	public static Animation wumpusIdle;
	public static Animation wumpusMoveUp;
	public static Animation wumpusMoveRight;
	public static Animation wumpusMoveDown;
	public static Animation wumpusMoveLeft;

	public static void init ( ) {
		SpriteSheet items = new SpriteSheet(ImageLoader.loadImage("/tex/items.png"));
		torch = items.getSprite(0, 0);
		sword = items.getSprite(0, 1);
		bow = items.getSprite(1, 0);
		compass = items.getSprite(1, 1);
		arrow = items.getSprite(2, 0);
		flashlight = items.getSprite(2, 1);
		trap = items.getSprite(0, 2);
		bomb = items.getSprite(1, 2);

		leverLeft = items.getSprite(2, 2);
		leverRight = items.getSprite(3, 2);

		SpriteSheet player = new SpriteSheet(ImageLoader.loadImage("/tex/player.png"));
		playerIdle = player.getAnimation(0, 0);
		playerMoveUp = player.getAnimation(0, 2);
		playerMoveRight = player.getAnimation(0, 4);
		playerMoveDown = player.getAnimation(0, 1);
		playerMoveLeft = player.getAnimation(0, 3);

		playerIdleTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 0);
		playerMoveUpTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 2);
		playerMoveRightTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 4);
		playerMoveDownTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 1);
		playerMoveLeftTorch = player.getAnimation(Constants.SPRITE_ANIMATION_LENGTH, 3);

		SpriteSheet textures = new SpriteSheet(ImageLoader.loadImage("/tex/textures.png"));
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
		coveredTile = textures.getSprite(3, 3);
		holeTile = textures.getSprite(4, 0);
		trapTile = textures.getSprite(6, 0);

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

		buttonEmpty = textures.getSprite(5, 1);
		buttonEmptyDisabled = textures.getSprite(6, 3);
		buttonPause = textures.getSprite(6, 2);

		buttonBoxLeftDisabled = textures.getSprite(0, 4);
		buttonBoxMiddleDisabled = textures.getSprite(1, 4);
		buttonBoxRightDisabled = textures.getSprite(2, 4);
		buttonUpDisabled = textures.getSprite(3, 4);
		buttonRightDisabled = textures.getSprite(4, 4);
		buttonDownDisabled = textures.getSprite(5, 4);
		buttonLeftDisabled = textures.getSprite(6, 4);

		title = Renderer.resizeImage(ImageLoader.loadImage("/tex/title.png"), Constants.SPRITE_SCALE * 1.5f);
		gameover = Renderer.resizeImage(ImageLoader.loadImage("/tex/gameover.png"), Constants.SPRITE_SCALE * 1.5f);
		win = Renderer.resizeImage(ImageLoader.loadImage("/tex/win.png"), Constants.SPRITE_SCALE * 1.5f);

		SpriteSheet wumpus = new SpriteSheet(ImageLoader.loadImage("/tex/wumpus.png"));
		wumpusIdle = wumpus.getAnimation(0, 0);
		wumpusMoveUp = wumpus.getAnimation(0, 2);
		wumpusMoveRight = wumpus.getAnimation(0, 4);
		wumpusMoveDown = wumpus.getAnimation(0, 1);
		wumpusMoveLeft = wumpus.getAnimation(0, 3);
	}
}
