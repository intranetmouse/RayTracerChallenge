package org.intranet.graphics.raytrace;

public class Point
	extends Tuple
{

	public Point(double x, double y, double z)
	{
		super(x, y, z, 1.0);
	}

	public Point add(Vector v)
	{
		double[] doubles = addDoubles(values, v.values);
		return new Point(doubles[0], doubles[1], doubles[2]);
	}

	public Vector subtract(Point a2)
	{
		double[] doubles = subtractDoubles(values, a2.values);
		return new Vector(doubles[0], doubles[1], doubles[2]);
	}
	public Point subtract(Vector a2)
	{
		double[] doubles = subtractDoubles(values, a2.values);
		return new Point(doubles[0], doubles[1], doubles[2]);
	}

	public Point negate()
	{
		double[] doubles = subtractDoubles(zeroTuple.values, values);
		return new Point(doubles[0], doubles[1], doubles[2]);
	}

	public Point multiply(double d)
	{
		return new Point(values[0]*d, values[1]*d, values[2]*d);
	}
}