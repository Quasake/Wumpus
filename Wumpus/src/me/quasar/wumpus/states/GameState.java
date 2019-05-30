package me.quasar.wumpus.states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.input.GameManager;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Wumpus;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class GameState extends State {
	private Map map;
	private GameManager gameManager;

	private boolean gameEnded;

	private Player player;
	private Wumpus wumpus;

	private Button retryButton;
	private Button exitButton;

	public GameState (Handler handler) {
		super(handler);
	}

	@Override
	public void update ( ) {
		gameManager.update( );

		player.update( );
		wumpus.update( );

		if (gameEnded) {
			retryButton.update( );
			exitButton.update( );

			if (retryButton.getClicked( )) {
				goToState = handler.getGame( ).gameState;
				panel.fadeOut( );
			}
			if (exitButton.getClicked( )) {
				goToState = handler.getGame( ).titlescreenState;
				panel.fadeOut( );
			}
		}
		if (gameManager.getGameOver( ) && !gameEnded) {
			gameEnded = true;
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		gameManager.render(graphics);

		player.render(graphics);
		wumpus.render(graphics);

		if (gameEnded) {
			BufferedImage result = (gameManager.getWin( )) ? Assets.win : Assets.gameover;
			graphics.drawImage(result, (Constants.MAP_WIDTH / 2) - (result.getWidth( ) / 2), (Constants.MAP_HEIGHT / 4) - (result.getHeight( ) / 2), null);

			retryButton.render(graphics);
			exitButton.render(graphics);
		}

		panel.render(graphics);
	}

	@Override
	public void init ( ) {
		map = new Map(Constants.MAP_SIZE, Constants.MAP_SIZE);
		map.generateMap(false);

		gameEnded = false;

		retryButton = new Button(Constants.MAP_WIDTH / 2, (Constants.GAME_HEIGHT / 4) + (Constants.GAME_HEIGHT / 6), "Retry", handler);
		exitButton = new Button(Constants.MAP_WIDTH / 2, (Constants.GAME_HEIGHT / 4) + ((Constants.GAME_HEIGHT / 6) * 2), "Exit", handler);

		Tile playerTile = map.getRandomTile(false);
		player = new Player(playerTile.getX( ), playerTile.getY( ), map);
		Tile wumpusTile;
		do {
			wumpusTile = map.getRandomTile(true);
		} while (wumpusTile == playerTile);
		wumpus = new Wumpus(wumpusTile.getX( ), wumpusTile.getY( ), map);

		gameManager = new GameManager(player, wumpus, handler);
	}

}
