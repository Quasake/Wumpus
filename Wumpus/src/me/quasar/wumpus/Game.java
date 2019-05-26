package me.quasar.wumpus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.Window;
import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.graphics.resources.SpriteSheet;
import me.quasar.wumpus.input.GameManager;
import me.quasar.wumpus.input.MouseManager;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class Game implements Runnable {
	private int width;
	private int height;

	private Window window;
	private Map map;
	private Player player;
	private Handler handler;
	private MouseManager mouseManager;
	private GameManager gameManager;

	private Font font;

	private Animation playerTitleScreenAnimation;
	private Button playButton;
	private Button exitButton;

	private Graphics graphics;
	private BufferStrategy bufferStrategy;
	private Thread thread;
	private boolean running;

	private enum GameState {
		TITLESCREEN, OPTIONS, GAME
	}

	private GameState currentState;

	public Game (int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void init ( ) {
		window = new Window(width, height, Constants.GAME_TITLE);
		map = new Map(Constants.MAP_SIZE, Constants.MAP_SIZE);
		handler = new Handler(this);

		mouseManager = new MouseManager( );
		window.getCanvas( ).addMouseListener(mouseManager);
		window.getCanvas( ).addMouseMotionListener(mouseManager);

		try { // Load game font
			font = Font.createFont(Font.TRUETYPE_FONT, getClass( ).getClassLoader( ).getResourceAsStream("fonts/pixelated.ttf")).deriveFont(Constants.GAME_TEXT_SIZE);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
			ge.registerFont(font);
		} catch (IOException | FontFormatException e) {
			font = null;
			e.printStackTrace( );
		}

		Assets.init( );
		map.generateMap( );

		Tile playerTile = map.getRandomTile(false);
		player = new Player(playerTile.getX( ), playerTile.getY( ), map);
		
		gameManager = new GameManager(handler);

		if (Math.random( ) < 0.5) {
			playerTitleScreenAnimation = Assets.playerIdleAnimation;
		} else {
			playerTitleScreenAnimation = Assets.playerIdleTorchAnimation;
		}
		playButton = new Button(Constants.INFOBOX_CENTER, Constants.GAME_HEIGHT / 4, "Play", handler);
		exitButton = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) * 3, "Exit", handler);

		currentState = GameState.TITLESCREEN;
	}

	private void update ( ) {
		switch (currentState) {
			case TITLESCREEN :
				playerTitleScreenAnimation.update( );

				playButton.update( );
				exitButton.update( );

				if (playButton.getClicked( )) {
					currentState = GameState.GAME;
				}
				if (exitButton.getClicked( )) {
					System.exit(0);
				}
				break;
			case OPTIONS :

				break;
			case GAME :
				gameManager.update( );
				
				player.update( );
				break;
		}
	}

	private void render ( ) {
		bufferStrategy = window.getCanvas( ).getBufferStrategy( );
		if (bufferStrategy == null) {
			window.getCanvas( ).createBufferStrategy(3);
			return;
		}
		graphics = bufferStrategy.getDrawGraphics( );

		graphics.clearRect(0, 0, width, height);
		graphics.setFont(font);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

		map.render(graphics);
		switch (currentState) {
			case TITLESCREEN :
				graphics.drawImage(Assets.title, (Constants.MAP_WIDTH / 2) - (Assets.title.getWidth( ) / 2), (Constants.MAP_HEIGHT / 4) - (Assets.title.getHeight( ) / 2), null);
				Renderer.drawText("Created by Frank Alfano", Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2, Constants.GAME_TEXT_SIZE, Color.LIGHT_GRAY, graphics);
				BufferedImage playerImage = SpriteSheet.resize(playerTitleScreenAnimation.getCurrentFrame( ), 3);
				graphics.drawImage(playerImage, (Constants.MAP_WIDTH / 2) - (playerImage.getWidth( ) / 2), ((Constants.MAP_HEIGHT / 4) * 3) - (playerImage.getHeight( ) / 2), null);

				playButton.render(graphics);
				exitButton.render(graphics);
				break;
			case OPTIONS :

				break;
			case GAME :
				gameManager.render(graphics);
				
				player.render(graphics);
				break;
		}

		bufferStrategy.show( );
		graphics.dispose( );
	}

	public synchronized void start ( ) {
		if (running) {
			return;
		}
		running = true;

		thread = new Thread(this);
		thread.start( );
	}

	public synchronized void stop ( ) {
		if (!running) {
			return;
		}
		running = false;

		try {
			thread.join( );
		} catch (InterruptedException e) {
			e.printStackTrace( );
		}
	}

	@Override
	public void run ( ) {
		init( );

		double timePerTick = 1000000000 / Constants.GAME_FPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime( );

		while (running) {
			now = System.nanoTime( );
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				update( );
				render( );

				delta--;
			}
		}

		stop( );
	}

	public Window getWindow ( ) {
		return window;
	}

	public MouseManager getMouseManager ( ) {
		return mouseManager;
	}

	public Player getPlayer ( ) {
		return player;
	}

	public Map getMap ( ) {
		return map;
	}
	
	public GameManager getGameManager () {
		return gameManager;
	}

}
