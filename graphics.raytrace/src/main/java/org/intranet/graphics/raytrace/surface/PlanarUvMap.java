package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Pair;
import org.intranet.graphics.raytrace.primitive.Point;

public final class PlanarUvMap
	implements UvMap
{
	@Override
	public Pair<Double> map(Point p)
	{
		double u = mod(p.getX());

		double v = mod(p.getZ());

		return new Pair<>(u, v);
	}

	private static final double mod(double val)
	{
		val = val % 1.0;
		if (val < -0.5)
			val = -val + 0.5 - 1;
		else if (val < 0.0)
			val = -val + 0.5;
		return val;
	}
}