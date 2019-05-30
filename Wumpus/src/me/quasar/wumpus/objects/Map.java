package me.quasar.wumpus.objects;

import java.awt.Graphics;

import me.quasar.wumpus.objects.items.Bow;
import me.quasar.wumpus.objects.items.Compass;
import me.quasar.wumpus.objects.items.Sword;
import me.quasar.wumpus.objects.items.Torch;
import me.quasar.wumpus.objects.tiles.FloorTile;
import me.quasar.wumpus.objects.tiles.HiddenTile;
import me.quasar.wumpus.objects.tiles.PoleTile;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.objects.tiles.WallTile;
import me.quasar.wumpus.utils.Constants;

public class Map {
	private int width;
	private int height;

	private Tile[ ][ ] tiles;

	public Map (int width, int height) {
		this.width = width;
		this.height = height;

		tiles = new Tile[width + (Constants.MAP_BORDER * 2) + 1][height + (Constants.MAP_BORDER * 2)];
	}

	public void update ( ) {

	}

	public void render (Graphics graphics) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y].render(graphics);
			}
		}
	}

	public void generateMap (boolean background) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (x == tiles.length - 1) {
					if (y == 0) {
						tiles[x][y] = new PoleTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 0);
					} else if (y == tiles[x].length - 1) {
						tiles[x][y] = new PoleTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 2);
					} else {
						tiles[x][y] = new PoleTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 1);
					}
					continue;
				}
				
				if ((x >= Constants.MAP_BORDER - 1 && x <= width + ((Constants.MAP_BORDER - 1) * 2) && !background)
					&& (y >= Constants.MAP_BORDER - 1 && y <= height + ((Constants.MAP_BORDER - 1) * 2))) {
					if ((x >= Constants.MAP_BORDER && x < width + Constants.MAP_BORDER) && (y >= Constants.MAP_BORDER && y < height + Constants.MAP_BORDER)) {
						tiles[x][y] = new FloorTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, true);
					} else {
						if (x == Constants.MAP_BORDER - 1) {
							if (y == Constants.MAP_BORDER - 1) {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 7, true);
							} else if (y == height + ((Constants.MAP_BORDER - 1) * 2)) {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 5, true);
							} else {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 6, true);
							}
						} else if (x == width + ((Constants.MAP_BORDER - 1) * 2)) {
							if (y == Constants.MAP_BORDER - 1) {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 1, true);
							} else if (y == height + ((Constants.MAP_BORDER - 1) * 2)) {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 3, true);
							} else {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 2, true);
							}
						} else {
							if (y == Constants.MAP_BORDER - 1) {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 0, true);
							} else if (y == height + ((Constants.MAP_BORDER - 1) * 2)) {
								tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 4, true);
							}
						}
					}
				} else {
					tiles[x][y] = new HiddenTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT);
				}
			}
		}
		
		if (!background) {
			for (int i = 0; i < width / Constants.MAP_MIN_SIZE; i++) {
				getRandomTile(false).setItem(new Torch( ));
			}
			getRandomTile(false).setItem(new Compass( ));
			if (Math.random( ) < 0.5) {
				getRandomTile(false).setItem(new Sword( ));
			} else {
				getRandomTile(false).setItem(new Bow( ));
			}
		}
	}

	public Tile getBoardTile (int x, int y) {
		if (x >= width || x < 0) {
			return null;
		}
		if (y >= height || y < 0) {
			return null;
		}

		return tiles[x + Constants.MAP_BORDER][y + Constants.MAP_BORDER];
	}

	public Tile getGameTile (int x, int y) {
		try {
			return tiles[x + Constants.MAP_BORDER][y + Constants.MAP_BORDER];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public Tile getRandomTile (boolean canHaveItem) {
		Tile tile = null;

		while (true) {
			tile = getBoardTile(Constants.RANDOM.nextInt(width), Constants.RANDOM.nextInt(height));
			if (!canHaveItem) {
				if (!tile.hasItem( )) {
					break;
				}
			} else {
				break;
			}
		}
		
		return tile;
	}

	public Tile[ ][ ] getTiles ( ) {
		return tiles;
	}

	public int getWidth ( ) {
		return width;
	}

	public int getHeight ( ) {
		return height;
	}

}
