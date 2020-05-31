package org.intranet.graphics.raytrace.primitive;

public class Point
	extends Tuple
{
	public static final Point NEGATIVE_INFINITY = new Point(
		Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	public static final Point POSITIVE_INFINITY = new Point(
		Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

	public Point(double x, double y, double z)
	{
		super(x, y, z, 1.0);
	}

	public Point(double x, double y, double z, double w)
	{
		super(x, y, z, w);
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