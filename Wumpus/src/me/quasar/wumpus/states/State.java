package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.TransitionPanel;
import me.quasar.wumpus.utils.Handler;

public abstract class State {
	private static State currentState = null;

	protected Handler handler;

	protected TransitionPanel panel;
	protected State goToState;

	public State (Handler handler) {
		this.handler = handler;

		panel = new TransitionPanel(Color.BLACK);

		init( );
	}

	public abstract void init ( );

	public abstract void update ( );

	public abstract void render (Graphics graphics);

	public static void setState (State state, boolean fade) {
		currentState = state;

		if (fade) {
			state.getPanel( ).fadeIn( );
		}
	}

	public static State getState ( ) {
		return currentState;
	}

	protected void updatePanel ( ) {
		panel.update( );

		if (goToState != null) {
			if (panel.getAlpha( ) == 1) {
				State.setState(goToState, true);
				goToState.init( );
				goToState = null;
			}
		}
	}

	public TransitionPanel getPanel ( ) {
		return panel;
	}
}
