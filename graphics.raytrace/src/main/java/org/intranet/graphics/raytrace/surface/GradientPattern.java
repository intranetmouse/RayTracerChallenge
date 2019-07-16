package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Point;

public class GradientPattern
	extends Pattern
{
	private Color a;
	public Color getA() { return a; }

	private Color b;
	public Color getB() { return b; }

	public GradientPattern(Color color1, Color color2)
	{
		a = color1;
		b = color2;
	}

	public Color colorAt(Point point)
	{
		Color distance = b.subtract(a);
		double fraction = point.getX() - Math.floor(point.getX());
		return a.add(distance.multiply(fraction));
	}
}
