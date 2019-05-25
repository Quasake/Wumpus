package me.quasar.wumpus.graphics.resources;

import java.awt.image.BufferedImage;

import me.quasar.wumpus.utils.Constants;

public class Animation {
	private int index = 0;
	private long lastTime;
	private long timer;
	
	private BufferedImage[] frames;

	public Animation (BufferedImage[] frames) {
		this.frames = frames;
		
		lastTime = System.currentTimeMillis( );
		timer = 0;
	}
	
	public void update () {
		timer += System.currentTimeMillis( ) - lastTime;
		lastTime = System.currentTimeMillis( );
		
		if (timer > Constants.SPRITE_ANIMATION_SPEED) {
			index++;
			timer = 0;
			
			if (index >= frames.length) {
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame () {
		return frames[index];
	}

}
