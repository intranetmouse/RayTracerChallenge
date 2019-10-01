package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class TestShape
	extends Shape
{
	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		return null;
	}

	@Override
	protected Vector localNormalAt(Point point)
	{
		return new Vector(point.getX(), point.getY(), point.getZ());
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof TestShape))
			return false;
		return super.equals(other);
	}

	@Override
	public Shape deepCopy()
	{ return null; }
}