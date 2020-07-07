package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public class Triangle
	extends ThreePoint
{
	private final Vector e1;
	public Vector getE1() { return e1; }
	private final Vector e2;
	public Vector getE2() { return e2; }

	private final Vector normal;
	public Vector getNormal() { return normal; }

	public Triangle(Point p1, Point p2, Point p3)
	{
		super(p1, p2, p3);
		e1 = p2.subtract(p1);
		e2 = p3.subtract(p1);
		normal = e2.cross(e1).normalize();
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		Vector dir_cross_e2 = ray.getDirection().cross(e2);
		double det = getE1().dot(dir_cross_e2);
		if (Math.abs(det) < Tuple.EPSILON)
			return new IntersectionList();

		double f = 1.0 / det;
		Vector p1_to_origin = ray.getOrigin().subtract(getP1());
		double u = f * p1_to_origin.dot(dir_cross_e2);
		if (u < 0 || u > 1)
			return new IntersectionList();

		Vector origin_cross_e1 = p1_to_origin.cross(getE1());
		double v = f * ray.getDirection().dot(origin_cross_e1);
		if (v < 0 || (u + v) > 1)
			return new IntersectionList();

		double t = f * getE2().dot(origin_cross_e1);
		return new IntersectionList(new Intersection(t, this));
	}

	@Override
	protected Vector localNormalAt(Point point)
	{ return normal; }

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (!(other instanceof Triangle))
			return false;

		Triangle otherTri = (Triangle)other;
		return getP1().equals(otherTri.getP1()) &&
			getP2().equals(otherTri.getP2()) &&
			getP3().equals(otherTri.getP3());
	}

	@Override
	protected BoundingBox createBoundingBox()
	{
		BoundingBox box = new BoundingBox();

		box = box.add(getP1());
		box = box.add(getP2());
		box = box.add(getP3());

		return box;
	}

	@Override
	public Shape deepCopy()
	{
		return this;
	}
}
