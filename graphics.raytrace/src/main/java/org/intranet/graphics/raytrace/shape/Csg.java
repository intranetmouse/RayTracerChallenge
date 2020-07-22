package org.intranet.graphics.raytrace.shape;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.ShapeParent;
import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;

public class Csg
	extends Shape
	implements ShapeParent
{
	private Shape left;
	public Shape getLeft() { return left; }

	private Shape right;
	public Shape getRight() { return right; }

	private CsgOperation operation;
	public CsgOperation getCsgOperation() { return operation; }

	public Csg(CsgOperation operation, Shape left, Shape right)
	{
		this.left = left;
		this.right = right;
		this.operation = operation;
		left.setParent(this);
		right.setParent(this);
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		if (!getBoundingBox().intersects(ray))
			return new IntersectionList();

		IntersectionList leftxs = left.intersections(ray);
		IntersectionList rightxs = right.intersections(ray);
		IntersectionList xs = leftxs.combineWith(rightxs);
		return filterIntersections(xs);
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

	@Override
	public Shape deepCopy()
	{
		return new Csg(operation, left.deepCopy(), right.deepCopy());
	}

	@Override
	public BoundingBox createBoundingBox()
	{
		BoundingBox box = left.getParentSpaceBounds();
		return box.add(right.getParentSpaceBounds());
	}

	public IntersectionList filterIntersections(IntersectionList ilist)
	{
		// begin outside of both children
		boolean inl = false;
		boolean inr = false;
		// prepare a list to receive the filtered intersections
		List<Intersection> intersections = new ArrayList<>();
		for (Intersection i : ilist.getIntersections())
		{
			// if i.object is part of the "left" child, then lhit is true
			boolean lhit = left.includes(i.getObject());
			if (operation.intersectionAllowed(lhit, inl, inr))
				intersections.add(i);

			// depending on which object was hit, toggle either inl or inr
			if (lhit)
				inl = !inl;
			else
				inr = !inr;
		}
		return new IntersectionList(intersections);
	}


	@Override
	public void divide(int threshold)
	{
		left.divide(threshold);
		right.divide(threshold);
	}
}
