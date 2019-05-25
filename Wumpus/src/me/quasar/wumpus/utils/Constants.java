package me.quasar.wumpus.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Random;

public class Constants {
	public static final int SPRITE_WIDTH = 16;
	public static final int SPRITE_HEIGHT = 16;
	public static final int SPRITE_SCALE = 4;

	public static final int IMAGE_WIDTH = SPRITE_WIDTH * SPRITE_SCALE;
	public static final int IMAGE_HEIGHT = SPRITE_HEIGHT * SPRITE_SCALE;

	public static final int MAP_BORDER = 2;
	public static final int MAP_MAX_SIZE = 10;
	public static final int MAP_MIN_SIZE = 4;
	public static int MAP_SIZE = 4;
	
	public static final int INFOBOX_WIDTH = IMAGE_WIDTH * 5;

	public static final String GAME_TITLE = "Wumpus";
	public static final int GAME_WIDTH = (IMAGE_WIDTH * (MAP_SIZE + (MAP_BORDER * 2) + 1)) + INFOBOX_WIDTH;
	public static final int GAME_HEIGHT = IMAGE_HEIGHT * (MAP_SIZE + (MAP_BORDER * 2));
	public static final int GAME_FPS = 60;
	public final Font GAME_FONT = LOAD_FONT("/fonts/pixelated.ttf");

	public static final Random RANDOM = new Random( );

	private final Font LOAD_FONT (String PATH) {
		Font fnt = null;

		try { // Load game font
			fnt = Font.createFont(Font.TRUETYPE_FONT, getClass( ).getClassLoader( ).getResourceAsStream(PATH)).deriveFont(32f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
			ge.registerFont(fnt);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace( );
		}

		return fnt;
	}
}
