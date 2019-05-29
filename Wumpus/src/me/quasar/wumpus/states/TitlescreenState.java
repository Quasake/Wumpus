package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.TransitionPanel;
import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.graphics.resources.SpriteSheet;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class TitlescreenState extends State {
	private Map map;

	private Animation titleScreenAnimation;

	private Button playButton;
	private Button exitButton;
	private Button creditsButton;
	private Button optionsButton;

	public TitlescreenState (Handler handler) {
		super(handler);
	}

	@Override
	public void update ( ) {

		titleScreenAnimation.update( );

		playButton.update( );
		optionsButton.update( );
		creditsButton.update( );
		exitButton.update( );

		if (playButton.getClicked( )) {
			goToState = handler.getGame( ).gameState;
			panel.fadeOut( );
		}
		if (optionsButton.getClicked( )) {
			goToState = handler.getGame( ).optionState;
			panel.fadeOut( );
		}
		if (creditsButton.getClicked( )) {
			goToState = handler.getGame( ).creditsState;
			panel.fadeOut( );
		}
		if (exitButton.getClicked( )) {
			System.exit(1);
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		graphics.drawImage(Assets.title, (Constants.MAP_WIDTH / 2) - (Assets.title.getWidth( ) / 2), (Constants.MAP_HEIGHT / 4) - (Assets.title.getHeight( ) / 2), null);
		Renderer.drawText("Created by Frank Alfano", Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2, Constants.GAME_TEXT_SIZE, Color.LIGHT_GRAY, graphics);
		BufferedImage playerImage = SpriteSheet.resize(titleScreenAnimation.getCurrentFrame( ), 3);
		graphics.drawImage(playerImage, (Constants.MAP_WIDTH / 2) - (playerImage.getWidth( ) / 2), ((Constants.MAP_HEIGHT / 4) * 3) - (playerImage.getHeight( ) / 2), null);

		playButton.render(graphics);
		exitButton.render(graphics);
		optionsButton.render(graphics);
		creditsButton.render(graphics);

		panel.render(graphics);
	}

	@Override
	public void init ( ) {
		map = new Map(Constants.MAP_SIZE, Constants.MAP_SIZE);
		map.generateMap(true);

		float randValue = (float) Math.random( );
		if (randValue < 0.333) {
			titleScreenAnimation = Assets.playerIdleAnimation;
		} else if (randValue >= 0.333 && randValue < 0.666) {
			titleScreenAnimation = Assets.playerIdleTorchAnimation;
		} else {
			titleScreenAnimation = Assets.wumpusIdleAnimation;
		}

		playButton = new Button(Constants.INFOBOX_CENTER, Constants.GAME_HEIGHT / 4, "Play", handler);
		creditsButton = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) + (Constants.GAME_HEIGHT / 6), "Credits", handler);
		optionsButton = new Button(Constants.INFOBOX_CENTER, ((Constants.GAME_HEIGHT / 4) * 3) - (Constants.GAME_HEIGHT / 6), "Options", handler);
		exitButton = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) * 3, "Exit", handler);
	}

}
