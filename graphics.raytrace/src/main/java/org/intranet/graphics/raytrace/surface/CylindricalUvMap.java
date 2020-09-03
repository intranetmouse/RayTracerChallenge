package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Pair;
import org.intranet.graphics.raytrace.primitive.Point;

public final class CylindricalUvMap
	implements UvMap
{
	@Override
	public Pair<Double> map(Point p)
	{
		// compute the azimuthal angle, same as with spherical_map()
		double theta = Math.atan2(p.getX(), p.getZ());
		double raw_u = theta / (2 * Math.PI);
		double u = 1 - (raw_u + 0.5);

		// let v go from 0 to 1 between whole units of y
		double v = PlanarUvMap.mod(p.getY());

		return new Pair<>(u, v);
	}
}