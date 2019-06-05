package me.quasar.wumpus.graphics.components;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.utils.Constants;

public class Panel {
	private float x;
	private float y;
	private int width;
	private int height;
	private Color color;
	private float alpha;

	private float fadeToAlpha;

	public Panel (float x, float y, int width, int height, Color color, float alpha) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.alpha = alpha;

		fadeToAlpha = alpha;
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
		graphics.setColor(new Color(color.getRed( ) / 255f, color.getGreen( ) / 255f, color.getBlue( ) / 255f, alpha));
		graphics.fillRect((int) x, (int) y, width, height);
	}

	public void fade (float alpha) {
		if (alpha > 1) {
			alpha = 1;
		} else if (alpha < 0) {
			alpha = 0;
		}

		fadeToAlpha = alpha;
	}

	public Color getColor ( ) {
		return color;
	}

	public void setColor (Color color) {
		this.color = color;
	}

	public float getX ( ) {
		return x;
	}

	public float getY ( ) {
		return y;
	}

	public int getWidth ( ) {
		return width;
	}

	public int getHeight ( ) {
		return height;
	}

	public float getAlpha ( ) {
		return alpha;
	}

	public void setAlpha (float alpha) {
		this.alpha = alpha;
		fadeToAlpha = alpha;
	}

}
