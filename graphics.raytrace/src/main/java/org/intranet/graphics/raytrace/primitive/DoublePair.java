package org.intranet.graphics.raytrace.primitive;

public final class DoublePair
{
	private final double first;
	public double getFirst() { return first; }

	private final double second;
	public double getSecond() { return second; }

	public DoublePair(double first, double second)
	{
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString()
	{ return "Pair [first=" + first + ", second=" + second + "]"; }
}