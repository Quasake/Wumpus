package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class OptionState extends State {
	private Map map;
	
	private Button backButton;
	private Button increaseSizeButton;
	private Button decreaseSizeButton;

	public OptionState (Handler handler) {
		super(handler);
	}

	@Override
	public void update ( ) {
		backButton.update( );

		increaseSizeButton.update( );
		decreaseSizeButton.update( );

		if (backButton.getClicked( )) {
			State.setState(handler.getGame( ).titlescreenState);
		}

		if (increaseSizeButton.getClicked( )) {
			handler.getGame( ).setBoardSize(Constants.MAP_SIZE + 1);
		}
		if (decreaseSizeButton.getClicked( )) {
			handler.getGame( ).setBoardSize(Constants.MAP_SIZE - 1);
		}
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);
		
		backButton.render(graphics);

		increaseSizeButton.render(graphics);
		decreaseSizeButton.render(graphics);

		Renderer.drawText("Map size : " + Constants.MAP_SIZE + " x " + Constants.MAP_SIZE, Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 4, Constants.GAME_TEXT_SIZE,
			Color.LIGHT_GRAY, graphics);
	}

	@Override
	public void init ( ) {
		map = new Map(Constants.MAP_SIZE, Constants.MAP_SIZE);
		map.generateMap(true);
		
		backButton = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 10) * 9, "Back", handler);
		increaseSizeButton = new Button(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH / 2), Constants.GAME_HEIGHT / 4, 1, handler);
		decreaseSizeButton = new Button(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH / 2), Constants.GAME_HEIGHT / 4, 3, handler);
	}

}
