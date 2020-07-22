package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
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
		double distance = -ray.getOrigin().getY() / ray.getDirection().getY();
		return new IntersectionList(new Intersection(distance, this));
	}

	private static final Vector LOCAL_NORMAL = new Vector(0, 1, 0);
	@Override
	protected final Vector localNormalAt(Point point, Intersection intersection)
	{
		return LOCAL_NORMAL;
	}

	private static final BoundingBox boundingBox = new BoundingBox(
		new Point(Double.NEGATIVE_INFINITY, 0, Double.NEGATIVE_INFINITY),
		new Point(Double.POSITIVE_INFINITY, 0, Double.POSITIVE_INFINITY));

	public BoundingBox createBoundingBox()
	{
		return boundingBox;
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Plane))
			return false;
		return super.equals(other);
	}

	@Override
	public Shape deepCopy()
	{
		Plane shape = new Plane();
		shape.deepCopyFrom(this);
		return shape;
	}

	@Override
	public String toString()
	{
		return "Plane [" + super.toString() + "]";
	}
}
