package me.quasar.wumpus.input;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class GameManager {
	private Handler handler;

	private Button moveUp;
	private Button moveRight;
	private Button moveDown;
	private Button moveLeft;
	private Button attackUp;
	private Button attackRight;
	private Button attackDown;
	private Button attackLeft;
	
	private boolean playerMoving = false;
	private int turns = 0;

	public GameManager (Handler handler) {
		this.handler = handler;

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
		if (handler.getPlayer( ).getMoving( )) {
			moveUp.setDisabled(true);
			moveRight.setDisabled(true);
			moveDown.setDisabled(true);
			moveLeft.setDisabled(true);
			attackUp.setDisabled(true);
			attackRight.setDisabled(true);
			attackDown.setDisabled(true);
			attackLeft.setDisabled(true);
			turns++;
		} else {
			moveUp.setDisabled(false);
			moveRight.setDisabled(false);
			moveDown.setDisabled(false);
			moveLeft.setDisabled(false);
			attackUp.setDisabled(false);
			attackRight.setDisabled(false);
			attackDown.setDisabled(false);
			attackLeft.setDisabled(false);
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
			handler.getPlayer().moveUp( );
		} else if (moveRight.getClicked( )) {
			handler.getPlayer().moveRight( );
		} else if (moveDown.getClicked( )) {
			handler.getPlayer().moveDown( );
		} else if (moveLeft.getClicked( )) {
			handler.getPlayer().moveLeft( );
		}
	}

	public void render (Graphics graphics) {
		Renderer.drawText("Move", Constants.INFOBOX_CENTER, ((Constants.GAME_HEIGHT / 3) * 2) - (Constants.GAME_TEXT_SIZE * 3), Constants.GAME_TEXT_SIZE, Color.BLACK, graphics);
		moveUp.render(graphics);
		moveRight.render(graphics);
		moveDown.render(graphics);
		moveLeft.render(graphics);
		Renderer.drawText("Attack", Constants.INFOBOX_CENTER, ((Constants.GAME_HEIGHT / 10) * 9) - (Constants.GAME_TEXT_SIZE * 3), Constants.GAME_TEXT_SIZE, Color.BLACK, graphics);
		attackUp.render(graphics);
		attackRight.render(graphics);
		attackDown.render(graphics);
		attackLeft.render(graphics);
	}

}
