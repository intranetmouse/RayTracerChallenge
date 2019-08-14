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

public final class Cylinder
	extends Shape
{
	public Cylinder()
	{
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		Vector rayDirection = ray.getDirection();
		double a = rayDirection.getX() * rayDirection.getX() +
			rayDirection.getZ() * rayDirection.getZ();

		List<Intersection> xs = new ArrayList<>();

		if (!Tuple.dblEqual(a, Tuple.EPSILON))
		{
			Point rayOrigin = ray.getOrigin();
			double b = 2 * rayOrigin.getX() * rayDirection.getX() +
				2 * rayOrigin.getZ() * rayDirection.getZ();
			double c = rayOrigin.getX() * rayOrigin.getX() +
				rayOrigin.getZ() * rayOrigin.getZ() - 1;

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

			double y0 = rayOrigin.getY() + t0 * rayDirection.getY();
			if (minimum < y0 && y0 < maximum)
				xs.add(new Intersection(t0, this));

			double y1 = rayOrigin.getY() + t1 * rayDirection.getY();
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
		double dist = point.getX() * point.getX() + point.getZ() * point.getZ();
		if (dist < 1 && point.getY() >= (maximum - Tuple.EPSILON))
			return new Vector(0, 1, 0);
		else if (dist < 1 && point.getY() <= (minimum + Tuple.EPSILON))
			return new Vector(0, -1, 0);
		else
			return new Vector(point.getX(), 0, point.getZ());
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Cylinder))
			return false;
		return super.equals(other);
	}

	private double minimum = Double.NEGATIVE_INFINITY;
	public double getMinimum() { return minimum; }
	public void setMinimum(double value) { minimum = value; }

	private double maximum = Double.POSITIVE_INFINITY;
	public double getMaximum() { return maximum; }
	public void setMaximum(double value) { maximum = value; }

	private boolean closed;
	public boolean isClosed() { return closed; }
	public void setClosed(boolean value) { closed = value; }
}
