package tool.image.design;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class OneString extends OnePart {
	public final String text;

	public OneString(String text, Margin margin, Point size, Point location, int locationMode) {
		super(margin, size, location, locationMode);
		this.text = text;
	}

	@Override
	protected BufferedImage getContentImage() {
		BufferedImage image = super.getContentImage();
		Graphics2D g2d = image.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(null, Font.BOLD, 15));

		FontMetrics fm = g2d.getFontMetrics();
		System.out.println(fm.getAscent() + "," + fm.getDescent() + "," + fm.getHeight());
		g2d.drawString(this.text, 0, 0 + fm.getAscent());

		return image;
	}
}
