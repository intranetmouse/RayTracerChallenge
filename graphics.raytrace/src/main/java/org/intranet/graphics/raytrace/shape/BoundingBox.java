package org.intranet.graphics.raytrace.shape;

import java.util.Arrays;
import java.util.List;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;

public class BoundingBox
{
	private Point min;
	public Point getMin()
	{ return min; }

	private Point max;
	public Point getMax()
	{ return max; }

	public BoundingBox()
	{
		this(Point.POSITIVE_INFINITY, Point.NEGATIVE_INFINITY);
	}

	public BoundingBox(Point minPoint, Point maxPoint)
	{
		min = minPoint;
		max = maxPoint;
	}

	public BoundingBox add(Point p)
	{
		if (p == null)
			throw new NullPointerException();
		Point newMin = new Point(
			Math.min(min.getX(), p.getX()),
			Math.min(min.getY(), p.getY()),
			Math.min(min.getZ(), p.getZ()));
		Point newMax = new Point(
			Math.max(max.getX(), p.getX()),
			Math.max(max.getY(), p.getY()),
			Math.max(max.getZ(), p.getZ()));
		return new BoundingBox(newMin, newMax);
	}

	public BoundingBox add(BoundingBox otherBox)
	{
		Point otherMin = otherBox.getMin();
		Point newMin = new Point(
			Math.min(min.getX(), otherMin.getX()),
			Math.min(min.getY(), otherMin.getY()),
			Math.min(min.getZ(), otherMin.getZ()));

		Point otherMax = otherBox.getMax();
		Point newMax = new Point(
			Math.max(max.getX(), otherMax.getX()),
			Math.max(max.getY(), otherMax.getY()),
			Math.max(max.getZ(), otherMax.getZ()));

		return new BoundingBox(newMin, newMax);
	}

	public boolean containsPoint(Point p)
	{
		return between(p.getX(), min.getX(), max.getX()) &&
			between(p.getY(), min.getY(), max.getY()) &&
			between(p.getZ(), min.getZ(), max.getZ());
	}

	private static boolean between(double a, double min, double max)
	{
		return a >= min && a <= max;
	}

	public boolean containsBox(BoundingBox otherBox)
	{
		return containsPoint(otherBox.getMin()) && containsPoint(otherBox.getMax());
	}

	public BoundingBox transform(Matrix mtx)
	{
		List<Point> points = Arrays.asList(
			min,
			new Point(min.getX(), min.getY(), max.getZ()),
			new Point(min.getX(), max.getY(), min.getZ()),
			new Point(min.getX(), max.getY(), max.getZ()),
			new Point(max.getX(), min.getY(), min.getZ()),
			new Point(max.getX(), min.getY(), max.getZ()),
			new Point(max.getX(), max.getY(), min.getZ()),
			max
		);

		BoundingBox box = new BoundingBox();

		for (Point p : points)
			box = box.add(mtx.multiply(p));

		return box;
	}
}
