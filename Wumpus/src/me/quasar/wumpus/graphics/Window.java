package me.quasar.wumpus.graphics;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	private JFrame frame;
	private Canvas canvas;

	public Window (int width, int height, String title) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		canvas = new Canvas( );
		canvas.setFocusable(false);
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));

		frame.add(canvas);
		frame.pack( );
	}

	public JFrame getFrame ( ) {
		return frame;
	}

	public Canvas getCanvas ( ) {
		return canvas;
	}
}
