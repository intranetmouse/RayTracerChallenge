package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Plane
	extends Shape
{
	public Plane()
	{
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		if (Math.abs(ray.getDirection().getY()) < Tuple.EPSILON)
			return new IntersectionList();
		double dist = -ray.getOrigin().getY() / ray.getDirection().getY();
		return new IntersectionList(new Intersection(dist, this));
	}

	private static final Vector LOCAL_NORMAL = new Vector(0, 1, 0);
	@Override
	protected final Vector localNormalAt(Point point)
	{
		return LOCAL_NORMAL;
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Plane))
			return false;
		return super.equals(other);
	}
}
