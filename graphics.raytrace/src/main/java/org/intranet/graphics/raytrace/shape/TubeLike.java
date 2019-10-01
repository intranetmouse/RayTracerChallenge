package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public abstract class TubeLike
	extends Shape
{
	protected double minimum = Double.NEGATIVE_INFINITY;
	public double getMinimum() { return minimum; }
	public void setMinimum(double value) { minimum = value; }

	protected double maximum = Double.POSITIVE_INFINITY;
	public double getMaximum() { return maximum; }
	public void setMaximum(double value) { maximum = value; }

	protected boolean closed;
	public boolean isClosed() { return closed; }
	public void setClosed(boolean value) { closed = value; }

	protected abstract void intersectSides(Ray ray, List<Intersection> xs);

	protected abstract double calcNormalY(double pointY, double dist);

	@Override
	public final IntersectionList localIntersections(Ray ray)
	{
		List<Intersection> xs = new ArrayList<>();

		intersectSides(ray, xs);

		intersectCaps(ray, xs, 1, 1);

		return new IntersectionList(xs);
	}

	protected final void calcSideIntersections(List<Intersection> xs, double a,
		double b, double c, double originY, double directionY)
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

			addSideIntersection(xs, t0, originY, directionY);
			addSideIntersection(xs, t1, originY, directionY);
		}
	}

	private final void addSideIntersection(List<Intersection> xs, double t,
		double originY, double directionY)
	{
// This does not work.  In cylinders.yml, the refraction is incorrect.
//		if (t <= 0)
//			return;

		double y = originY + t * directionY;
//System.out.println("t="+t+", y="+y+", minimum="+minimum+", maximum="+maximum);
		if (minimum < y && y < maximum)
			xs.add(new Intersection(t, this));
	}

	@Override
	protected final Vector localNormalAt(Point point)
	{
		// compute the square of the distance from the y axis
		double pointX = point.getX();
		double pointZ = point.getZ();
		double pointY = point.getY();

		double dist = pointX * pointX + pointZ * pointZ;
		if (closed && dist < 1 && pointY >= (maximum - Tuple.EPSILON))
			return new Vector(0, 1, 0);
		else if (closed && dist < 1 && pointY <= (minimum + Tuple.EPSILON))
			return new Vector(0, -1, 0);
		else
		{
			double y = calcNormalY(pointY, dist);
			return new Vector(pointX, y, pointZ);
		}
	}

	final void intersectCaps(Ray ray, List<Intersection> xs, double bottomRadius,
		double topRadius)
	{
		double rayDirectionY = ray.getDirection().getY();

		// caps only matter if the cylinder is closed, and might possibly be
		// intersected by the ray.
		if (!closed || Tuple.isZero(rayDirectionY))
			return;

		double rayOriginY = ray.getOrigin().getY();

		// check for an intersection with the lower end cap by intersecting
		// the ray with the plane at y=cyl.minimum
		double tMin = (minimum - rayOriginY) / rayDirectionY;
		if (checkCap(ray, tMin, getMinimumRadius()))
			xs.add(new Intersection(tMin, this));

		// check for an intersection with the upper end cap by intersecting
		// the ray with the plane at y=cyl.maximum
		double tMax = (maximum - rayOriginY) / rayDirectionY;
		if (checkCap(ray, tMax, getMaximumRadius()))
			xs.add(new Intersection(tMax, this));
	}

	protected abstract double getMinimumRadius();
	protected abstract double getMaximumRadius();

	/** a helper function to reduce duplication.
	* checks to see if the intersection at `t` is within a radius
	* of 1 (the radius of your cylinders) from the y axis.
	*/
	final boolean checkCap(Ray ray, double t, double radius)
	{
		Point rayOrigin = ray.getOrigin();
		Vector rayDirection = ray.getDirection();
		double x = rayOrigin.getX() + t * rayDirection.getX();
		double z = rayOrigin.getZ() + t * rayDirection.getZ();
		return (x*x + z*z) <= Math.abs(radius);
	}
}
