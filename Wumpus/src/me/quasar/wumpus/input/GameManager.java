package me.quasar.wumpus.input;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.entities.Arrow;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Wumpus;
import me.quasar.wumpus.objects.tiles.FloorTile;
import me.quasar.wumpus.objects.tiles.Tile;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class GameManager {
	private Player player;
	private Wumpus wumpus;
	private Handler handler;

	private Button moveUp;
	private Button moveRight;
	private Button moveDown;
	private Button moveLeft;
	private Button attackUp;
	private Button attackRight;
	private Button attackDown;
	private Button attackLeft;

	private Button weaponOption;
	private Button torchOption;
	private Button wumpusOption;
	private Tile compassTile;
	private boolean compassSelection = false;

	private int turns = 0;
	private static ArrayList<String> events = new ArrayList<String>( );
	private boolean updatedEvents = false;
	private boolean gameover = false;
	private String gameoverMessage = "";
	private boolean win = false;

	public GameManager (Player player, Wumpus wumpus, Handler handler) {
		this.player = player;
		this.wumpus = wumpus;
		this.handler = handler;

		events.clear( );

		moveUp = new Button(Constants.INFOBOX_CENTER - ((Constants.IMAGE_WIDTH / 2) * 3), (Constants.GAME_HEIGHT / 3) * 2, 0, handler);
		moveRight = new Button(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH / 2), (Constants.GAME_HEIGHT / 3) * 2, 1, handler);
		moveDown = new Button(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH / 2), (Constants.GAME_HEIGHT / 3) * 2, 2, handler);
		moveLeft = new Button(Constants.INFOBOX_CENTER + ((Constants.IMAGE_WIDTH / 2) * 3), (Constants.GAME_HEIGHT / 3) * 2, 3, handler);
		attackUp = new Button(Constants.INFOBOX_CENTER - ((Constants.IMAGE_WIDTH / 2) * 3), (Constants.GAME_HEIGHT / 10) * 9, 0, handler);
		attackRight = new Button(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH / 2), (Constants.GAME_HEIGHT / 10) * 9, 1, handler);
		attackDown = new Button(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH / 2), (Constants.GAME_HEIGHT / 10) * 9, 2, handler);
		attackLeft = new Button(Constants.INFOBOX_CENTER + ((Constants.IMAGE_WIDTH / 2) * 3), (Constants.GAME_HEIGHT / 10) * 9, 3, handler);

		weaponOption = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4), "Weapon", handler);
		torchOption = new Button(Constants.INFOBOX_CENTER, (int) ((Constants.GAME_HEIGHT / 4) + (Constants.IMAGE_HEIGHT * 1.5)), "Torch", handler);
		wumpusOption = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) + (Constants.IMAGE_HEIGHT * 3), "Wumpus", handler);
	}

	public void update ( ) {
		if (!gameover) {
			if (compassSelection) {
				weaponOption.update( );
				torchOption.update( );
				wumpusOption.update( );

				if (handler.getLeftPressed( )) {
					if (weaponOption.getClicked( )) {
						compassTile = player.getMap( ).getTileWithItem(player.getWeapon( ));
						compassSelection = false;
						wumpus.moveRandomly( );
						updatedEvents = false;
						turns++;
					} else if (torchOption.getClicked( )) {
						compassTile = player.getMap( ).getTileWithItem("torch");
						compassSelection = false;
						wumpus.moveRandomly( );
						updatedEvents = false;
						turns++;
					} else if (wumpusOption.getClicked( )) {
						compassTile = wumpus.getMap( ).getGameTile(wumpus.getTileX( ), wumpus.getTileY( ));
						compassSelection = false;
						wumpus.moveRandomly( );
						updatedEvents = false;
						turns++;
					}
				}
			} else {
				if (player.getMoving( ) || (player.getArrow( ) != null && !player.getArrow( ).isDestroyed( ))) {
					if (updatedEvents) {
						setButtons(true);

						updatedEvents = false;
						events.clear( );
					}

					if (player.getArrow( ) != null) {
						if (player.getArrow( ).getTileX( ) == wumpus.getTileX( ) && player.getArrow( ).getTileY( ) == wumpus.getTileY( )) {
							player.getArrow( ).destroy( );

							if (Math.random( ) < 0.65) {
								gameover = true;
								win = true;
								setGameOverMessage("You shot the Wumpus with an arrow.");
							} else {
								addEvent("The Wumpus dodged your arrow.");
							}
						}
					}
				} else {
					moveUp.update( );
					moveRight.update( );
					moveDown.update( );
					moveLeft.update( );
					attackUp.update( );
					attackRight.update( );
					attackDown.update( );
					attackLeft.update( );

					if (!updatedEvents) {
						setButtons(false);

						updateEvents( );
						updatedEvents = true;

						if (player.getMap( ).getGameTile(player.getTileX( ), player.getTileY( )).equals(compassTile)) {
							compassTile = null;
						}

						if (player.getTileX( ) == wumpus.getTileX( ) && player.getTileY( ) == wumpus.getTileY( )) {
							float chance = 0.05f + ((player.hasWeapon( )) ? 0.6f : 0f);
							gameover = true;

							if (Math.random( ) < chance) {
								win = true;
								setGameOverMessage("You ran into the Wumpus and won.");
							} else {
								win = false;
								setGameOverMessage("You ran into the Wumpus and lost.");
							}
						} else if (((FloorTile) player.getMap( ).getGameTile(player.getTileX( ), player.getTileY( ))).isHole( )) {
							gameover = true;
							win = false;
							setGameOverMessage("You fell into a pit.");
						}
					}

					checkButtons( );
				}
			}
		}
	}

	public void render (Graphics graphics) {
		if (!gameover) {
			Renderer.drawText("Move", Constants.INFOBOX_CENTER, ((Constants.GAME_HEIGHT / 3) * 2) - (Constants.GAME_TEXT_SIZE * 3), Constants.GAME_TEXT_SIZE, Color.BLACK,
				graphics);
			moveUp.render(graphics);
			moveRight.render(graphics);
			moveDown.render(graphics);
			moveLeft.render(graphics);
			Renderer.drawText("Attack", Constants.INFOBOX_CENTER, ((Constants.GAME_HEIGHT / 10) * 9) - (Constants.GAME_TEXT_SIZE * 3), Constants.GAME_TEXT_SIZE, Color.BLACK,
				graphics);
			attackUp.render(graphics);
			attackRight.render(graphics);
			attackDown.render(graphics);
			attackLeft.render(graphics);

			for (int i = 0; i < events.size( ); i++) {
				Renderer.drawText(events.get(i), Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) + (Constants.GAME_TEXT_SIZE * i), (int) (Constants.GAME_TEXT_SIZE / 1.5),
					Color.BLACK, graphics);
			}

			if (compassSelection) {
				weaponOption.render(graphics);
				torchOption.render(graphics);
				wumpusOption.render(graphics);
			}
		}

		Renderer.drawText("Turns : " + turns, (Constants.IMAGE_WIDTH / 2) * 3, Constants.GAME_HEIGHT - (Constants.IMAGE_HEIGHT / 2), Constants.GAME_TEXT_SIZE, Color.LIGHT_GRAY,
			graphics);
	}

	private void updateEvents ( ) {
		if ((Math.abs(player.getTileX( ) - wumpus.getTileX( )) <= Constants.ENTITY_RANGE && Math.abs(player.getTileX( ) - wumpus.getTileX( )) >= -Constants.ENTITY_RANGE)
			&& (Math.abs(player.getTileY( ) - wumpus.getTileY( )) <= Constants.ENTITY_RANGE && Math.abs(player.getTileY( ) - wumpus.getTileY( )) >= -Constants.ENTITY_RANGE)
			&& wumpus.isHidden( )) {
			addEvent("You smell wumpus.");
		} else if (Math.abs(player.getTileX( ) - wumpus.getTileX( )) <= player.getTorchNumber( ) && Math.abs(player.getTileY( ) - wumpus.getTileY( )) <= player.getTorchNumber( )) {
			addEvent("You found wumpus droppings.");
		}

		boolean foundHole = false;
		for (int i = -Constants.ENTITY_RANGE; i <= Constants.ENTITY_RANGE; i++) {
			for (int j = -Constants.ENTITY_RANGE; j <= Constants.ENTITY_RANGE; j++) {
				if ((player.getMap( ).getGameTile(i + player.getTileX( ), j + player.getTileY( )) instanceof FloorTile)
					&& ((FloorTile) player.getMap( ).getGameTile(i + player.getTileX( ), j + player.getTileY( ))).isHole( )) {
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

	}

	public static void addEvent (String event) {
		events.add(event);
	}

	public boolean getGameOver ( ) {
		return gameover;
	}

	public String getGameOverMessage ( ) {
		return gameoverMessage;
	}

	public void checkButtons ( ) {
		if (handler.getLeftPressed( )) {
			if (moveUp.getClicked( )) {
				if (player.moveUp(true)) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (moveRight.getClicked( )) {
				if (player.moveRight(true)) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (moveDown.getClicked( )) {
				if (player.moveDown(true)) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (moveLeft.getClicked( )) {
				if (player.moveLeft(true)) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (attackUp.getClicked( )) {
				if (player.searchInventory("bow")) {
					player.shootArrow(new Arrow(player.getX( ), player.getY( ), 0, player.getMap( )));
				} else if (player.searchInventory("sword")) {

				}
				wumpus.moveRandomly( );
				turns++;
			} else if (attackRight.getClicked( )) {
				if (player.searchInventory("bow")) {
					player.shootArrow(new Arrow(player.getX( ), player.getY( ), 1, player.getMap( )));
				} else if (player.searchInventory("sword")) {

				}
				wumpus.moveRandomly( );
				turns++;
			} else if (attackDown.getClicked( )) {
				if (player.searchInventory("bow")) {
					player.shootArrow(new Arrow(player.getX( ), player.getY( ), 2, player.getMap( )));
				} else if (player.searchInventory("sword")) {

				}
				wumpus.moveRandomly( );
				turns++;
			} else if (attackLeft.getClicked( )) {
				if (player.searchInventory("bow")) {
					player.shootArrow(new Arrow(player.getX( ), player.getY( ), 3, player.getMap( )));
				} else if (player.searchInventory("sword")) {

				}
				wumpus.moveRandomly( );
				turns++;
			} else if (player.searchInventory("compass")) {
				boolean inXBounds = (handler.getMouseX( ) > Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (player.getInventory( ).length / 2.0))
					+ (Constants.IMAGE_WIDTH * player.getIndexOfItem("compass")))
					&& (handler.getMouseX( ) < Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (player.getInventory( ).length / 2.0))
						+ (Constants.IMAGE_WIDTH * player.getIndexOfItem("compass")) + Constants.IMAGE_WIDTH);
				boolean inYBounds = handler.getMouseY( ) > (Constants.GAME_HEIGHT / 10) * 1 && handler.getMouseY( ) < ((Constants.GAME_HEIGHT / 10) * 1) + Constants.IMAGE_HEIGHT;

				if (inXBounds && inYBounds) {
					compassSelection = true;
					setButtons(true);
					events.clear( );
				}
			}
		}
	}

	public void setButtons (boolean disabled) {
		moveUp.setDisabled(disabled);
		moveRight.setDisabled(disabled);
		moveDown.setDisabled(disabled);
		moveLeft.setDisabled(disabled);
		attackUp.setDisabled(disabled);
		attackRight.setDisabled(disabled);
		attackDown.setDisabled(disabled);
		attackLeft.setDisabled(disabled);
	}

	public void setGameOverMessage (String str) {
		gameoverMessage = str;
	}

	public boolean getWin ( ) {
		return win;
	}

}
