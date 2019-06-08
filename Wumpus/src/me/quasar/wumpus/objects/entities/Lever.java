package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.Map;

public class Lever extends Entity {
	private boolean switched;

	public Lever (float x, float y, Map map) {
		super(x, y, 0, map);
	}

	@Override
	public void update ( ) {

	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage((switched) ? Assets.leverRight : Assets.leverLeft, (int) x, (int) y, null);
	}

	@Override
	protected void updateAnimations ( ) {

	}

	public void setSwitched (boolean switched) {
		this.switched = switched;
	}

}
