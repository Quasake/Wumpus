package me.quasar.wumpus.objects.entities;

import me.quasar.wumpus.objects.gameboard.Map;

public abstract class Entity {
	private float x;
	private float y;
	private int width;
	private int height;
	
	private Map map;

	public Entity(float x, float y, int width, int height, Map map) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.map = map;
	}

}
