package me.quasar.wumpus.objects.entities;

import me.quasar.wumpus.objects.gameboard.Map;

public abstract class Entity {
	private int tileX;
	private int tileY;

	private Map map;

	public Entity (int tileX, int tileY, Map map) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.map = map;
	}

}
