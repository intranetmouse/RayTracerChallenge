package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;

public final class PlanarUvMap
	implements UvMap
{
	@Override
	public DoublePair map(Point p)
	{
		double u = mod(p.getX());

		double v = mod(p.getZ());

		return new DoublePair(u, v);
	}

	static final double mod(double val)
	{
		if (val < 0)
			return Math.abs(Math.floor(val)) + val;
		return val - Math.floor(val);
//		val = val % 1.0;
//		if (val < -0.5)
//			val = -val + 0.5 - 1;
//		else if (val < 0.0)
//			val = -val + 0.5;
//		return val;
	}
}