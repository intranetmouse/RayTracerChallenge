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

	public void copyValues(Color other, double min, double max)
	{
		for (int i = 0; i < values.length; i++)
			values[i] = clip(other.values[i], min, max);
	}
	private double clip(double value, double min, double max)
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
}