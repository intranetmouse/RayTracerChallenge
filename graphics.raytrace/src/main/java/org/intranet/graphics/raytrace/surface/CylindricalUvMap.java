package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;

public final class CylindricalUvMap
	implements UvMap
{
	@Override
	public DoublePair map(Point p)
	{
		// compute the azimuthal angle, same as with spherical_map()
		double theta = Math.atan2(p.getX(), p.getZ());
		double raw_u = theta / (2 * Math.PI);
		double u = 1 - (raw_u + 0.5);

		// let v go from 0 to 1 between whole units of y
		double v = MathFuncs.mod1(p.getY());

		return new DoublePair(u, v);
	}
}