package org.intranet.graphics.raytrace.shape;

import java.util.Random;

public final class RandomSequence
	implements Sequence
{
	Random r = new Random(System.currentTimeMillis());

	public RandomSequence()
	{
	}

	public double next()
	{
		return r.nextDouble();
	}
}