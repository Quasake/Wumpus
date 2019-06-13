package me.quasar.wumpus.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	private boolean[ ] keys;

	public KeyManager ( ) {
		keys = new boolean[512];
	}

	@Override
	public void keyPressed (KeyEvent e) {
		try {
			keys[e.getKeyCode( )] = true;
		} catch (ArrayIndexOutOfBoundsException a) {

		}
	}

	@Override
	public void keyReleased (KeyEvent e) {
		try {
			keys[e.getKeyCode( )] = false;
		} catch (ArrayIndexOutOfBoundsException a) {

		}
	}

	@Override
	public void keyTyped (KeyEvent e) {

	}

	public boolean getKeyValue (int keyCode) {
		try {
			return keys[keyCode];
		} catch (ArrayIndexOutOfBoundsException a) {
			return false;
		}
	}

}
