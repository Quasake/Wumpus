package me.quasar.wumpus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import me.quasar.wumpus.audio.Sounds;
import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Window;
import me.quasar.wumpus.input.KeyManager;
import me.quasar.wumpus.input.MouseManager;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.states.CreditsState;
import me.quasar.wumpus.states.ExtrasState;
import me.quasar.wumpus.states.GameState;
import me.quasar.wumpus.states.OptionsState;
import me.quasar.wumpus.states.State;
import me.quasar.wumpus.states.TitlescreenState;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class Game implements Runnable {
	private Window window;
	private Handler handler;
	private Font font;
	private Map map;

	private MouseManager mouseManager;
	private KeyManager keyManager;

	public State titlescreenState;
	public State gameState;
	public State optionsState;
	public State creditsState;
	public State extrasState;

	private boolean running;
	private Thread thread;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;

	private enum ProgramState {
		TITLESCREEN, OPTIONS, EXTRAS, CREDITS, GAME
	}

	public Game ( ) {
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

	private void init (ProgramState state, boolean fade) {
		window = new Window(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, Constants.GAME_TITLE);
		handler = new Handler(this);

		mouseManager = new MouseManager( );
		window.getCanvas( ).addMouseListener(mouseManager);
		window.getCanvas( ).addMouseMotionListener(mouseManager);

		keyManager = new KeyManager( );
		window.getFrame( ).addKeyListener(keyManager);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass( ).getClassLoader( ).getResourceAsStream("fonts/pixelated.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment( ).registerFont(font);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace( );
		}
		Assets.init( );

		gameState = new GameState(handler);
		titlescreenState = new TitlescreenState(handler);
		optionsState = new OptionsState(handler);
		extrasState = new ExtrasState(handler);
		creditsState = new CreditsState(handler);

		switch (state) {
			case TITLESCREEN :
				State.setState(titlescreenState, fade);
				break;
			case OPTIONS :
				State.setState(optionsState, fade);
				break;
			case EXTRAS :
				State.setState(extrasState, fade);
				break;
			case CREDITS :
				State.setState(creditsState, fade);
				break;
			case GAME :
				State.setState(gameState, fade);
				break;
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

	@Override
	public void run ( ) {
		init(ProgramState.TITLESCREEN, true);

		float timePerTick = 1000000000 / Constants.GAME_FPS;
		float delta = 0;
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

	public void setMapSize (int size) {
		if (size <= Constants.MAP_MAX_SIZE && size >= Constants.MAP_MIN_SIZE) {
			Constants.UPDATE(size);
			window.getFrame( ).dispose( );

			init(ProgramState.OPTIONS, false);
		}
	}

	public Window getWindow ( ) {
		return window;
	}

	public MouseManager getMouseManager ( ) {
		return mouseManager;
	}

	public KeyManager getKeyManager ( ) {
		return keyManager;
	}

}
