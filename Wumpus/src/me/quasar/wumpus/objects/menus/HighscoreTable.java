package me.quasar.wumpus.objects.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.utils.Constants;

public class HighscoreTable {
	private List<String[ ]> table;

	public HighscoreTable (String[ ][ ] table) {
		this.table = Arrays.asList(table);
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

	public void setScore (int index, int score) {
		table.get(index)[1] = Integer.toString(score);
	}

	public void addScore (int score) {
		for (int i = 0; i < table.size( ); i++) {
			if (Integer.parseInt(getScore(i)) > score) {
				table.add(new String[ ] { Integer.toString(i + 1), Integer.toString(score) });
			}
		}
	}

}
