package org.intranet.graphics.raytrace;

public class Tuple
{
	static final Tuple zeroTuple = new Tuple(0, 0, 0, 0);

	protected double[] values;
	public Tuple(double... values)
	{
		this.values = new double[values.length];
		for (int i = 0; i < values.length; i++)
			this.values[i] = values[i];
	}

	public static final double EPSILON = 0.0000001;
	public static boolean dblEqual(double a, double b)
	{
		return Math.abs(a - b) < EPSILON;
	}

	public double getX() { return values[0]; }
	public double getY() { return values[1]; }
	public double getZ() { return values[2]; }
	public double getW() { return values[3]; }

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Tuple))
			return false;
		Tuple otherTuple = (Tuple)other;
		return dblEqual(values[0], otherTuple.values[0]) &&
				dblEqual(values[1], otherTuple.values[1]) &&
				dblEqual(values[2], otherTuple.values[2]) &&
				dblEqual(values[3], otherTuple.values[3]);
	}

	public static double[] addDoubles(double[] a, double[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException();
		double[] result = new double[a.length];
		for (int i = 0; i < a.length; i++)
			result[i] = a[i] + b[i];
		return result;
	}

	public static double[] subtractDoubles(double[] a, double[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException();
		double[] result = new double[a.length];
		for (int i = 0; i < a.length; i++)
			result[i] = a[i] - b[i];
		return result;
	}

	public Tuple negate()
	{
		double[] doubles = subtractDoubles(zeroTuple.values, values);
		return new Tuple(doubles[0], doubles[1], doubles[2], doubles[3]);
	}

	@Override
	public String toString()
	{
		return String.format("%s(%.8f,%.8f,%.8f)",
			getClass().getSimpleName(),
			values[0], values[1], values[2]);
	}

	public Tuple multiply(double d)
	{
		return new Tuple(values[0]*d, values[1]*d, values[2]*d, values[3]*d);
	}

	public Tuple hadamard_product(Tuple d)
	{
		return new Tuple(values[0]*d.values[0],
			values[1]*d.values[1],
			values[2]*d.values[2],
			values[3]*d.values[3]);
	}

	public Tuple divide(double d)
	{
		d = 1 / d;
		return new Tuple(values[0]*d, values[1]*d, values[2]*d, values[3]*d);
	}

	public double magnitude()
	{
		return magnitude(values[0], values[1], values[2], values[3]);
	}

	public static double magnitude(double ... dbls)
	{
		double squaresTotal = 0;
		for (double d : dbls)
			squaresTotal += d * d;
		return Math.sqrt(squaresTotal);
	}

	public Tuple normalize()
	{
		return divide(magnitude());
	}

	public double dot(Tuple other)
	{
		return values[0] * other.values[0] +
			values[1] * other.values[1] +
			values[2] * other.values[2] +
			values[3] * other.values[3];
	}
}
