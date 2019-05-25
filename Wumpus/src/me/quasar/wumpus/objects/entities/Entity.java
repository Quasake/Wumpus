package me.quasar.wumpus.objects.entities;

import me.quasar.wumpus.objects.Map;

public abstract class Entity {
	private float x;
	private float y;
	
	private int tileX;
	private int tileY;
	private int moveToTileX;
	private int moveToTileY;

	private Map map;

	public Entity (int tileX, int tileY, Map map) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.map = map;
	}
	
	public int getTileX () {
		return tileX;
	}
	
	public int getTileY () {
		return tileY;
	}
	
	public float getX () {
		return x;
	}
	
	public float getY () {
		return y;
	}

}
