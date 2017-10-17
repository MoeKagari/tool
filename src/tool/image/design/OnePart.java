package tool.image.design;

import java.awt.Point;
import java.awt.image.BufferedImage;

import tool.function.FunctionUtils;

public abstract class OnePart {
	public final Margin margin;
	public final Point size;
	public final Point location;
	public final int locationMode;

	public OnePart(Margin margin, Point size, Point location, int locationMode) {
		this.margin = FunctionUtils.notNull(margin, FunctionUtils::returnSelf, new Margin(0, 0, 0, 0));
		this.size = size;
		this.location = FunctionUtils.notNull(location, FunctionUtils::returnSelf, new Point(0, 0));
		this.locationMode = locationMode;
	}

	protected BufferedImage getContentImage() {
		return new BufferedImage(this.size.x - this.margin.getX(), this.size.y - this.margin.getY(), BufferedImage.TYPE_INT_ARGB);
	}

	public final BufferedImage getImage() {
		BufferedImage image = new BufferedImage(this.size.x, this.size.y, BufferedImage.TYPE_INT_ARGB);
		image.createGraphics().drawImage(this.getContentImage(), this.margin.marginLeft, this.margin.marginTop, null);
		return image;
	}

	public static final class Margin {
		public final int marginTop, marginBottom, marginLeft, marginRight;

		public Margin(int marginTop, int marginBottom, int marginLeft, int marginRight) {
			this.marginTop = marginTop;
			this.marginBottom = marginBottom;
			this.marginLeft = marginLeft;
			this.marginRight = marginRight;
		}

		private int getX() {
			return this.marginLeft + this.marginRight;
		}

		private int getY() {
			return this.marginTop + this.marginBottom;
		}
	}
}
