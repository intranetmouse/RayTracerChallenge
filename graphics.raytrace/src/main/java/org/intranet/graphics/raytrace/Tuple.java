package org.intranet.graphics.raytrace;

public class Tuple
{
	public static Tuple point(double x, double y, double z)
	{ return new Tuple(x, y, z, 1.0); }
	public static Tuple vector(double x, double y, double z)
	{ return new Tuple(x, y, z, 0.0); }
	public static Tuple color(double x, double y, double z)
	{ return new Tuple(x, y, z, 0.0); }

	public Tuple(double x, double y, double z, double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public static final double EPSILON = 0.0000001;
	public static boolean dblEqual(double a, double b)
	{
		return Math.abs(a - b) < EPSILON;
	}

	double x;
	public double getX() { return x; }
	public double getRed() { return x; }

	double y;
	public double getY() { return y; }
	public double getGreen() { return y; }

	double z;
	public double getZ() { return z; }
	public double getBlue() { return z; }

	double w;
	public double getW() { return w; }

	public boolean isPoint()
	{
		return dblEqual(1.0, w);
	}

	public boolean isVector()
	{
		return dblEqual(0.0, w);
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Tuple))
			return false;
		Tuple otherTuple = (Tuple)other;
		return dblEqual(x, otherTuple.x) &&
				dblEqual(y, otherTuple.y) &&
				dblEqual(z, otherTuple.z) &&
				dblEqual(w, otherTuple.w);
	}

	public Tuple add(Tuple a2) {
		if (isPoint() && a2.isPoint())
			throw new IllegalArgumentException("Cannot add two points");
		return new Tuple(x + a2.x, y + a2.y, z + a2.z, w + a2.w);
	}

	public Tuple subtract(Tuple a2) {
		if (isVector() && a2.isPoint())
			throw new IllegalArgumentException("Cannot subtract a point from a vector");
		return new Tuple(x - a2.x, y - a2.y, z - a2.z, w - a2.w);
	}

	@Override
	public String toString()
	{
		return String.format("%s(%.8f,%.8f,%.8f)",
			isVector() ? "vector" : "point", x, y, z);
	}

	private static final Tuple zero = Tuple.vector(0, 0, 0);

	public Tuple negate()
	{
		Tuple diff = zero.subtract(this);
		return diff;
	}

	public Tuple multiply(double d)
	{
		return new Tuple(x*d, y*d, z*d, w*d);
	}

	public Tuple hadamard_product(Tuple d)
	{
		return new Tuple(x*d.x, y*d.y, z*d.z, w*d.w);
	}

	public Tuple divide(double d)
	{
		d = 1 / d;
		return new Tuple(x*d, y*d, z*d, w*d);
	}

	public double magnitude()
	{
		return magnitude(x, y, z, w);
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
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}

	public Tuple cross(Tuple b)
	{
		return vector(
			y * b.z - z * b.y,
			z * b.x - x * b.z,
			x * b.y - y * b.x);
	}
}
