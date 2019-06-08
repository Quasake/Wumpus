package me.quasar.wumpus.states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.graphics.components.buttons.Button;
import me.quasar.wumpus.graphics.components.buttons.ImageButton;
import me.quasar.wumpus.graphics.components.buttons.TextButton;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.menus.HighscoreTable;
import me.quasar.wumpus.utils.Constants;
import me.quasar.wumpus.utils.Handler;

public class ExtrasState extends State {
	private Map map;

	private Button backButton;

	private Button highscoreTableIncrease;
	private Button highscoreTableDecrease;
	private int highscoreTableNumber = Constants.MAP_MIN_SIZE;
	private HighscoreTable[ ] highscores;

	public ExtrasState (Handler handler) {
		super(handler);

		highscores = read("data/highscores.txt");
	}

	@Override
	public void init ( ) {
		backButton = new TextButton(Constants.INFOBOX_CENTER, (Constants.GAME_HEIGHT / 8) * 7, "Back", handler);

		highscoreTableIncrease = new ImageButton(Constants.INFOBOX_CENTER + (Constants.IMAGE_WIDTH / 2), Constants.GAME_HEIGHT / 4, 1, handler);
		highscoreTableDecrease = new ImageButton(Constants.INFOBOX_CENTER - (Constants.IMAGE_WIDTH / 2), Constants.GAME_HEIGHT / 4, 3, handler);

		map = new Map(Constants.MAP_SIZE, true);
		map.generateMap( );
	}

	@Override
	public void update ( ) {
		highscoreTableIncrease.update( );
		highscoreTableDecrease.update( );

		backButton.update( );

		if (highscoreTableNumber == Constants.MAP_MAX_SIZE && !highscoreTableIncrease.isDisabled( )) {
			highscoreTableIncrease.setDisabled(true);
		} else if (highscoreTableNumber < Constants.MAP_MAX_SIZE && highscoreTableIncrease.isDisabled( )) {
			highscoreTableIncrease.setDisabled(false);
		}
		if (highscoreTableNumber == Constants.MAP_MIN_SIZE && !highscoreTableDecrease.isDisabled( )) {
			highscoreTableDecrease.setDisabled(true);
		} else if (highscoreTableNumber > Constants.MAP_MIN_SIZE && highscoreTableDecrease.isDisabled( )) {
			highscoreTableDecrease.setDisabled(false);
		}

		if (backButton.isClicked( )) {
			goToState(handler.getGame( ).titlescreenState);
		} else if (highscoreTableIncrease.isClicked( )) {
			highscoreTableNumber++;
		} else if (highscoreTableDecrease.isClicked( )) {
			highscoreTableNumber--;
		}

		updatePanel( );
	}

	@Override
	public void render (Graphics graphics) {
		map.render(graphics);

		highscoreTableIncrease.render(graphics);
		highscoreTableDecrease.render(graphics);

		Renderer.drawText(highscoreTableNumber + " x " + highscoreTableNumber + " highscores : ", Constants.MAP_WIDTH / 8, Constants.GAME_HEIGHT / 4, Constants.GAME_TEXT_SIZE,
			false, Color.WHITE, graphics);
		highscores[highscoreTableNumber - Constants.MAP_MIN_SIZE].render(graphics);

		backButton.render(graphics);

		panel.render(graphics);
	}

	private HighscoreTable[ ] read (String file) {
		HighscoreTable[ ] scores = new HighscoreTable[Constants.MAP_MAX_SIZE - Constants.MAP_MIN_SIZE + 1];

		try {
			List<String> lines = Files.readAllLines(Paths.get(file), Charset.defaultCharset( ));

			int index = 0;
			for (int i = 0; i < scores.length; i++) {
				String[ ][ ] table = new String[5][2];

				for (int j = 0; j < 5; j++) {
					String[ ] line = lines.get(j + (i * 5)).split(":");
					table[j][0] = line[1];
					table[j][1] = line[2];
				}

				scores[index] = new HighscoreTable(table);
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace( );
		}

		return scores;
	}

	public void write (String file) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileWriter(file));

			for (int i = 0; i < highscores.length; i++) {
				for (int j = 0; j < 5; j++) {
					writer.println((i + Constants.MAP_MIN_SIZE) + ":" + (j + 1) + ":" + highscores[i].getScore(j));
				}
			}

			writer.close( );
		} catch (IOException e) {
			e.printStackTrace( );
		}
	}

	public void writeScore (int mapSize, int score) {
		highscores[mapSize - Constants.MAP_MIN_SIZE].addScore(score);
		write("data/highscores.txt");
	}

}
