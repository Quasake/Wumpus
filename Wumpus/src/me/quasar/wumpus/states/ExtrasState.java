package me.quasar.wumpus.states;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class ExtrasState extends State {
	private Map map;

	private Button backButton;

	public ExtrasState (Handler handler) {
		super(handler);
	}

	@Override
	public void init ( ) {
		backButton = new TextButton(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 8) * 7, "Back", handler);

		map = new Map(Constants.MAP_SIZE, true);
		map.generateMap( );
	}

	@Override
	public void update ( ) {
		backButton.update( );

		if (backButton.isClicked( )) {
			goToState(handler.getGame( ).titlescreenState);
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		backButton.render(graphics);

		panel.render(graphics);
	}

}
