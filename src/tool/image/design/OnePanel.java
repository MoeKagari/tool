package tool.image.design;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OnePanel extends OnePart {
	public final List<OnePart> childs = new ArrayList<>();

	public OnePanel(Point size) {
		super(new Margin(0, 0, 0, 0), size, new Point(0, 0), 0);
	}

	public OnePanel(Margin margin, Point size, Point location, int locationMode) {
		super(margin, size, location, locationMode);
	}

	@Override
	protected BufferedImage getContentImage() {
		BufferedImage image = super.getContentImage();
		Graphics2D g2d = image.createGraphics();
		this.childs.forEach(child -> drawOnePart(image.getWidth(), image.getHeight(), g2d, child));
		return image;
	}

	private static void drawOnePart(int width, int height, Graphics2D g2d, OnePart child) {
		BufferedImage image = child.getImage();
		if (image == null) return;

		int x = 0, y = 0;
		switch (child.locationMode) {
			case 0:
				x = child.location.x;
				y = child.location.y;
				break;
			case 1:
				x = width - child.location.x - child.size.x;
				y = child.location.y;
				break;
			case 2:
				x = child.location.x;
				y = height - child.location.y - child.size.y;
				break;
			case 3:
				x = width - child.location.x - child.size.x;
				y = height - child.location.y - child.size.y;
				break;
		}

		g2d.drawImage(image, x, y, null);
	}
}
