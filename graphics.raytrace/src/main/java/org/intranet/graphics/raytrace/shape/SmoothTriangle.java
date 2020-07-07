package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;

public class SmoothTriangle
	extends Shape
{
	private Point p1;
	public Point getP1() { return p1; }

	private Point p2;
	public Point getP2() { return p2; }

	private Point p3;
	public Point getP3() { return p3; }

	private Vector n1;
	public Vector getN1() { return n1; }

	private Vector n2;
	public Vector getN2() { return n2; }

	private Vector n3;
	public Vector getN3() { return n3; }


	public SmoothTriangle(Point p1, Point p2, Point p3, Vector n1, Vector n2,
		Vector n3)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Vector localNormalAt(Point point)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape deepCopy()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
