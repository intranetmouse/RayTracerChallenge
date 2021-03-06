package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;

public class StripePattern
	extends Pattern
{
	private Color a;
	public Color getA() { return a; }

	private Color b;
	public Color getB() { return b; }

	public StripePattern(Color color1, Color color2)
	{
		a = color1;
		b = color2;
	}

	public Color colorAt(Point point)
	{
		int xInt = ((int)Math.floor(point.getX())) % 2;
		return xInt == 0 ? a : b;
	}
}
