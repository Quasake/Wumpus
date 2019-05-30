package me.quasar.wumpus.input;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.entities.Player;
import me.quasar.wumpus.objects.entities.Wumpus;
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

	private int turns = 0;
	private static ArrayList<String> events = new ArrayList<String>( );
	private boolean updatedEvents = false;
	private boolean gameover = false;
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
	}

	public void update ( ) {
		if (!gameover) {
			if (player.getMoving( )) {
				moveUp.setDisabled(true);
				moveRight.setDisabled(true);
				moveDown.setDisabled(true);
				moveLeft.setDisabled(true);
				attackUp.setDisabled(true);
				attackRight.setDisabled(true);
				attackDown.setDisabled(true);
				attackLeft.setDisabled(true);

				if (updatedEvents) {
					updatedEvents = false;
					events.clear( );
				}
			} else {
				moveUp.setDisabled(false);
				moveRight.setDisabled(false);
				moveDown.setDisabled(false);
				moveLeft.setDisabled(false);
				attackUp.setDisabled(false);
				attackRight.setDisabled(false);
				attackDown.setDisabled(false);
				attackLeft.setDisabled(false);

				if (!updatedEvents) {
					updateEvents( );
					updatedEvents = true;

					if (player.getTileX( ) == wumpus.getTileX( ) && player.getTileY( ) == wumpus.getTileY( )) {
						float chance = 0.05f + ((player.hasWeapon( )) ? 0.6f : 0f);
						gameover = true;

						if (Math.random( ) < chance) {
							win = true;
						} else {
							win = false;
						}
					}
				}
			}

			moveUp.update( );
			moveRight.update( );
			moveDown.update( );
			moveLeft.update( );
			attackUp.update( );
			attackRight.update( );
			attackDown.update( );
			attackLeft.update( );

			if (moveUp.getClicked( )) {
				if (player.moveUp( )) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (moveRight.getClicked( )) {
				if (player.moveRight( )) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (moveDown.getClicked( )) {
				if (player.moveDown( )) {
					wumpus.moveRandomly( );
				}
				turns++;
			} else if (moveLeft.getClicked( )) {
				if (player.moveLeft( )) {
					wumpus.moveRandomly( );
				}
				turns++;
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
				Renderer.drawText(events.get(i), Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) + (Constants.GAME_TEXT_SIZE * i), Constants.GAME_TEXT_SIZE / 2, Color.BLACK,
					graphics);
			}

			for (int i = 0; i < player.getInventory( ).length; i++) {
				graphics.drawImage(Assets.inventorySlot,
					(int) (Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (player.getInventory( ).length / 2.0)) + (Constants.IMAGE_WIDTH * i)),
					(Constants.GAME_HEIGHT / 10) * 1, null);
				if (player.getInventory( )[i] != null) {
					graphics.drawImage(player.getInventory( )[i].getTexture( ),
						(int) (Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH * (player.getInventory( ).length / 2.0)) + (Constants.IMAGE_WIDTH * i)),
						(Constants.GAME_HEIGHT / 10) * 1, null);
				}
			}
		}

		Renderer.drawText("Turns : " + turns, (Constants.IMAGE_WIDTH / 2) * 3, Constants.GAME_HEIGHT - (Constants.IMAGE_HEIGHT / 2), Constants.GAME_TEXT_SIZE, Color.LIGHT_GRAY,
			graphics);
	}

	private void updateEvents ( ) {
		if ((Math.abs(player.getTileX( ) - wumpus.getTileX( )) <= 2 && Math.abs(player.getTileX( ) - wumpus.getTileX( )) >= -2)
			&& (Math.abs(player.getTileY( ) - wumpus.getTileY( )) <= 2 && Math.abs(player.getTileY( ) - wumpus.getTileY( )) >= -2) && wumpus.isHidden( )) {
			addEvent("You smell wumpus.");
		} else if (Math.abs(player.getTileX( ) - wumpus.getTileX( )) <= player.getTorchNumber( ) && Math.abs(player.getTileY( ) - wumpus.getTileY( )) <= player.getTorchNumber( )) {
			addEvent("You found wumpus droppings.");
		}

	}

	public static void addEvent (String event) {
		events.add(event);
	}

	public boolean getGameOver ( ) {
		return gameover;
	}

	public boolean getWin ( ) {
		return win;
	}

}
