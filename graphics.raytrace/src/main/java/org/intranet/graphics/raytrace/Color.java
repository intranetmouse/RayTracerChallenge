package org.intranet.graphics.raytrace;

public class Color
	extends Tuple
{
	public Color(double x, double y, double z)
	{ super(x, y, z, 0.0); }

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
}