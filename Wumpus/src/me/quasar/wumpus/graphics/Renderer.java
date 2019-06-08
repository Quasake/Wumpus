package me.quasar.wumpus.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import me.quasar.wumpus.utils.Constants;

public class Renderer {
	public static BufferedImage resizeImage (BufferedImage image, float scale) {
		BufferedImage resizedImage = new BufferedImage((int) (scale * image.getWidth( )), (int) (scale * image.getHeight( )), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) resizedImage.getGraphics( );
		g2d.scale(scale, scale);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose( );

		return resizedImage;
	}

	public static void drawText (String text, float x, float y, float textSize, boolean centered, Color textColor, Graphics graphics) {
		graphics.setFont(graphics.getFont( ).deriveFont(textSize));
		graphics.setColor(textColor);

		float fontWidth = (float) graphics.getFont( ).getStringBounds(text, new FontRenderContext(new AffineTransform( ), true, true)).getWidth( );
		float fontHeight = (float) graphics.getFont( ).getStringBounds(text, new FontRenderContext(new AffineTransform( ), true, true)).getHeight( );
		graphics.drawString(text, (int) ((centered) ? (x - (fontWidth / 2)) : x), (int) (y - (fontHeight / 2) + graphics.getFontMetrics(graphics.getFont( )).getAscent( )));
	}

	public static void drawImage (BufferedImage image, float x, float y, boolean centered, Graphics graphics) {
		int drawX = (int) ((centered) ? x - (image.getWidth( ) / 2) : x);
		int drawY = (int) ((centered) ? y - (image.getHeight( ) / 2) : y);

		graphics.drawImage(image, drawX, drawY, null);
	}

	public static BufferedImage rotateImage (BufferedImage image, int direction) {
		int width = image.getWidth( );
		int height = image.getHeight( );

		BufferedImage rotatedImage = new BufferedImage(height, width, image.getType( ));

		Graphics2D graphics2D = rotatedImage.createGraphics( );
		graphics2D.translate((height - width) / 2, (height - width) / 2);
		switch (direction) {
			case Constants.RIGHT :
				graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
				break;
			case Constants.DOWN :
				graphics2D.rotate(Math.PI, height / 2, width / 2);
				break;
			case Constants.LEFT :
				graphics2D.rotate((Math.PI / 2) * 3, height / 2, width / 2);
				break;
		}
		graphics2D.drawRenderedImage(image, null);

		return rotatedImage;
	}
}
