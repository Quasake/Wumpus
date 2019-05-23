package me.quasar.wumpus.objects.gameboard;

public class Map {
	private int width;
	private int height;

	private Tile[ ][ ] tiles;

	public Map (int width, int height) {
		this.width = width;
		this.height = height;

		tiles = new Tile[width][height];
	}

	public void generateMap ( ) {

	}

	public Tile getTile (int x, int y) {
		try {
			return tiles[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

}
