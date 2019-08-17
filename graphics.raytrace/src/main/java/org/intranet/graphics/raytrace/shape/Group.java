package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;

public class Group
	extends Shape
{
	private final List<Shape> children = new ArrayList<>();

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		return new IntersectionList();
	}

	@Override
	protected Vector localNormalAt(Point point, Matrix inverse)
	{
		return new Vector(0, 1, 0);
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return children.isEmpty();
	}

	public void addChild(Shape s)
	{
		children.add(s);
		s.setParent(this);
	}

	public boolean contains(Shape s)
	{
		return children.contains(s);
	}
}
