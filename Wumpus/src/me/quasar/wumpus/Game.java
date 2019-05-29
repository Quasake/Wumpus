package me.quasar.wumpus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Window;
import me.quasar.wumpus.input.MouseManager;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.states.CreditsState;
import me.quasar.wumpus.states.GameState;
import me.quasar.wumpus.states.OptionState;
import me.quasar.wumpus.states.State;
import me.quasar.wumpus.states.TitlescreenState;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class Game implements Runnable {
	private Window window;
	private Handler handler;
	private MouseManager mouseManager;

	private Font font;

	private Graphics graphics;
	private BufferStrategy bufferStrategy;
	private Thread thread;
	private boolean running;
	
	public State gameState;
	public State titlescreenState;
	public State optionState;
	public State creditsState;

	public Game ( ) {
	}

	private void init (int state) {
		window = new Window(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, Constants.GAME_TITLE);

		mouseManager = new MouseManager( );
		window.getCanvas( ).addMouseListener(mouseManager);
		window.getCanvas( ).addMouseMotionListener(mouseManager);
		
		handler = new Handler(this);

		try { // Load game font
			font = Font.createFont(Font.TRUETYPE_FONT, getClass( ).getClassLoader( ).getResourceAsStream("fonts/pixelated.ttf")).deriveFont(Constants.GAME_TEXT_SIZE);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
			ge.registerFont(font);
		} catch (IOException | FontFormatException e) {
			font = null;
			e.printStackTrace( );
		}
		Assets.init( );
		
		gameState = new GameState(handler);
		titlescreenState = new TitlescreenState(handler);
		optionState = new OptionState(handler);
		creditsState = new CreditsState(handler);

		switch(state) {
			case 0:
				State.setState(titlescreenState);
				break;
			case 1:
				State.setState(optionState);
				break;
			case 2:
				State.setState(creditsState);
				break;
			case 3:
				State.setState(gameState);
			default:
				State.setState(titlescreenState);
		}
	}

	private void update ( ) {
		if (State.getState( ) != null) {
			State.getState( ).update( );
		}
	}

	private void render ( ) {
		bufferStrategy = window.getCanvas( ).getBufferStrategy( );
		if (bufferStrategy == null) {
			window.getCanvas( ).createBufferStrategy(3);
			return;
		}
		graphics = bufferStrategy.getDrawGraphics( );

		graphics.clearRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		graphics.setFont(font);
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		
		if (State.getState( ) != null) {
			State.getState( ).render(graphics);
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
		init(0);

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

	public void setBoardSize (int size) {
		if (size <= Constants.MAP_MAX_SIZE && size >= Constants.MAP_MIN_SIZE) {
			Constants.UPDATE(size);

			window.getFrame( ).dispose( );

			init(1);
		}
	}

	public Window getWindow ( ) {
		return window;
	}

	public MouseManager getMouseManager ( ) {
		return mouseManager;
	}

}
