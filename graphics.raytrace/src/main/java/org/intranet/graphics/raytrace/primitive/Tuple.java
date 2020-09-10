package org.intranet.graphics.raytrace.primitive;

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
	public static final double EPSILON4 = 0.0001;
	public static boolean dblEqual(double a, double b)
	{
		return a == b || Math.abs(a - b) <= EPSILON;
	}
	public static boolean isZero(double a)
	{
		return Math.abs(a) <= EPSILON;
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
		if (values.length != otherTuple.values.length)
			return false;
		for (int i = 0; i < values.length; i++)
			if (!dblEqual(values[i], otherTuple.values[i]))
				return false;
		return true;
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

	private static final int DEC = 10;
	String str;
	@Override
	public String toString()
	{
		if (str == null)
			str = calcStr();
		return str;
	}

	private String calcStr()
	{
		return String.format("%s(%." + DEC + "f,%." + DEC + "f,%." + DEC + "f[,%." + DEC + "f])",
			getClass().getSimpleName(),
			values[0], values[1], values[2], values[3]);
	}

	public Tuple multiply(double d)
	{
		return new Tuple(values[0]*d, values[1]*d, values[2]*d, values[3]*d);
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
