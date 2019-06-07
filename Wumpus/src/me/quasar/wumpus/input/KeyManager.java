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
		keys[e.getKeyCode( )] = true;
	}

	@Override
	public void keyReleased (KeyEvent e) {
		keys[e.getKeyCode( )] = false;
	}

	@Override
	public void keyTyped (KeyEvent e) {

	}
	
	public boolean getKeyValue (int keyCode) {
		return keys[keyCode];
	}

}
