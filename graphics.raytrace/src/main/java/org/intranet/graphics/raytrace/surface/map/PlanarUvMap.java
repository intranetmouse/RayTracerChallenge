package org.intranet.graphics.raytrace.surface.map;

import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;

public final class PlanarUvMap
	implements UvMap
{
	@Override
	public DoublePair map(Point p)
	{
		double u = MathFuncs.mod1(p.getX());

		double v = MathFuncs.mod1(p.getZ());

		return new DoublePair(u, v);
	}
}