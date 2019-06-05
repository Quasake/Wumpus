package me.quasar.wumpus.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

	public static BufferedImage tint (BufferedImage image, Color color) {
		BufferedImage result = new BufferedImage(image.getWidth( ), image.getHeight( ), BufferedImage.TRANSLUCENT);
		final float tintOpacity = 0.45f;
		Graphics2D g2d = result.createGraphics( );

		g2d.drawImage(image, null, 0, 0);
		g2d.setColor(new Color(color.getRed( ) / 255f, color.getGreen( ) / 255f, color.getBlue( ) / 255f, tintOpacity));

		Raster data = image.getData( );
		for (int x = data.getMinX( ); x < data.getWidth( ); x++) {
			for (int y = data.getMinY( ); y < data.getHeight( ); y++) {
				int[ ] pixel = data.getPixel(x, y, new int[4]);
				if (pixel[3] > 0) {
					g2d.fillRect(x, y, 1, 1);
				}
			}
		}
		g2d.dispose( );
		return result;
	}

	public static BufferedImage rotate90 (BufferedImage image, int direction) {
		int width = image.getWidth( );
		int height = image.getHeight( );

		BufferedImage result = new BufferedImage(height, width, image.getType( ));

		Graphics2D graphics2D = result.createGraphics( );
		graphics2D.translate((height - width) / 2, (height - width) / 2);
		switch (direction) {
			case 1 :
				graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
				break;
			case 2 :
				graphics2D.rotate(Math.PI, height / 2, width / 2);
				break;
			case 3 :
				graphics2D.rotate((Math.PI / 2) * 3, height / 2, width / 2);
				break;
		}
		graphics2D.drawRenderedImage(image, null);

		return result;
	}

}
