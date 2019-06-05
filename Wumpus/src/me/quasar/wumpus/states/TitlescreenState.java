package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.graphics.resources.Animation;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class TitlescreenState extends State {
	private Map map;

	private Animation animation;

	private Button playButton;
	private Button optionsButton;
	private Button extrasButton;
	private Button creditsButton;
	private Button quitButton;

	public TitlescreenState (Handler handler) {
		super(handler);
	}

	@Override
	public void init ( ) {
		playButton = new TextButton(Constants.INFOBOX_CENTER, Constants.GAME_HEIGHT / 4, "Play", handler);
		optionsButton = new TextButton(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 8) * 3, "Options", handler);
		extrasButton = new TextButton(Constants.INFOBOX_CENTER, Constants.GAME_HEIGHT / 2, "Extras", handler);
		creditsButton = new TextButton(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 8) * 5, "Credits", handler);
		quitButton = new TextButton(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 4) * 3, "Quit", handler);

		float value = (float) Math.random( );
		if (value < 0.3) {
			animation = Assets.playerIdle;
		} else if (value >= 0.3 && value < 0.6) {
			animation = Assets.playerIdleTorch;
		} else {
			animation = Assets.wumpusIdle;
		}

		map = new Map(Constants.MAP_SIZE, true);
		map.generateMap( );
	}

	@Override
	public void update ( ) {
		animation.update( );

		playButton.update( );
		optionsButton.update( );
		extrasButton.update( );
		creditsButton.update( );
		quitButton.update( );

		if (playButton.isClicked( )) {
			goToState(handler.getGame( ).gameState);
		} else if (optionsButton.isClicked( )) {
			goToState(handler.getGame( ).optionsState);
		} else if (extrasButton.isClicked( )) {
			goToState(handler.getGame( ).extrasState);
		} else if (creditsButton.isClicked( )) {
			goToState(handler.getGame( ).creditsState);
		} else if (quitButton.isClicked( )) {
			System.exit(0);
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		Renderer.drawImage(Assets.title, Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 4, true, graphics);
		Renderer.drawText("Created by Frank Alfano", Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2, Constants.GAME_TEXT_SIZE, true, Color.LIGHT_GRAY, graphics);
		Renderer.drawImage(Renderer.resizeImage(animation.getCurrentFrame( ), 3), Constants.MAP_WIDTH / 2, (Constants.MAP_HEIGHT / 4) * 3, true, graphics);

		playButton.render(graphics);
		optionsButton.render(graphics);
		extrasButton.render(graphics);
		creditsButton.render(graphics);
		quitButton.render(graphics);

		panel.render(graphics);
	}

}
