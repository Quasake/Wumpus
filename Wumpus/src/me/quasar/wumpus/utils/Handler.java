package me.quasar.wumpus.utils;

import java.awt.Canvas;

import javax.swing.JFrame;

import me.quasar.wumpus.Game;
import me.quasar.wumpus.graphics.Window;
import me.quasar.wumpus.input.KeyManager;
import me.quasar.wumpus.input.MouseManager;
import me.quasar.wumpus.states.ExtrasState;

public class Handler {
	private Game game;

	public Handler (Game game) {
		this.game = game;
	}

	public Game getGame ( ) {
		return game;
	}

	public void writeScore (int mapSize, int score) {
		((ExtrasState) getGame( ).extrasState).writeScore(mapSize, score);
	}

	public Window getWindow ( ) {
		return game.getWindow( );
	}

	public JFrame getFrame ( ) {
		return getWindow( ).getFrame( );
	}

	public Canvas getCanvas ( ) {
		return getWindow( ).getCanvas( );
	}

	public MouseManager getMouseManager ( ) {
		return game.getMouseManager( );
	}

	public int getMouseX ( ) {
		return getMouseManager( ).getMouseX( );
	}

	public int getMouseY ( ) {
		return getMouseManager( ).getMouseY( );
	}

	public boolean isLeftPressed ( ) {
		return getMouseManager( ).isLeftPressed( );
	}

	public KeyManager getKeyManager ( ) {
		return game.getKeyManager( );
	}

	public boolean getKeyValue (int keyCode) {
		return getKeyManager( ).getKeyValue(keyCode);
	}

}
