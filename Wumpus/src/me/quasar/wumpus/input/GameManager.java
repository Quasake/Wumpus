package me.quasar.wumpus.input;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.ImageButton;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Wumpus;
import me.quasar.wumpus.objects.tiles.FloorTile;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.objects.tiles.WallTile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;
import me.quasar.wumpus.utils.Utils;

public class GameManager {
	private Player player;
	private Wumpus wumpus;
	private Map map;
	private Handler handler;

	private Button moveUp;
	private Button moveRight;
	private Button moveDown;
	private Button moveLeft;

	private Button attackUp;
	private Button attackRight;
	private Button attackDown;
	private Button attackLeft;

	private final int START_TURN = 0;
	private final int DO_TURN = 1;
	private final int END_TURN = 2;
	private int turnStage = START_TURN;

	private int turns = 0;

	private boolean gameOver;
	private String gameOverMessage;
	private boolean win;

	private Tile compassTile;
	private boolean compassWumpus;
	private boolean compassSelection;
	private Button compassWeaponButton;
	private Button compassTorchButton;
	private Button compassWumpusButton;

	private boolean updated;

	private boolean swungSword;
	private boolean hitWumpus;

	private ArrayList<String> events;

	public GameManager (Player player, Wumpus wumpus, Map map, Handler handler) {
		this.player = player;
		this.wumpus = wumpus;
		this.map = map;
		this.handler = handler;

		events = new ArrayList<String>( );

		moveUp = new ImageButton(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * 1.5f), (Constants.GAME_HEIGHT / 8) * 5, 0, handler);
		moveRight = new ImageButton(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * 0.5f), (Constants.GAME_HEIGHT / 8) * 5, 1, handler);
		moveDown = new ImageButton(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH * 0.5f), (Constants.GAME_HEIGHT / 8) * 5, 2, handler);
		moveLeft = new ImageButton(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH * 1.5f), (Constants.GAME_HEIGHT / 8) * 5, 3, handler);

		attackUp = new ImageButton(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * 1.5f), (Constants.GAME_HEIGHT / 8) * 7, 0, handler);
		attackRight = new ImageButton(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * 0.5f), (Constants.GAME_HEIGHT / 8) * 7, 1, handler);
		attackDown = new ImageButton(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH * 0.5f), (Constants.GAME_HEIGHT / 8) * 7, 2, handler);
		attackLeft = new ImageButton(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH * 1.5f), (Constants.GAME_HEIGHT / 8) * 7, 3, handler);

		compassWeaponButton = new TextButton(Constants.MAP_WIDTH / 4, Constants.GAME_HEIGHT / 2, "Weapon", handler);
		compassTorchButton = new TextButton(Constants.MAP_WIDTH / 2, Constants.GAME_HEIGHT / 2, "Torch", handler);
		compassWumpusButton = new TextButton((Constants.MAP_WIDTH / 4) * 3, Constants.GAME_HEIGHT / 2, "Wumpus", handler);
	}

	public void update ( ) {
		player.update( );
		wumpus.update( );

		if (!gameOver) {
			switch (turnStage) {
				case START_TURN :
					startTurn( );
					break;
				case DO_TURN :
					doTurn( );
					break;
				case END_TURN :
					endTurn( );
					break;
			}
		}
	}

	public void render (Graphics graphics) {
		Renderer.drawText("Move", Constants.INFOBOX_CENTER, Constants.GAME_HEIGHT / 2, Constants.GAME_TEXT_SIZE, true, Color.BLACK, graphics);
		moveUp.render(graphics);
		moveRight.render(graphics);
		moveDown.render(graphics);
		moveLeft.render(graphics);

		Renderer.drawText("Attack", Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) * 3, Constants.GAME_TEXT_SIZE, true, Color.BLACK, graphics);
		attackUp.render(graphics);
		attackRight.render(graphics);
		attackDown.render(graphics);
		attackLeft.render(graphics);

		wumpus.render(graphics);
		player.render(graphics);

		Renderer.drawText("Turns : " + turns, Constants.GAME_TEXT_SIZE * 2, Constants.GAME_HEIGHT - (Constants.GAME_TEXT_SIZE * 2), Constants.GAME_TEXT_SIZE, false,
			Color.LIGHT_GRAY, graphics);

		if (compassSelection) {
			compassWeaponButton.render(graphics);
			compassTorchButton.render(graphics);
			compassWumpusButton.render(graphics);
		}

		for (int i = 0; i < events.size( ); i++) {
			Renderer.drawText(events.get(i), Constants.GAME_WIDTH - Constants.INFOBOX_WIDTH, (Constants.GAME_HEIGHT / 4) + (Constants.GAME_TEXT_SIZE * i),
				Constants.GAME_TEXT_SIZE / 2, false, Color.BLACK, graphics);
		}
	}

	private void startTurn ( ) {
		if (!updated) {
			updateEvents( );

			coverTiles( );
			uncoverTiles(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER);
		}

		if (getPlayerInput( )) {
			events.clear( );

			if (swungSword) {
				if (hitWumpus) {
					if (Utils.chance(0.65f)) {
						gameOver("You killed the wumpus with your sword.", true);
					} else {
						addEvent("The wumpus dodged your sword.");
					}
				} else {
					addEvent("You missed the wumpus with your sword.");
				}

				hitWumpus = false;
				swungSword = false;
			}

			wumpus.moveRandomly( );

			setButtons(true);
			updated = false;

			coverTiles( );
			uncoverTiles(player.getMoveTileX( ) + Constants.MAP_BORDER, player.getMoveTileY( ) + Constants.MAP_BORDER);

			turnStage = DO_TURN;
			turns++;
		}
	}

	private void doTurn ( ) {
		if (!player.arrowIsDestroyed( )) {
			if (!player.getArrow( ).isMoving( )) {
				if (map.getTile(player.getArrow( ).getTileX( ) + Constants.MAP_BORDER, player.getArrow( ).getTileY( ) + Constants.MAP_BORDER) instanceof WallTile) {
					addEvent("Your arrow hit a wall.");
					player.getArrow( ).destroy( );
				} else if (player.getArrow( ).getTileX( ) == wumpus.getTileX( ) && player.getArrow( ).getTileY( ) == wumpus.getTileY( )) {
					if (Utils.chance(0.65f)) {
						gameOver("You shot the wumpus with an arrow", true);
					} else {
						addEvent("The wumpus dodged your arrow.");
						player.getArrow( ).destroy( );
					}
				}
			}

			player.getArrow( ).update( );
		}

		if (compassSelection && compassTile == null) {
			if (getCompassInput( )) {
				compassSelection = false;
			}
		}
		if (compassTile != map.getTile(wumpus.getTileX( ) + Constants.MAP_BORDER, wumpus.getTileY( ) + Constants.MAP_BORDER) && compassWumpus) {
			compassTile = map.getTile(wumpus.getTileX( ) + Constants.MAP_BORDER, wumpus.getTileY( ) + Constants.MAP_BORDER);
		}

		if (!player.isMoving( ) && !wumpus.isMoving( ) && player.arrowIsDestroyed( ) && !compassSelection) {
			turnStage = END_TURN;
		}
	}

	private void endTurn ( ) {
		Tile currentPlayerTile = map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER);
		if (currentPlayerTile.hasItem( )) {
			player.addItem(currentPlayerTile.getItem( ));
			addEvent("You picked up a " + currentPlayerTile.getItem( ).getName( ) + "!");
			currentPlayerTile.setItem(null);
		}

		if (player.getTileX( ) == wumpus.getTileX( ) && player.getTileY( ) == wumpus.getTileY( )) {
			if (Utils.chance(0.05f + ((player.hasWeapon( )) ? 60 : 0))) {
				gameOver("You ran into the wumpus and won.", true);
			} else {
				gameOver("You ran into the wumpus and lost.", false);
			}
		} else if (((FloorTile) map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER)).isHole( )) {
			gameOver("You fell into a pit.", false);
		}

		turnStage = START_TURN;
		setButtons(false);
	}

	private void updateEvents ( ) {
		if (Utils.inTileRange(player.getTileX( ), player.getTileY( ), wumpus.getTileX( ), wumpus.getTileY( ), player.getTorchCount( ), map)) {
			addEvent("You found wumpus droppings.");
		} else if (Utils.inTileRange(player.getTileX( ), player.getTileY( ), wumpus.getTileX( ), wumpus.getTileY( ), Constants.ENTITY_RANGE, map)) {
			addEvent("You smell wumpus.");
		}

		boolean foundHole = false;
		for (int x = -Constants.ENTITY_RANGE + player.getTileX( ) + Constants.MAP_BORDER; x <= Constants.ENTITY_RANGE + player.getTileX( ) + Constants.MAP_BORDER; x++) {
			for (int y = -Constants.ENTITY_RANGE + player.getTileY( ) + Constants.MAP_BORDER; y <= Constants.ENTITY_RANGE + player.getTileY( ) + Constants.MAP_BORDER; y++) {
				if ((map.getTile(x, y) instanceof FloorTile) && (((FloorTile) map.getTile(x, y)).isHole( ))) {
					addEvent("You feel a slight breeze.");
					foundHole = true;
					break;
				}
			}

			if (foundHole) {
				break;
			}
		}

		if (compassTile != null) {
			if (compassTile.getX( ) > player.getX( )) {
				if (compassTile.getY( ) > player.getY( )) {
					addEvent("The compass points South East.");
				} else if (compassTile.getY( ) < player.getY( )) {
					addEvent("The compass points North East.");
				} else {
					addEvent("The compass points East.");
				}
			} else if (compassTile.getX( ) < player.getX( )) {
				if (compassTile.getY( ) > player.getY( )) {
					addEvent("The compass points South West.");
				} else if (compassTile.getY( ) < player.getY( )) {
					addEvent("The compass points North West.");
				} else {
					addEvent("The compass points West.");
				}
			} else {
				if (compassTile.getY( ) > player.getY( )) {
					addEvent("The compass points South.");
				} else if (compassTile.getY( ) < player.getY( )) {
					addEvent("The compass points North.");
				}
			}
		}

		updated = true;
	}

	private void uncoverTiles (int tileX, int tileY) {
		for (int x = -player.getTorchCount( ) + tileX; x <= player.getTorchCount( ) + tileX; x++) {
			for (int y = -player.getTorchCount( ) + tileY; y <= player.getTorchCount( ) + tileY; y++) {
				try {
					map.getTile(x, y).setHidden(false);

					if (x == Constants.MAP_BORDER) {
						if (y == Constants.MAP_BORDER) {
							map.getTile(x - 1, y).setHidden(false);
							map.getTile(x - 1, y - 1).setHidden(false);
							map.getTile(x, y - 1).setHidden(false);
						} else if (y == Constants.MAP_BORDER + map.getSize( ) - 1) {
							map.getTile(x - 1, y).setHidden(false);
							map.getTile(x - 1, y + 1).setHidden(false);
							map.getTile(x, y + 1).setHidden(false);
						} else {
							map.getTile(x - 1, y).setHidden(false);
						}
					} else if (x == Constants.MAP_BORDER + map.getSize( ) - 1) {
						if (y == Constants.MAP_BORDER) {
							map.getTile(x, y - 1).setHidden(false);
							map.getTile(x + 1, y - 1).setHidden(false);
							map.getTile(x + 1, y).setHidden(false);
						} else if (y == Constants.MAP_BORDER + map.getSize( ) - 1) {
							map.getTile(x + 1, y).setHidden(false);
							map.getTile(x + 1, y + 1).setHidden(false);
							map.getTile(x, y + 1).setHidden(false);
						} else {
							map.getTile(x + 1, y).setHidden(false);
						}
					} else {
						if (y == Constants.MAP_BORDER) {
							map.getTile(x, y - 1).setHidden(false);
						} else if (y == Constants.MAP_BORDER + map.getSize( ) - 1) {
							map.getTile(x, y + 1).setHidden(false);
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	private void coverTiles ( ) {
		for (int x = Constants.MAP_BORDER - 1; x <= Constants.MAP_BORDER + map.getSize( ); x++) {
			for (int y = Constants.MAP_BORDER - 1; y <= Constants.MAP_BORDER + map.getSize( ); y++) {
				if (!Utils.inTileRange(x, y, player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER, player.getTorchCount( ), map)
					&& !Utils.inTileRange(x, y, player.getMoveTileX( ) + Constants.MAP_BORDER, player.getMoveTileY( ) + Constants.MAP_BORDER, player.getTorchCount( ), map)) {
					map.getTile(x, y).setCovered(true);
				} else {
					map.getTile(x, y).setCovered(false);
				}
			}
		}
	}

	private boolean getCompassInput ( ) {
		compassWeaponButton.update( );
		compassTorchButton.update( );
		compassWumpusButton.update( );

		if (compassWeaponButton.isClicked( )) {
			compassTile = map.getTileWithItem(map.getWeaponId( ));
		} else if (compassTorchButton.isClicked( )) {
			compassTile = map.getTileWithItem(Constants.ID_TORCH);
		} else if (compassWumpusButton.isClicked( )) {
			compassTile = map.getTile(wumpus.getTileX( ) + Constants.MAP_BORDER, wumpus.getTileY( ) + Constants.MAP_BORDER);
			compassWumpus = true;
		}

		return compassTile != null;
	}

	private boolean getPlayerInput ( ) {
		moveUp.update( );
		moveRight.update( );
		moveDown.update( );
		moveLeft.update( );

		attackUp.update( );
		attackRight.update( );
		attackDown.update( );
		attackLeft.update( );

		int slotClickedIndex = player.getInventory( ).getSlotClicked( );

		if (slotClickedIndex > -1 && compassTile == null) {
			if (player.getInventory( ).getItem(slotClickedIndex).getId( ) == Constants.ID_COMPASS) {
				compassSelection = true;

				if (map.getTileWithItem(map.getWeaponId( )) == null) {
					compassWeaponButton.setDisabled(true);
				}
				if (map.getTileWithItem(Constants.ID_TORCH) == null) {
					compassTorchButton.setDisabled(true);
				}

				player.getInventory( ).getSlot(slotClickedIndex).setDisabled(true);

				return true;
			}
		} else if (moveUp.isClicked( )) {
			return player.moveUp(true, true);
		} else if (moveRight.isClicked( )) {
			return player.moveRight(true, true);
		} else if (moveDown.isClicked( )) {
			return player.moveDown(true, true);
		} else if (moveLeft.isClicked( )) {
			return player.moveLeft(true, true);
		} else if (attackUp.isClicked( )) {
			if (player.hasWeapon( )) {
				if (player.getWeapon( ).getId( ) == Constants.ID_BOW) {
					player.shootArrow(0);
				} else {
					swungSword = true;
					hitWumpus = player.swingSword(wumpus.getTileX( ), wumpus.getTileY( ), 0);
				}

				return true;
			}
		} else if (attackRight.isClicked( )) {
			if (player.hasWeapon( )) {
				if (player.getWeapon( ).getId( ) == Constants.ID_BOW) {
					player.shootArrow(1);
				} else {
					swungSword = true;
					hitWumpus = player.swingSword(wumpus.getTileX( ), wumpus.getTileY( ), 1);
				}

				return true;
			}
		} else if (attackDown.isClicked( )) {
			if (player.hasWeapon( )) {
				if (player.getWeapon( ).getId( ) == Constants.ID_BOW) {
					player.shootArrow(2);
				} else {
					swungSword = true;
					hitWumpus = player.swingSword(wumpus.getTileX( ), wumpus.getTileY( ), 2);
				}

				return true;
			}
		} else if (attackLeft.isClicked( )) {
			if (player.hasWeapon( )) {
				if (player.getWeapon( ).getId( ) == Constants.ID_BOW) {
					player.shootArrow(3);
				} else {
					swungSword = true;
					hitWumpus = player.swingSword(wumpus.getTileX( ), wumpus.getTileY( ), 3);
				}

				return true;
			}
		}

		return false;
	}

	private void gameOver (String gameOverMessage, boolean win) {
		gameOver = true;

		this.gameOverMessage = gameOverMessage;
		this.win = win;
	}

	private void setButtons (boolean disabled) {
		moveUp.setDisabled(disabled);
		moveRight.setDisabled(disabled);
		moveDown.setDisabled(disabled);
		moveLeft.setDisabled(disabled);
		attackUp.setDisabled(disabled);
		attackRight.setDisabled(disabled);
		attackDown.setDisabled(disabled);
		attackLeft.setDisabled(disabled);
	}

	public void addEvent (String event) {
		events.add(event);
	}

	public boolean isGameOver ( ) {
		return gameOver;
	}

	public boolean isWin ( ) {
		return win;
	}

	public String getGameOverMessage ( ) {
		return gameOverMessage;
	}

}
