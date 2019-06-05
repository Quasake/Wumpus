package me.quasar.wumpus.states;

import java.awt.Graphics;

import me.quasar.wumpus.objects.Button;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class CreditsState extends State {
	private Map map;
	
	private Button backButton;

	public CreditsState (Handler handler) {
		super(handler);
	}

	@Override
	public void update ( ) {
		backButton.update( );
		
		if (backButton.getClicked( )) {
			goToState = handler.getGame( ).titlescreenState;
			panel.fadeOut( );
		}
		
		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		backButton.render(graphics);
		
		panel.render(graphics);
	}

	@Override
	public void init ( ) {
		map = new Map(Constants.MAP_SIZE, Constants.MAP_SIZE);
		map.generateMap(true);
		
		backButton = new Button(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 10) * 9, "Back", handler);
	}

}
