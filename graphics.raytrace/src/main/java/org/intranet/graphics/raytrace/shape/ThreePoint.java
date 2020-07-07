package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;

public abstract class ThreePoint
	extends Shape
{
	private Point p1;
	public Point getP1() { return p1; }

	private Point p2;
	public Point getP2() { return p2; }

	private Point p3;
	public Point getP3() { return p3; }

	protected ThreePoint(Point p1, Point p2, Point p3)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
}
