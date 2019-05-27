package me.quasar.wumpus.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Renderer {

	public static void drawText (String text, float x, float y, float textSize, Color textColor, Graphics graphics) {
		Color previousColor = graphics.getColor( );
		Font previousFont = graphics.getFont( );
		graphics.setFont(previousFont.deriveFont(textSize));
		graphics.setColor(textColor);

		float fontWidth = (float) graphics.getFont( ).getStringBounds(text, new FontRenderContext(new AffineTransform( ), true, true)).getWidth( );
		float fontHeight = (float) graphics.getFont( ).getStringBounds(text, new FontRenderContext(new AffineTransform( ), true, true)).getHeight( );
		graphics.drawString(text, (int) (x - (fontWidth / 2)), (int) (y - (fontHeight / 2) + graphics.getFontMetrics(graphics.getFont( )).getAscent( )));

		graphics.setColor(previousColor);
		graphics.setFont(previousFont);
	}

	public static BufferedImage tint (BufferedImage loadImg, Color color) {
		BufferedImage img = new BufferedImage(loadImg.getWidth( ), loadImg.getHeight( ), BufferedImage.TRANSLUCENT);
		final float tintOpacity = 0.45f;
		Graphics2D g2d = img.createGraphics( );

		g2d.drawImage(loadImg, null, 0, 0);
		g2d.setColor(new Color(color.getRed( ) / 255f, color.getGreen( ) / 255f, color.getBlue( ) / 255f, tintOpacity));

		Raster data = loadImg.getData( );
		for (int x = data.getMinX( ); x < data.getWidth( ); x++) {
			for (int y = data.getMinY( ); y < data.getHeight( ); y++) {
				int[ ] pixel = data.getPixel(x, y, new int[4]);
				if (pixel[3] > 0) {
					g2d.fillRect(x, y, 1, 1);
				}
			}
		}
		g2d.dispose( );
		return img;
	}

}
