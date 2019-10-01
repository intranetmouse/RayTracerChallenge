package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Cube
	extends Shape
{
	public Cube()
	{
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		Pair<Double> xtPair = check_axis(ray.getOrigin().getX(), ray.getDirection().getX());
		double xtmin = xtPair.first;
		double xtmax = xtPair.getSecond();

		Pair<Double> ytPair = check_axis(ray.getOrigin().getY(), ray.getDirection().getY());
		double ytmin = ytPair.getFirst();
		double ytmax = ytPair.getSecond();

		Pair<Double> ztPair = check_axis(ray.getOrigin().getZ(), ray.getDirection().getZ());
		double ztmin = ztPair.first;
		double ztmax = ztPair.second;

		double tmin = Math.max(Math.max(xtmin, ytmin), ztmin);
		double tmax = Math.min(Math.min(xtmax, ytmax), ztmax);

		if (tmin > tmax)
			return new IntersectionList();

		Intersection i1 = new Intersection(tmin, this);
		Intersection i2 = new Intersection(tmax, this);
		return new IntersectionList(i1, i2);
	}

	private Pair<Double> check_axis(double origin, double direction)
	{
		double tmin_numerator = (-1 - origin);
		double tmax_numerator = (1 - origin);

		double tmin = tmin_numerator / direction;
		double tmax = tmax_numerator / direction;

//		double tmin, tmax;
//		if (Math.abs(direction) >= Tuple.EPSILON)
//		{
//			tmin = tmin_numerator / direction;
//			tmax = tmax_numerator / direction;
//		}
//		else
//		{
//			tmin = tmin_numerator < 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
//			tmax = tmax_numerator < 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
//		}

		if (tmin > tmax)
		{
			double temp = tmin;
			tmin = tmax;
			tmax = temp;
		}

		return new Pair<Double>(tmin, tmax);
	}

	public static final class Pair<T>
	{
		private final T first;
		public T getFirst() { return first; }

		private final T second;
		public T getSecond() { return second; }

		public Pair(T first, T second)
		{
			this.first = first;
			this.second = second;
		}
	}

	@Override
	protected final Vector localNormalAt(Point point)
	{
		double maxc = Math.max(Math.max(Math.abs(point.getX()), Math.abs(point.getY())), Math.abs(point.getZ()));

		if (maxc == Math.abs(point.getX()))
			return new Vector(point.getX(), 0, 0);
		else if (maxc == Math.abs(point.getY()))
			return new Vector(0, point.getY(), 0);
		else
			return new Vector(0, 0, point.getZ());
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Cube))
			return false;
		return super.equals(other);
	}

	@Override
	public Shape deepCopy()
	{
		Cube cube = new Cube();
		cube.deepCopyFrom(this);
		return cube;
	}
}
