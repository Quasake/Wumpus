package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.utils.Constants;

public class Trap extends Entity {
	private boolean hidden;

	public Trap (float x, float y, Map map) {
		super(x, y, 0, map);
	}

	@Override
	public void update ( ) {
		hidden = (map.getTile(moveTileX + Constants.MAP_BORDER, moveTileY + Constants.MAP_BORDER).isHidden( )
			|| map.getTile(moveTileX + Constants.MAP_BORDER, moveTileY + Constants.MAP_BORDER).isCovered( ))
			&& (map.getTile(tileX + Constants.MAP_BORDER, tileY + Constants.MAP_BORDER).isHidden( )
				|| map.getTile(tileX + Constants.MAP_BORDER, tileY + Constants.MAP_BORDER).isCovered( ));
	}

	@Override
	public void render (Graphics graphics) {
		if (!hidden) {
			graphics.drawImage(Assets.trapTile, (int) x, (int) y, null);
		}
	}

	@Override
	protected void updateAnimations ( ) {

	}

}
