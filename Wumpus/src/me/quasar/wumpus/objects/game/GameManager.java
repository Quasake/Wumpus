package me.quasar.wumpus.objects.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.ImageButton;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.entities.Lever;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Trap;
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

	private boolean gameOver = false;
	private String gameOverMessage = "";
	private boolean win = false;

	private Tile compassTile;
	private boolean compassWumpus = false;
	private boolean compassSelection = false;
	private Button compassWeaponButton;
	private Button compassTorchButton;
	private Button compassWumpusButton;

	private boolean updated = false;

	private boolean swungSword = false;
	private boolean hitWumpus = false;
	private boolean laidTrap = false;

	private Trap trap = null;
	private Lever lever = null;

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

		if (Utils.chance(0.5f)) {
			Tile leverTile;
			do {
				leverTile = map.getRandomTile(false, false);
			} while (leverTile == map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER));
			lever = new Lever(leverTile.getX( ), leverTile.getY( ), map);
		}
	}

	public void update ( ) {
		player.update( );
		wumpus.update( );

		if (lever != null) {
			lever.update( );
		}
		if (trap != null) {
			trap.update( );
		}

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

		Renderer.drawText("Attack / Use", Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) * 3, Constants.GAME_TEXT_SIZE, true, Color.BLACK, graphics);
		attackUp.render(graphics);
		attackRight.render(graphics);
		attackDown.render(graphics);
		attackLeft.render(graphics);

		if (lever != null) {
			lever.render(graphics);
		}
		if (trap != null) {
			trap.render(graphics);
		}

		wumpus.render(graphics);
		player.render(graphics);

		Renderer.drawText("Turns : " + turns, Constants.GAME_TEXT_SIZE * 2, Constants.GAME_HEIGHT - (Constants.GAME_TEXT_SIZE * 2), Constants.GAME_TEXT_SIZE, false,
			Color.LIGHT_GRAY, graphics);

		for (int i = 0; i < events.size( ); i++) {
			Renderer.drawText(events.get(i), Constants.GAME_WIDTH - Constants.INFOBOX_WIDTH, (Constants.GAME_HEIGHT / 4) + (Constants.GAME_TEXT_SIZE * i),
				Constants.GAME_TEXT_SIZE / 2, false, Color.BLACK, graphics);
		}

		if (compassSelection) {
			compassWeaponButton.render(graphics);
			compassTorchButton.render(graphics);
			compassWumpusButton.render(graphics);
		}
	}

	private void startTurn ( ) {
		if (!updated) {
			updateEvents( );

			GameUtils.coverTiles(player, map);
			GameUtils.uncoverTiles(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER, player, map);
		}

		if (getPlayerInput( )) {
			if (!laidTrap) {
				events.clear( );
			} else {
				laidTrap = false;
			}

			if (swungSword) {
				if (hitWumpus) {
					if (Utils.chance(0.65f)) {
						gameOver("You killed the wumpus with a sword.", true);
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

			GameUtils.coverTiles(player, map);
			GameUtils.uncoverTiles(player.getMoveTileX( ) + Constants.MAP_BORDER, player.getMoveTileY( ) + Constants.MAP_BORDER, player, map);

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
				player.removeItem(0);
			}
		}
		if (compassTile != map.getTile(wumpus.getTileX( ) + Constants.MAP_BORDER, wumpus.getTileY( ) + Constants.MAP_BORDER) && compassWumpus) {
			compassTile = map.getTile(wumpus.getTileX( ) + Constants.MAP_BORDER, wumpus.getTileY( ) + Constants.MAP_BORDER);
		}
		if (map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER) == compassTile) {
			compassTile = null;
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

		if (trap != null) {
			if (wumpus.getTileX( ) == trap.getTileX( ) && wumpus.getTileY( ) == trap.getTileY( )) {
				gameOver("The wumpus fell into your trap.", true);
			}
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

	private boolean getCompassInput ( ) {
		compassWeaponButton.update( );
		compassTorchButton.update( );
		compassWumpusButton.update( );

		if (compassWeaponButton.isClicked( )) {
			compassTile = map.getTileWithItem(map.getWeaponId( ));
		} else if (compassTorchButton.isClicked( )) {
			compassTile = map.getTileWithItem(Constants.ID_FLASHLIGHT);
			if (compassTile == null) {
				compassTile = map.getTileWithItem(Constants.ID_TORCH);
			}
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

		if (moveUp.isClicked( ) || handler.getKeyValue(KeyEvent.VK_W)) {
			return player.moveUp(true, true);
		} else if (moveRight.isClicked( ) || handler.getKeyValue(KeyEvent.VK_D)) {
			return player.moveRight(true, true);
		} else if (moveDown.isClicked( ) || handler.getKeyValue(KeyEvent.VK_S)) {
			return player.moveDown(true, true);
		} else if (moveLeft.isClicked( ) || handler.getKeyValue(KeyEvent.VK_A)) {
			return player.moveLeft(true, true);
		} else if (attackUp.isClicked( ) || handler.getKeyValue(KeyEvent.VK_UP)) {
			return doAction(Constants.UP);
		} else if (attackRight.isClicked( ) || handler.getKeyValue(KeyEvent.VK_RIGHT)) {
			return doAction(Constants.RIGHT);
		} else if (attackDown.isClicked( ) || handler.getKeyValue(KeyEvent.VK_DOWN)) {
			return doAction(Constants.DOWN);
		} else if (attackLeft.isClicked( ) || handler.getKeyValue(KeyEvent.VK_LEFT)) {
			return doAction(Constants.LEFT);
		}

		return false;
	}

	private boolean doAction (int direction) {
		if (player.getInventory( ).getItem(0) != null) {
			switch (player.getWeapon( ).getId( )) {
				case Constants.ID_BOW :
					player.shootArrow(direction);

					return true;
				case Constants.ID_SWORD :
					swungSword = true;
					hitWumpus = player.swingSword(wumpus.getTileX( ), wumpus.getTileY( ), direction);

					return true;
				case Constants.ID_BOMB :

					break;
				case Constants.ID_COMPASS :
					compassSelection = true;

					if (map.getTileWithItem(map.getWeaponId( )) == null) {
						compassWeaponButton.setDisabled(true);
					}
					if (map.getTileWithItem(Constants.ID_TORCH) == null && map.getTileWithItem(Constants.ID_FLASHLIGHT) == null) {
						compassTorchButton.setDisabled(true);
					}

					return true;
				case Constants.ID_TRAP :
					Tile trapTile = null;
					switch (direction) {
						case Constants.UP :
							trapTile = map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER - 1);

							break;
						case Constants.RIGHT :
							trapTile = map.getTile(player.getTileX( ) + Constants.MAP_BORDER + 1, player.getTileY( ) + Constants.MAP_BORDER);

							break;
						case Constants.DOWN :
							trapTile = map.getTile(player.getTileX( ) + Constants.MAP_BORDER, player.getTileY( ) + Constants.MAP_BORDER + 1);

							break;
						case Constants.LEFT :
							trapTile = map.getTile(player.getTileX( ) + Constants.MAP_BORDER - 1, player.getTileY( ) + Constants.MAP_BORDER);

							break;
					}

					boolean notLever = lever == null
						|| (lever != null && trapTile != map.getTile(lever.getTileX( ) + Constants.MAP_BORDER, lever.getTileY( ) + Constants.MAP_BORDER));
					boolean isFloorTile = (trapTile instanceof FloorTile) && !((FloorTile) trapTile).isHole( );

					if (notLever && isFloorTile) {
						trap = new Trap(trapTile.getX( ), trapTile.getY( ), map);

						addEvent("You laid down a trap.");
						laidTrap = true;
						player.removeItem(0);
						return true;
					}

					return false;
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

	public int getTurns ( ) {
		return turns;
	}

	public String getGameOverMessage ( ) {
		return gameOverMessage;
	}

}
