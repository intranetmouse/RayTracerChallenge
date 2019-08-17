package org.intranet.graphics.raytrace.shape;

import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Cylinder
	extends TubeLike
{
	public Cylinder()
	{
	}

	@Override
	protected void intersectSides(Ray ray, List<Intersection> xs)
	{
		Vector rayDirection = ray.getDirection();
		double directionX = rayDirection.getX();
		double directionZ = rayDirection.getZ();
		double a = directionX * directionX +
			directionZ * directionZ;

		if (!Tuple.dblEqual(a, Tuple.EPSILON))
		{
			Point rayOrigin = ray.getOrigin();
			double originX = rayOrigin.getX();
			double originZ = rayOrigin.getZ();
			double b = 2 * originX * directionX +
				2 * originZ * directionZ;
			double c = originX * originX +
				originZ * originZ - 1;

			double originY = rayOrigin.getY();
			double directionY = rayDirection.getY();

			calcSideIntersections(xs, a, b, c, originY, directionY);
		}
	}

	@Override
	protected double calcNormalY(double pointY, double dist)
	{ return 0; }

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Cylinder))
			return false;
		return super.equals(other);
	}
}
