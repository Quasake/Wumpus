package me.quasar.wumpus.objects.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.graphics.Assets;
import me.quasar.wumpus.graphics.Renderer;
import me.quasar.wumpus.input.GameManager;
import me.quasar.wumpus.objects.Map;
import me.quasar.wumpus.objects.tiles.WallTile;

public class Arrow extends Entity {
	private BufferedImage image;
	private int direction;
	private boolean destroyed = false;

	public Arrow (float x, float y, int direction, Map map) {
		super(x, y, map);

		this.direction = direction;
		image = Renderer.rotate90(Assets.arrow, direction);
	}

	@Override
	public void update ( ) {
		if (!destroyed) {
			move( );

			if (!moving) {
				if (!(map.getGameTile(tileX, tileY) instanceof WallTile)) {
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
				} else {
					GameManager.addEvent("The arrow did not hit anything.");
					destroyed = true;
				}
			}
		}
	}

	@Override
	public void render (Graphics graphics) {
		if (!destroyed) {
			graphics.drawImage(image, (int) x, (int) y, null);
		}
	}

	@Override
	protected void updateAnimations ( ) {

	}

	public void destroy ( ) {
		destroyed = true;
	}

	public boolean isDestroyed ( ) {
		return destroyed;
	}

}
