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

public final class Cone
	extends TubeLike
{
	public Cone()
	{
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		Vector rayDirection = ray.getDirection();
		double directionX = rayDirection.getX();
		double directionY = rayDirection.getY();
		double directionZ = rayDirection.getZ();

		double a = directionX * directionX -
			directionY * directionY +
			directionZ * directionZ;

		Point rayOrigin = ray.getOrigin();
		double originX = rayOrigin.getX();
		double originY = rayOrigin.getY();
		double originZ = rayOrigin.getZ();

		double b = 2 * originX * directionX -
			2 * originY * directionY +
			2 * originZ * directionZ;
		double c = originX * originX -
			originY * originY +
			originZ * originZ;

		boolean aIsZero = Tuple.isZero(a);

		List<Intersection> xs = new ArrayList<>();

		if (aIsZero && !Tuple.isZero(b))
		{
			double t = -c / (2 * b);
			xs.add(new Intersection(t, this));
		}
		else if (!aIsZero)
		{
			double disc = b * b - 4 * a * c;

			if (disc >= 0)
			{
				double t0 = (-b - Math.sqrt(disc)) / (2 * a);
				double t1 = (-b + Math.sqrt(disc)) / (2 * a);
				if (t0 > t1)
				{
					double temp = t0;
					t0 = t1;
					t1 = temp;
				}

				double y0 = originY + t0 * directionY;
				if (minimum < y0 && y0 < maximum)
					xs.add(new Intersection(t0, this));

				double y1 = originY + t1 * directionY;
				if (minimum < y1 && y1 < maximum)
					xs.add(new Intersection(t1, this));
			}
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
		if (checkCap(ray, t, minimum))
			xs.add(new Intersection(t, this));

		// check for an intersection with the upper end cap by intersecting
		// the ray with the plane at y=cyl.maximum
		t = (maximum - rayOriginY) / rayDirectionY;
		if (checkCap(ray, t, maximum))
			xs.add(new Intersection(t, this));
	}

	/** a helper function to reduce duplication.
	* checks to see if the intersection at `t` is within a radius
	* of 1 (the radius of your cylinders) from the y axis.
	*/
	boolean checkCap(Ray ray, double t, double radius)
	{
		Point rayOrigin = ray.getOrigin();
		Vector rayDirection = ray.getDirection();
		double x = rayOrigin.getX() + t * rayDirection.getX();
		double z = rayOrigin.getZ() + t * rayDirection.getZ();
		return (x*x + z*z) <= Math.abs(radius);
	}

	@Override
	protected final Vector localNormalAt(Point point, Matrix inverse)
	{
		// compute the square of the distance from the y axis
		double pointX = point.getX();
		double pointZ = point.getZ();
		double dist = pointX * pointX + pointZ * pointZ;
		double pointY = point.getY();
		if (dist < 1 && pointY >= (maximum - Tuple.EPSILON))
			return new Vector(0, 1, 0);
		else if (dist < 1 && pointY <= (minimum + Tuple.EPSILON))
			return new Vector(0, -1, 0);
		else
		{
			double y = Math.sqrt(pointX * pointX + pointZ * pointZ);
			if (pointY > 0)
				y = -y;
			return new Vector(pointX, y, pointZ);
		}
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Cone))
			return false;
		return super.equals(other);
	}
}
