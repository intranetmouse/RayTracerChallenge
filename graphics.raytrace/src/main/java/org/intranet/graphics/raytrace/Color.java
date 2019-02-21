package org.intranet.graphics.raytrace;

public final class Color
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

	public void copyValues(Color other)
	{
		for (int i = 0; i < values.length; i++)
			values[i] = other.values[i];
	}
}