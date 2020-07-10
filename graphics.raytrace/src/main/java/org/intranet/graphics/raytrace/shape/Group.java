package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.intranet.graphics.raytrace.shape.Cube.Pair;

public final class Group
	extends Shape
	implements ShapeParent
{
	private final List<Shape> children = new ArrayList<>();
	public List<Shape> getChildren() { return children; }

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		if (!getBoundingBox().intersects(ray))
			return new IntersectionList();

		List<Intersection> intersections = children.parallelStream()
			.map(e -> e.intersections(ray))
			.map(IntersectionList::getIntersections)
			.flatMap(List<Intersection>::stream)
			.sorted(Comparator.comparingDouble(Intersection::getDistance))
			.collect(Collectors.toList());
		return new IntersectionList(intersections);
	}

	@Override
	protected Vector localNormalAt(Point point, Intersection intersection)
	{
		throw new IllegalStateException("See Ch. 14 p. 200.");
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		return other == this;
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
	public BoundingBox createBoundingBox()
	{
		BoundingBox box = new BoundingBox();
		for (Shape child : children)
		{
			BoundingBox childBox = child.getParentSpaceBounds();
			box = box.add(childBox);
		}
		return box;
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

	public Pair<List<Shape>> partitionChildren()
	{
		List<Shape> leftList = new ArrayList<>();
		List<Shape> rightList = new ArrayList<>();
		List<Shape> newChildrenList = new ArrayList<>();

		BoundingBox box = getBoundingBox();
		Pair<BoundingBox> twoBoxes = box.split();
		for (Shape child : children)
		{
			BoundingBox childBox = child.getParentSpaceBounds();
			boolean left = twoBoxes.getFirst().containsBox(childBox);
			boolean right = twoBoxes.getSecond().containsBox(childBox);
			if (left && !right)
				leftList.add(child);
			else if (right && !left)
				rightList.add(child);
			else
				newChildrenList.add(child);
		}
		children.clear();
		children.addAll(newChildrenList);

		return new Pair<>(leftList, rightList);
	}

	public void createSubgroup(Shape ... shapes)
	{
		createSubgroup(Arrays.asList(shapes));
	}

	public void createSubgroup(List<Shape> shapes)
	{
		Group sub = new Group();
		for (Shape shape : shapes)
			sub.addChild(shape);
		addChild(sub);
	}

	@Override
	public void divide(int threshold)
	{
		int numChildren = children.size();
		if (numChildren >= threshold)
		{
			Pair<List<Shape>> branches = partitionChildren();
			List<Shape> left = branches.getFirst();
			List<Shape> right = branches.getSecond();
			if (left != null && !left.isEmpty())
				createSubgroup(left);
			if (right != null && !right.isEmpty())
				createSubgroup(right);
		}

		for (Shape child : children)
			child.divide(threshold);
	}
}
