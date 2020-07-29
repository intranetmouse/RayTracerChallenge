package org.intranet.graphics.raytrace.shape;

public final class FixedSequence
	implements Sequence
{
	private double[] doubles;
	int idx = 0;

	public FixedSequence(double ... doubles)
	{
		this.doubles = doubles;
	}

	public double next()
	{
		double d = doubles[idx++];
		if (idx >= doubles.length)
			idx = 0;
		return d;
	}
}