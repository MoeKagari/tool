package tool.image.design;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class OneImage extends OnePart {
	public final BufferedImage image;

	public OneImage(BufferedImage image) {
		this(image, null);
	}

	public OneImage(BufferedImage image, Point location) {
		super(null, new Point(image.getWidth(), image.getHeight()), location, 0);
		this.image = image;
	}

	@Override
	protected BufferedImage getContentImage() {
		return this.image;
	}
}
