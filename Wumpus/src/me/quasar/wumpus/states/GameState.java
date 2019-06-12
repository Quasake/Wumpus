package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Lever;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Trap;
import me.quasar.wumpus.objects.entities.Wumpus;
import me.quasar.wumpus.objects.game.GameManager;
import me.quasar.wumpus.objects.tiles.FloorTile;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;
import me.quasar.wumpus.utils.Utils;

public class GameState extends State {
	private Player player;
	private Wumpus wumpus;

	private Map map;

	private GameManager gameManager;

	private Button retryButton;
	private Button exitButton;

	public GameState (Handler handler) {
		super(handler);
	}

	@Override
	public void init ( ) {
		retryButton = new TextButton(Constants.MAP_WIDTH / 4, (Constants.MAP_HEIGHT / 4) * 3, "Retry", handler);
		exitButton = new TextButton((Constants.MAP_WIDTH / 4) * 3, (Constants.MAP_HEIGHT / 4) * 3, "Exit", handler);

		map = new Map(Constants.MAP_SIZE, false);
		map.generateMap( );

		Tile playerTile;
		do {
			playerTile = map.getRandomTile(false, false);
		} while (((FloorTile) playerTile).isHole( ));
		player = new Player(playerTile.getX( ), playerTile.getY( ), map, handler);

		Tile wumpusTile;
		do {
			wumpusTile = map.getRandomTile(true, false);
		} while (wumpusTile == playerTile);
		wumpus = new Wumpus(wumpusTile.getX( ), wumpusTile.getY( ), map);

		gameManager = new GameManager(player, wumpus, map, handler);
	}

	@Override
	public void update ( ) {
		gameManager.update( );

		if (gameManager.isGameOver( )) {
			retryButton.update( );
			exitButton.update( );

			if (retryButton.isClicked( )) {
				if (gameManager.isWin( )) {
					handler.writeScore(map.getSize( ), gameManager.getTurns( ));
				}

				goToState(handler.getGame( ).gameState);
			} else if (exitButton.isClicked( )) {
				if (gameManager.isWin( )) {
					handler.writeScore(map.getSize( ), gameManager.getTurns( ));
				}

				goToState(handler.getGame( ).titlescreenState);
			}
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		gameManager.render(graphics);

		if (gameManager.isGameOver( )) {
			Renderer.drawImage((gameManager.isWin( )) ? Assets.win : Assets.gameover, Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 4, true, graphics);
			Renderer.drawText(gameManager.getGameOverMessage( ), Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2, Constants.GAME_TEXT_SIZE, true,
				(gameManager.isWin( )) ? Color.GREEN : Color.RED, graphics);

			retryButton.render(graphics);
			exitButton.render(graphics);
		}

		panel.render(graphics);
	}

}
