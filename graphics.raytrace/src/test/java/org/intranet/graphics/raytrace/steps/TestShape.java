package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Matrix;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Vector;

public final class TestShape
	extends Shape
{
	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		return null;
	}

	@Override
	protected Vector localNormalAt(Point point, Matrix inverse)
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
}