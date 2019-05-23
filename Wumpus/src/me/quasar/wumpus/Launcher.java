package me.quasar.wumpus;

import me.quasar.wumpus.utils.Constants;

public class Launcher {

	public static void main (String[ ] args) {
		Game game = new Game(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		game.start( );
	}

}
