package me.quasar.wumpus.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceLoader {
	public static BufferedImage loadImage (String path) {
		try {
			return ImageIO.read(ResourceLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace( );
			System.exit(1);
		}
		return null;
	}

	public static AudioInputStream loadSound (String path) {
		try {
			return AudioSystem.getAudioInputStream(ResourceLoader.class.getResource(path));
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace( );
			System.exit(1);
		}
		return null;
	}
}
