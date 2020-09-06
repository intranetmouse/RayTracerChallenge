package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;

public class RingPattern
	extends Pattern
{
	private Color a;
	public Color getA() { return a; }

	private Color b;
	public Color getB() { return b; }

	public RingPattern(Color color1, Color color2)
	{
		a = color1;
		b = color2;
	}

	public Color colorAt(Point point)
	{
		double x = point.getX();
		double z = point.getZ();
		double dist = Math.sqrt(x*x+z*z);
		long floorDist = (long)Math.floor(dist);

		return floorDist % 2 == 0 ? a : b;
	}
}
