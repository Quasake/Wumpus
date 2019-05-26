package me.quasar.wumpus.utils;

import me.quasar.wumpus.Game;
import me.quasar.wumpus.graphics.Window;
import me.quasar.wumpus.input.MouseManager;
import me.quasar.wumpus.objects.entities.Player;

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

	public MouseManager getMouseManager ( ) {
		return game.getMouseManager( );
	}

	public int getMouseX ( ) {
		return game.getMouseManager( ).getMouseX( );
	}

	public int getMouseY ( ) {
		return game.getMouseManager( ).getMouseY( );
	}

	public boolean getLeftPressed ( ) {
		return game.getMouseManager( ).getLeftPressed( );
	}
	
	public Player getPlayer () {
		return game.getPlayer( );
	}

}
