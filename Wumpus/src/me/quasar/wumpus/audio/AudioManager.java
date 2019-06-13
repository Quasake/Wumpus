package me.quasar.wumpus.audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {

	public static synchronized void playSoundEffect (String soundPath) {
		new Thread(new Runnable( ) {
			public void run ( ) {
				try {
					Clip clip = AudioSystem.getClip( );
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(soundPath));

					clip.open(inputStream);
					clip.start( );
				} catch (Exception e) {
					e.printStackTrace( );
				}
			}
		}).start( );
	}

}
