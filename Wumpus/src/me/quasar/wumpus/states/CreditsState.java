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

	private int line = 0;

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

		line = 0;

		drawLine("Creator : Frank Alfano", Color.WHITE, graphics);
		drawLine("Teacher : Mr. Grossi", Color.LIGHT_GRAY, graphics);
		drawLine("Language : Java", Color.LIGHT_GRAY, graphics);
		drawSpace(2);

		drawLine("Music : Bosca Ceoil", Color.LIGHT_GRAY, graphics);
		drawLine("Sound Effects : sfxr", Color.LIGHT_GRAY, graphics);
		drawSpace(2);

		drawLine("Art : paint.NET", Color.LIGHT_GRAY, graphics);
		drawLine("Font : fontstruct.com", Color.LIGHT_GRAY, graphics);
		drawSpace(2);

		drawLine("Special Thanks To :", Color.WHITE, graphics);
		drawLine("CodeNMore", Color.YELLOW, graphics);
		drawLine("Brackeys", Color.YELLOW, graphics);
		drawLine("Mr. Grossi [the god himself]", Color.YELLOW, graphics);

		panel.render(graphics);
	}

	private void drawLine (String text, Color color, Graphics graphics) {
		Renderer.drawText(text, Constants.INFOBOX_CREDITS_X,
			Constants.INFOBOX_CREDITS_Y - ((Constants.INFOBOX_CREDITS_LINES / 2) * (Constants.GAME_TEXT_SIZE * 1.5f)) + ((Constants.GAME_TEXT_SIZE * 1.5f) * line),
			Constants.GAME_TEXT_SIZE, true, color, graphics);
		line++;
	}

	private void drawSpace (int spaceSize) {
		line += spaceSize;
	}

}
