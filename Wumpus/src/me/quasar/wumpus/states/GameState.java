package me.quasar.wumpus.states;

import java.awt.Graphics;

import me.quasar.wumpus.input.GameManager;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Wumpus;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class GameState extends State {
	private Map map;
	private GameManager gameManager;
	
	private Player player;
	private Wumpus wumpus;

	public GameState (Handler handler) {
		super(handler);
	}

	@Override
	public void update ( ) {
		gameManager.update( );

		player.update( );
		wumpus.update( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);
		
		gameManager.render(graphics);

		player.render(graphics);
		wumpus.render(graphics);
	}

	@Override
	public void init ( ) {
		map = new Map(Constants.MAP_SIZE, Constants.MAP_SIZE);
		map.generateMap(false);
		
		Tile playerTile = map.getRandomTile(false);
		player = new Player(playerTile.getX( ), playerTile.getY( ), map);
		Tile wumpusTile;
		do {
			wumpusTile = map.getRandomTile(true);
		} while (wumpusTile.getX( ) != playerTile.getX( ) && wumpusTile.getY( ) != playerTile.getY( ));
		wumpus = new Wumpus(wumpusTile.getX( ), wumpusTile.getY( ), map);
		
		gameManager = new GameManager(player, wumpus, handler);
	}

}
