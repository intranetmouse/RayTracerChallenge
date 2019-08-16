package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.primitive.Matrix;
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
	public IntersectionList localIntersections(Ray ray)
	{
		Vector rayDirection = ray.getDirection();
		double directionX = rayDirection.getX();
		double directionZ = rayDirection.getZ();
		double a = directionX * directionX +
			directionZ * directionZ;

		List<Intersection> xs = new ArrayList<>();

		if (!Tuple.dblEqual(a, Tuple.EPSILON))
		{
			Point rayOrigin = ray.getOrigin();
			double originX = rayOrigin.getX();
			double originZ = rayOrigin.getZ();
			double b = 2 * originX * directionX +
				2 * originZ * directionZ;
			double c = originX * originX +
				originZ * originZ - 1;

			double disc = b * b - 4 * a * c;

			if (disc < 0)
				return new IntersectionList();

			double t0 = (-b - Math.sqrt(disc)) / (2 * a);
			double t1 = (-b + Math.sqrt(disc)) / (2 * a);
			if (t0 > t1)
			{
				double temp = t0;
				t0 = t1;
				t1 = temp;
			}

			double originY = rayOrigin.getY();
			double directionY = rayDirection.getY();

			double y0 = originY + t0 * directionY;
			if (minimum < y0 && y0 < maximum)
				xs.add(new Intersection(t0, this));

			double y1 = originY + t1 * directionY;
			if (minimum < y1 && y1 < maximum)
				xs.add(new Intersection(t1, this));
		}

		intersectCaps(ray, xs);

		return new IntersectionList(xs);
	}

	void intersectCaps(Ray ray, List<Intersection> xs)
	{
		double rayDirectionY = ray.getDirection().getY();

		// caps only matter if the cylinder is closed, and might possibly be
		// intersected by the ray.
		if (!closed || Tuple.dblEqual(rayDirectionY, Tuple.EPSILON))
			return;

		double rayOriginY = ray.getOrigin().getY();

		// check for an intersection with the lower end cap by intersecting
		// the ray with the plane at y=cyl.minimum
		double t = (minimum - rayOriginY) / rayDirectionY;
		if (checkCap(ray, t))
			xs.add(new Intersection(t, this));

		// check for an intersection with the upper end cap by intersecting
		// the ray with the plane at y=cyl.maximum
		t = (maximum - rayOriginY) / rayDirectionY;
		if (checkCap(ray, t))
			xs.add(new Intersection(t, this));
	}

	/** a helper function to reduce duplication.
	* checks to see if the intersection at `t` is within a radius
	* of 1 (the radius of your cylinders) from the y axis.
	*/
	boolean checkCap(Ray ray, double t)
	{
		Point rayOrigin = ray.getOrigin();
		Vector rayDirection = ray.getDirection();
		double x = rayOrigin.getX() + t * rayDirection.getX();
		double z = rayOrigin.getZ() + t * rayDirection.getZ();
		return (x*x + z*z) <= 1;
	}

	@Override
	protected final Vector localNormalAt(Point point, Matrix inverse)
	{
		// compute the square of the distance from the y axis
		double pointX = point.getX();
		double pointY = point.getY();
		double pointZ = point.getZ();

		double dist = pointX * pointX + pointZ * pointZ;
		if (dist < 1 && pointY >= (maximum - Tuple.EPSILON))
			return new Vector(0, 1, 0);
		else if (dist < 1 && pointY <= (minimum + Tuple.EPSILON))
			return new Vector(0, -1, 0);
		else
			return new Vector(pointX, 0, pointZ);
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Cylinder))
			return false;
		return super.equals(other);
	}
}
