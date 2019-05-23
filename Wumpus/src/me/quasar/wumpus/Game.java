package me.quasar.wumpus;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Window;
import me.quasar.wumpus.utils.Constants;

public class Game implements Runnable {
	private int width;
	private int height;

	private Window window;

	private Graphics graphics;
	private BufferStrategy bufferStrategy;
	private Thread thread;
	private boolean running;

	public Game (int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void init ( ) {
		window = new Window(width, height, Constants.GAME_TITLE);

		Assets.init( );
	}

	private void update ( ) {

	}

	private void render ( ) {
		bufferStrategy = window.getCanvas( ).getBufferStrategy( );
		if (bufferStrategy == null) {
			window.getCanvas( ).createBufferStrategy(3);
			return;
		}
		graphics = bufferStrategy.getDrawGraphics( );

		graphics.clearRect(0, 0, width, height);

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

}
