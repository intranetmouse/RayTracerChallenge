package org.intranet.graphics.raytrace.primitive;

public final class Color
	extends Tuple
{
	boolean outOfBounds;

	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(1, 1, 1);

	public Color(double x, double y, double z)
	{
		super(x, y, z, 0.0);
		outOfBounds = x < 0.0 || x > 1.0 || y < 0.0 || y > 1.0 || z < 0.0 ||
			z > 1.0;
	}

	public double getRed()
	{ return values[0]; }

	public double getGreen()
	{ return values[1]; }

	public double getBlue()
	{ return values[2]; }

	public Color add(Color c)
	{
		double[] doubles = addDoubles(values, c.values);
		return new Color(doubles[0], doubles[1], doubles[2]);
	}

	public Color subtract(Color c)
	{
		double[] doubles = subtractDoubles(values, c.values);
		return new Color(doubles[0], doubles[1], doubles[2]);
	}

	public Color hadamard_product(Color d)
	{
		return new Color(values[0]*d.values[0],
			values[1]*d.values[1],
			values[2]*d.values[2]);
	}

	public Color multiply(double d)
	{
		return new Color(values[0]*d, values[1]*d, values[2]*d);
	}

	public Color divide(double d)
	{
		return multiply(1 / d);
	}

	private static double clip(double value, double min, double max)
	{
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	public Color multiply(Color c2)
	{
		return new Color(
			values[0] * c2.values[0],
			values[1] * c2.values[1],
			values[2] * c2.values[2]);
	}

	public Color clipped()
	{
		if (!outOfBounds)
			return this;
		return new Color(clip(values[0], 0.0, 1.0), clip(values[1], 0.0, 1.0),
			clip(values[2], 0.0, 1.0));
	}

	public static final java.awt.Color rayColorToAwtColor(Color color)
	{
		java.awt.Color awtColor = new java.awt.Color(
			(int)(color.getRed() * 255.99),
			(int)(color.getGreen() * 255.99),
			(int)(color.getBlue() * 255.99));
		return awtColor;
	}
}