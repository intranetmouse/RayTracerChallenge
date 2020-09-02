package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Pair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class SphericalUvMap
	implements UvMap
{
	@Override
	public Pair<Double> map(Point p)
	{
		// compute the azimuthal angle
		// -π < theta <= π
		// angle increases clockwise as viewed from above,
		// which is opposite of what we want, but we'll fix it later.
		double theta = Math.atan2(p.getX(), p.getZ());

		// vec is the vector pointing from the sphere's origin (the world origin)
		// to the point, which will also happen to be exactly equal to the sphere's
		// radius.
		Vector vec = new Vector(p.getX(), p.getY(), p.getZ());
		double radius = vec.magnitude();

		// compute the polar angle
		// 0 <= phi <= π
		double phi = Math.acos(p.getY() / radius);

		// -0.5 < raw_u <= 0.5
		double raw_u = theta / (2 * Math.PI);

		// 0 <= u < 1
		// here's also where we fix the direction of u. Subtract it from 1,
		// so that it increases counterclockwise as viewed from above.
		double u = 1 - (raw_u + 0.5);

		// we want v to be 0 at the south pole of the sphere,
		// and 1 at the north pole, so we have to "flip it over"
		// by subtracting it from 1.
		double v = 1 - phi / Math.PI;

		return new Pair<>(u, v);
	}
}