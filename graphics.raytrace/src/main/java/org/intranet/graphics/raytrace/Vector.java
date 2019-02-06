package org.intranet.graphics.raytrace;

public class Vector
	extends Tuple
{
	private static final Vector zeroVector = new Vector(0, 0, 0);

	public Vector(double x, double y, double z)
	{ super(x, y, z, 0.0); }

	public Vector cross(Vector b)
	{
		return new Vector(
			values[1] * b.values[2] - values[2] * b.values[1],
			values[2] * b.values[0] - values[0] * b.values[2],
			values[0] * b.values[1] - values[1] * b.values[0]);
	}

	public Tuple add(Tuple p) {
		double[] doubles = addDoubles(values, p.values);
		return new Vector(doubles[0], doubles[1], doubles[2]);
	}

	public Vector subtract(Vector a2)
	{
		double[] doubles = subtractDoubles(values, a2.values);
		return new Vector(doubles[0], doubles[1], doubles[2]);
	}

	public Vector negate()
	{
		double[] doubles = subtractDoubles(zeroVector.values, values);
		return new Vector(doubles[0], doubles[1], doubles[2]);
	}
}