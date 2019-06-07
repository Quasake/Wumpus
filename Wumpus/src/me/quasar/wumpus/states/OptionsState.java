package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.ImageButton;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class OptionsState extends State {
	private Map map;

	private Button increaseMapSize;
	private Button decreaseMapSize;

	private Button hazardsOn;
	private Button hazardsOff;

	private Button backButton;

	public OptionsState (Handler handler) {
		super(handler);
	}

	@Override
	public void init ( ) {
		increaseMapSize = new ImageButton(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH / 2), Constants.GAME_HEIGHT / 4, 1, handler);
		decreaseMapSize = new ImageButton(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH / 2), Constants.GAME_HEIGHT / 4, 3, handler);

		hazardsOn = new TextButton(Constants.INFOBOX_CENTER - Constants.IMAGE_WIDTH, (Constants.GAME_HEIGHT / 8) * 3, "On", handler);
		hazardsOff = new TextButton(Constants.INFOBOX_CENTER + Constants.IMAGE_WIDTH, (Constants.GAME_HEIGHT / 8) * 3, "Off", handler);

		backButton = new TextButton(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 8) * 7, "Back", handler);

		map = new Map(Constants.MAP_SIZE, true);
		map.generateMap( );
	}

	@Override
	public void update ( ) {
		increaseMapSize.update( );
		decreaseMapSize.update( );

		hazardsOn.update( );
		hazardsOff.update( );

		backButton.update( );

		if (Constants.MAP_SIZE == Constants.MAP_MAX_SIZE && !increaseMapSize.isDisabled( )) {
			increaseMapSize.setDisabled(true);
		} else if (Constants.MAP_SIZE < Constants.MAP_MAX_SIZE && increaseMapSize.isDisabled( )) {
			increaseMapSize.setDisabled(false);
		}
		if (Constants.MAP_SIZE == Constants.MAP_MIN_SIZE && !decreaseMapSize.isDisabled( )) {
			decreaseMapSize.setDisabled(true);
		} else if (Constants.MAP_SIZE > Constants.MAP_MIN_SIZE && decreaseMapSize.isDisabled( )) {
			decreaseMapSize.setDisabled(false);
		}

		if (Constants.SETTINGS_HAZARDS) {
			hazardsOn.setDisabled(true);
			hazardsOff.setDisabled(false);
		} else {
			hazardsOn.setDisabled(false);
			hazardsOff.setDisabled(true);
		}

		if (increaseMapSize.isClicked( )) {
			handler.getGame( ).setMapSize(Constants.MAP_SIZE + 1);
		} else if (decreaseMapSize.isClicked( )) {
			handler.getGame( ).setMapSize(Constants.MAP_SIZE - 1);
		} else if (backButton.isClicked( )) {
			goToState(handler.getGame( ).titlescreenState);
		} else if (hazardsOn.isClicked( )) {
			Constants.SETTINGS_HAZARDS = true;
		} else if (hazardsOff.isClicked( )) {
			Constants.SETTINGS_HAZARDS = false;
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		Renderer.drawText("Map size : " + Constants.MAP_SIZE + " x " + Constants.MAP_SIZE, Constants.MAP_WIDTH / 2, Constants.GAME_HEIGHT / 4, Constants.GAME_TEXT_SIZE, true,
			Color.LIGHT_GRAY, graphics);

		increaseMapSize.render(graphics);
		decreaseMapSize.render(graphics);

		Renderer.drawText("Hazards : " + ((Constants.SETTINGS_HAZARDS) ? "On" : "Off"), Constants.MAP_WIDTH / 2, (Constants.GAME_HEIGHT / 8) * 3, Constants.GAME_TEXT_SIZE, true,
			Color.LIGHT_GRAY, graphics);

		hazardsOn.render(graphics);
		hazardsOff.render(graphics);

		backButton.render(graphics);

		panel.render(graphics);
	}

}
