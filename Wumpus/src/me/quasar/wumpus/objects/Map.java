package me.quasar.wumpus.objects;

import java.awt.Graphics;

import me.quasar.wumpus.objects.items.Bomb;
import me.quasar.wumpus.objects.items.Bow;
import me.quasar.wumpus.objects.items.Compass;
import me.quasar.wumpus.objects.items.FlashLight;
import me.quasar.wumpus.objects.items.Sword;
import me.quasar.wumpus.objects.items.Torch;
import me.quasar.wumpus.objects.items.Trap;
import me.quasar.wumpus.objects.tiles.EmptyTile;
import me.quasar.wumpus.objects.tiles.FloorTile;
import me.quasar.wumpus.objects.tiles.SeparatorTile;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.objects.tiles.WallTile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Utils;

public class Map {
	private int size;

	private boolean background;
	private int weaponId = Constants.ID_NULL;

	private Tile[ ][ ] tiles;

	public Map (int size, boolean background) {
		this.size = size;
		this.background = background;

		tiles = new Tile[size + (Constants.MAP_BORDER * 2) + 1][size + (Constants.MAP_BORDER * 2)];
	}

	public void render (Graphics graphics) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (tiles[x][y] != null) {
					tiles[x][y].render(graphics);
				}
			}
		}
	}

	public void generateMap ( ) {
		generateTiles( );

		if (!background) {
			generateItems( );
		}
	}

	private void generateItems ( ) {
		for (int i = 0; i < size / Constants.MAP_MIN_SIZE; i++) {
			if (Utils.chance(1f) && getTileWithItem(Constants.ID_FLASHLIGHT) == null) {
				getRandomTile(false, false).setItem(new FlashLight( ));
			} else {
				getRandomTile(false, false).setItem(new Torch( ));
			}

			if (Constants.SETTINGS_HAZARDS) {
				((FloorTile) getRandomTile(false, false)).setHole(true);
			}
		}

		getRandomTile(false, false).setItem(new Compass( ));

		if (Utils.chance(0.5f)) {
			getRandomTile(false, false).setItem(new Sword( ));
			weaponId = Constants.ID_SWORD;
		} else {
			getRandomTile(false, false).setItem(new Bow( ));
			weaponId = Constants.ID_BOW;
		}

		if (Utils.chance(0.33f)) {
			getRandomTile(false, false).setItem(new Trap( ));
		} else {
			if (Utils.chance(0.1f)) {
				getRandomTile(false, false).setItem(new Bomb( ));
			}
		}
	}

	private void generateTiles ( ) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (x == tiles.length - 1) {
					if (y == 0) {
						tiles[x][y] = new SeparatorTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 0);
					} else if (y == tiles[x].length - 1) {
						tiles[x][y] = new SeparatorTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 2);
					} else {
						tiles[x][y] = new SeparatorTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 1);
					}
				} else {
					if (background) {
						tiles[x][y] = new EmptyTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT);
					} else {
						if ((x >= Constants.MAP_BORDER - 1 && x <= size + ((Constants.MAP_BORDER - 1) * 2))
							&& (y >= Constants.MAP_BORDER - 1 && y <= size + ((Constants.MAP_BORDER - 1) * 2))) {
							if (x == Constants.MAP_BORDER - 1) {
								if (y == Constants.MAP_BORDER - 1) {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 7, true);
								} else if (y == size + ((Constants.MAP_BORDER - 1) * 2)) {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 5, true);
								} else {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 6, true);
								}
							} else if (x == size + ((Constants.MAP_BORDER - 1) * 2)) {
								if (y == Constants.MAP_BORDER - 1) {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 1, true);
								} else if (y == size + ((Constants.MAP_BORDER - 1) * 2)) {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 3, true);
								} else {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 2, true);
								}
							} else {
								if (y == Constants.MAP_BORDER - 1) {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 0, true);
								} else if (y == size + ((Constants.MAP_BORDER - 1) * 2)) {
									tiles[x][y] = new WallTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, 4, true);
								} else {
									tiles[x][y] = new FloorTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT, true);
								}
							}
						} else {
							tiles[x][y] = new EmptyTile(x * Constants.IMAGE_WIDTH, y * Constants.IMAGE_HEIGHT);
						}
					}
				}
			}
		}
	}

	public Tile getTile (int x, int y) {
		try {
			return tiles[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setTile (int x, int y, Tile tile) {
		try {
			tiles[x][y] = tile;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	public boolean freeTile (int tileX, int tileY) {
		return getTile(tileX, tileY) instanceof FloorTile;
	}

	public Tile getRandomTile (boolean canHaveItem, boolean canHaveHazard) {
		Tile tile = null;

		while (true) {
			tile = getTile(Constants.RANDOM.nextInt(size) + Constants.MAP_BORDER, Constants.RANDOM.nextInt(size) + Constants.MAP_BORDER);
			if (!canHaveItem) {
				if (tile.hasItem( )) {
					continue;
				}
			}

			if (!canHaveHazard) {
				if (((FloorTile) tile).isHole( )) {
					continue;
				}
			}

			break;
		}

		return tile;
	}

	public Tile getTileWithItem (int id) {
		for (int i = Constants.MAP_BORDER; i < Constants.MAP_BORDER + size; i++) {
			for (int j = Constants.MAP_BORDER; j < Constants.MAP_BORDER + size; j++) {
				if (getTile(i, j).hasItem( )) {
					if (getTile(i, j).getItem( ).getId( ) == id) {
						return getTile(i, j);
					}
				}
			}
		}
		return null;
	}

	public Tile[ ][ ] getTiles ( ) {
		return tiles;
	}

	public int getSize ( ) {
		return size;
	}

	public int getWeaponId ( ) {
		return weaponId;
	}

}
