package me.quasar.wumpus.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
	private int mouseX;
	private int mouseY;

	private boolean leftPressed;

	public MouseManager ( ) {

	}

	@Override
	public void mouseDragged (MouseEvent e) {

	}

	@Override
	public void mouseMoved (MouseEvent e) {
		mouseX = e.getX( );
		mouseY = e.getY( );
	}

	@Override
	public void mouseClicked (MouseEvent e) {

	}

	@Override
	public void mouseEntered (MouseEvent e) {

	}

	@Override
	public void mouseExited (MouseEvent e) {

	}

	@Override
	public void mousePressed (MouseEvent e) {
		if (e.getButton( ) == MouseEvent.BUTTON1) {
			leftPressed = true;
		}
	}

	@Override
	public void mouseReleased (MouseEvent e) {
		if (e.getButton( ) == MouseEvent.BUTTON1) {
			leftPressed = false;
		}
	}

	public int getMouseX ( ) {
		return mouseX;
	}

	public int getMouseY ( ) {
		return mouseY;
	}

	public boolean isLeftPressed ( ) {
		return leftPressed;
	}

}
