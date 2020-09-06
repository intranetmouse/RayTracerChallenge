package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;

public class CheckerPattern
	extends Pattern
{
	private Color a;
	public Color getA() { return a; }

	private Color b;
	public Color getB() { return b; }

	public CheckerPattern(Color color1, Color color2)
	{
		a = color1;
		b = color2;
	}

	public Color colorAt(Point point)
	{
		double x = point.getX();
		double y = point.getY();
		double z = point.getZ();
		double dist = (Math.floor(x) + Math.floor(y) + Math.floor(z)) % 2;

		return dist % 2 == 0 ? a : b;
	}
}
