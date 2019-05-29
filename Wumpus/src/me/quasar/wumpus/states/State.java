package me.quasar.wumpus.states;

import java.awt.Graphics;

import me.quasar.wumpus.utils.Handler;

public abstract class State {
	private static State currentState = null;

	protected Handler handler;

	public State (Handler handler) {
		this.handler = handler;

		init( );
	}

	public abstract void init ( );

	public abstract void update ( );

	public abstract void render (Graphics graphics);

	public static void setState (State state) {
		currentState = state;
	}

	public static State getState ( ) {
		return currentState;
	}
}
