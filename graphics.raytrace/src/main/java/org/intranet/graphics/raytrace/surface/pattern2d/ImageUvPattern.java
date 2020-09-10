package org.intranet.graphics.raytrace.surface.pattern2d;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.surface.map.Canvas;

public final class ImageUvPattern
	implements UvPattern
{
	private final Canvas canvas;

	public ImageUvPattern(Canvas canvas)
	{
		this.canvas = canvas;
	}

	@Override
	public Color colorAt(double u, double v)
	{
		// flip v over so it matches the image layout, with y at the top
		v = 1 - v;

		double x = u * (canvas.getWidth() - 1);
		double y = v * (canvas.getHeight() - 1);

		// round x and y to the nearest whole number
		return canvas.getPixelColor((int)Math.round(x), (int)Math.round(y));
	}
}