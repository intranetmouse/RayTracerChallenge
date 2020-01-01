package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.ShapeParent;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Group
	extends Shape
	implements ShapeParent
{
	private final List<Shape> children = new ArrayList<>();
	public List<Shape> getChildren() { return children; }

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		List<Intersection> intersections = children.parallelStream()
			.map(e -> e.intersections(ray).getIntersections())
			.flatMap(e -> e.stream())
			.sorted(Comparator.comparingDouble(Intersection::getDistance))
			.collect(Collectors.toList());
		return new IntersectionList(intersections);
	}

	@Override
	protected Vector localNormalAt(Point point)
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

	@Override
	public Shape deepCopy()
	{
		Group shape = new Group();

		shape.deepCopyFrom(this);
		for (Shape child : children)
			shape.addChild(child.deepCopy());
		return shape;
	}

	@Override
	public String toString()
	{
		return "Group [" + super.toString() + ", children=" + children + "]";
	}
}
