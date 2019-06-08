package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.components.Panel;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public abstract class State {
	private static State currentState = null;

	protected Handler handler;

	protected State goToState;
	protected Panel panel;

	public State (Handler handler) {
		this.handler = handler;

		panel = new Panel(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, Color.BLACK, 1);

		init( );
	}

	public abstract void init ( );

	public abstract void update ( );

	public abstract void render (Graphics graphics);

	public static void setState (State state, boolean fade) {
		currentState = state;

		if (fade) {
			state.getPanel( ).fade(0);
		} else {
			state.getPanel( ).setAlpha(0);
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

	protected void goToState (State state) {
		goToState = state;
		panel.fade(1);
	}

	public Panel getPanel ( ) {
		return panel;
	}
	
}
