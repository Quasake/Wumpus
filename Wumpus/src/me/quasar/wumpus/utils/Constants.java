package me.quasar.wumpus.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class Constants {
	public static int SPRITE_WIDTH = 16;
	public static int SPRITE_HEIGHT = 16;
	public static int SPRITE_SCALE = 4;

	public static int IMAGE_WIDTH = SPRITE_WIDTH * SPRITE_SCALE;
	public static int IMAGE_HEIGHT = SPRITE_HEIGHT * SPRITE_SCALE;

	public static String GAME_TITLE = "Wumpus";
	public static int GAME_WIDTH = 600;
	public static int GAME_HEIGHT = 600;
	public static int GAME_FPS = 60;
	public Font GAME_FONT = LOAD_FONT("/res/fonts/pixelated.ttf");

	private Font LOAD_FONT (String PATH) {
		Font fnt = null;

		try { // Load game font
			fnt = Font.createFont(Font.TRUETYPE_FONT, getClass( ).getClassLoader( ).getResourceAsStream(PATH))
				.deriveFont(32f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
			ge.registerFont(fnt);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace( );
		}

		return fnt;
	}
}
