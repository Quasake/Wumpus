package me.quasar.wumpus.graphics;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.utils.Constants;

public class TransitionPanel {
	private float alpha = 0;
	private float fadeToAlpha = alpha;
	
	private Color color;
	
	public TransitionPanel (Color color) {
		this.color = color;
	}

	public void update ( ) {
		if (fadeToAlpha > alpha) {
			if (alpha + Constants.GAME_FADESPEED >= fadeToAlpha) {
				alpha = fadeToAlpha;
			} else {
				alpha += Constants.GAME_FADESPEED;
			}
		} else if (fadeToAlpha < alpha) {
			if (alpha - Constants.GAME_FADESPEED <= fadeToAlpha) {
				alpha = fadeToAlpha;
			} else {
				alpha -= Constants.GAME_FADESPEED;
			}
		}
	}

	public void render (Graphics graphics) {
		Color prevColor = graphics.getColor( );
		graphics.setColor(new Color(color.getRed( ), color.getBlue( ), color.getGreen( ), alpha));
		
		graphics.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
	
		graphics.setColor(prevColor);
	}

	public float getAlpha ( ) {
		return alpha;
	}
	
	public void setAlpha (float alpha) {
		this.alpha = alpha;
	}

	public Color getColor ( ) {
		return color;
	}
	
	public void fadeIn () {
		alpha = 1;
		fadeToAlpha = 0;
	}
	
	public void fadeOut () {
		alpha = 0;
		fadeToAlpha = 1;
	}

}
