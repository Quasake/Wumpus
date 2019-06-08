package me.quasar.wumpus.objects.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.utils.Constants;

public class HighscoreTable {
	private ArrayList<String[ ]> table;

	public HighscoreTable (String[ ][ ] table) {
		this.table = new ArrayList<String[ ]>(Arrays.asList(table));
	}

	public void render (Graphics graphics) {
		for (int i = 0; i < table.size( ); i++) {
			Renderer.drawText("#" + table.get(i)[0] + " : " + table.get(i)[1], (Constants.MAP_WIDTH / 4) * 3,
				(Constants.GAME_HEIGHT / 4) - ((Constants.INFOBOX_HIGHSCORE_LENGTH / 2) * (Constants.GAME_TEXT_SIZE * 3)) + ((Constants.GAME_TEXT_SIZE * 3) * i),
				Constants.GAME_TEXT_SIZE, true, (i == 0) ? Color.YELLOW : Color.LIGHT_GRAY, graphics);
		}
	}

	public String getScore (int index) {
		return table.get(index)[1];
	}

	public void addScore (int score) {
		for (int i = 0; i < table.size( ); i++) {
			if (getScore(i).equals("-") || Integer.parseInt(getScore(i)) > score) {
				String[ ] newScore = new String[ ] { Integer.toString(i + 1), Integer.toString(score) };

				table.add(i, newScore);
				table.remove(table.size( ) - 1);

				for (int j = 0; j < table.size( ); j++) {
					table.get(j)[0] = Integer.toString(j + 1);
				}

				return;
			}
		}
	}

}
