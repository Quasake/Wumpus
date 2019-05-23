package me.quasar.wumpus.utils;

import me.quasar.wumpus.Game;
import me.quasar.wumpus.graphics.Window;

public class Handler {
	private Game game;

	public Handler (Game game) {
		this.game = game;
	}

	public Game getGame ( ) {
		return game;
	}

	public Window getWindow ( ) {
		return game.getWindow( );
	}

}
