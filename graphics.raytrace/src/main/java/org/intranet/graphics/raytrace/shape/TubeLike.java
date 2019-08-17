package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
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

			double y0 = originY + t0 * directionY;
			if (minimum < y0 && y0 < maximum)
				xs.add(new Intersection(t0, this));

			double y1 = originY + t1 * directionY;
			if (minimum < y1 && y1 < maximum)
				xs.add(new Intersection(t1, this));
		}
	}

	@Override
	protected final Vector localNormalAt(Point point, Matrix inverse)
	{
		// compute the square of the distance from the y axis
		double pointX = point.getX();
		double pointZ = point.getZ();
		double pointY = point.getY();

		double dist = pointX * pointX + pointZ * pointZ;
		if (dist < 1 && pointY >= (maximum - Tuple.EPSILON))
			return new Vector(0, 1, 0);
		else if (dist < 1 && pointY <= (minimum + Tuple.EPSILON))
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
	final boolean checkCap(Ray ray, double t, double radius)
	{
		Point rayOrigin = ray.getOrigin();
		Vector rayDirection = ray.getDirection();
		double x = rayOrigin.getX() + t * rayDirection.getX();
		double z = rayOrigin.getZ() + t * rayDirection.getZ();
		return (x*x + z*z) <= Math.abs(radius);
	}
}
