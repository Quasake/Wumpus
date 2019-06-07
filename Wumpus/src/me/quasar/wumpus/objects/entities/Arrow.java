package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.tiles.WallTile;
import me.quasar.wumpus.utils.Constants;

public class Arrow extends Entity {
	private boolean destroyed;
	private int direction;
	private boolean hitWall;

	private BufferedImage texture;

	public Arrow (float x, float y, int direction, Map map) {
		super(x, y, Constants.ENTITY_SPEED * 3, map);

		this.direction = direction;
		texture = Renderer.rotateImage(Assets.arrow, direction);
	}

	@Override
	public void update ( ) {
		if (!moving) {
			switch (direction) {
				case 0 :
					moveUp(false);
					break;
				case 1 :
					moveRight(false);
					break;
				case 2 :
					moveDown(false);
					break;
				case 3 :
					moveLeft(false);
					break;
			}
		}

		move( );
	}

	@Override
	public void render (Graphics graphics) {
		graphics.drawImage(texture, (int) x, (int) y, null);
	}

	@Override
	protected void updateAnimations ( ) {

	}

	public boolean isDestroyed ( ) {
		return destroyed;
	}

	public void destroy ( ) {
		destroyed = true;
	}

	public boolean hitWall ( ) {
		return hitWall;
	}

}
