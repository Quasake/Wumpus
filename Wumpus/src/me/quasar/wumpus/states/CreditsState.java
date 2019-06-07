package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
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

		Renderer.drawText("Creator : Frank Alfano", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y, Constants.GAME_TEXT_SIZE, true, Color.LIGHT_GRAY, graphics);
		Renderer.drawText("Teacher : Mr. Grossi", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + Constants.INFOBOX_CREDITS_SPACING, Constants.GAME_TEXT_SIZE, true,
			Color.LIGHT_GRAY, graphics);
		Renderer.drawText("Language : Java", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 2), Constants.GAME_TEXT_SIZE, true,
			Color.LIGHT_GRAY, graphics);

		Renderer.drawText("Music : Bosca Ceoil", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 5), Constants.GAME_TEXT_SIZE, true,
			Color.LIGHT_GRAY, graphics);
		Renderer.drawText("Sound Effects : sfxr", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 6), Constants.GAME_TEXT_SIZE,
			true, Color.LIGHT_GRAY, graphics);

		Renderer.drawText("Art : paint.NET", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 9), Constants.GAME_TEXT_SIZE, true,
			Color.LIGHT_GRAY, graphics);
		Renderer.drawText("Font : fontstruct.com", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 10), Constants.GAME_TEXT_SIZE,
			true, Color.LIGHT_GRAY, graphics);

		Renderer.drawText("Special Thanks To :", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 13), Constants.GAME_TEXT_SIZE,
			true, Color.LIGHT_GRAY, graphics);
		Renderer.drawText("CodeNMore", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 14), Constants.GAME_TEXT_SIZE, true,
			Color.YELLOW, graphics);
		Renderer.drawText("Brackeys", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 15), Constants.GAME_TEXT_SIZE, true,
			Color.YELLOW, graphics);
		Renderer.drawText("Mr. Grossi [the god himself]", Constants.INFOBOX_CREDITS_X, Constants.INFOBOX_CREDITS_Y + (Constants.INFOBOX_CREDITS_SPACING * 16),
			Constants.GAME_TEXT_SIZE, true, Color.YELLOW, graphics);

		panel.render(graphics);
	}

}
