package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.Map;

public class Trap extends Entity {

	public Trap (float x, float y, Map map) {
		super(x, y, 0, map);
	}

	@Override
	public void update ( ) {

	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(Assets.trapTile, (int) x, (int) y, null);
	}

	@Override
	protected void updateAnimations ( ) {

	}

}
