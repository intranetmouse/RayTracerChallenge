package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public class Triangle
	extends Shape
{
	private Point p1;
	public Point getP1() { return p1; }

	private Point p2;
	public Point getP2() { return p2; }

	private Point p3;
	public Point getP3() { return p3; }

	private final Vector e1;
	public Vector getE1() { return e1; }
	private final Vector e2;
	public Vector getE2() { return e2; }

	private Vector n1;
	public Vector getN1() { return n1; }

	private Vector n2;
	public Vector getN2() { return n2; }

	private Vector n3;
	public Vector getN3() { return n3; }

	private final Vector normal;
	public Vector getNormal() { return normal; }

	public Triangle(Point p1, Point p2, Point p3)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		e1 = p2.subtract(p1);
		e2 = p3.subtract(p1);
		normal = e2.cross(e1).normalize();
	}

	public Triangle(Point p1, Point p2, Point p3, Vector n1, Vector n2,
		Vector n3)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;

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
		return new IntersectionList(new Intersection(t, this, u, v));
	}

	@Override
	protected Vector localNormalAt(Point point, Intersection hit)
	{
		if (n1 == null)
			return normal;

		double u = hit == null ? 1 : hit.getU();
		double v = hit == null ? 1 : hit.getV();

		Vector n2u = getN2().multiply(u);
		Vector n3v = getN3().multiply(v);
		Vector n1scaled = getN1();
		if (hit != null)
			n1scaled = n1scaled.multiply(1 - u - v);
		return n2u.add(n3v).add(n1scaled);
	}

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
