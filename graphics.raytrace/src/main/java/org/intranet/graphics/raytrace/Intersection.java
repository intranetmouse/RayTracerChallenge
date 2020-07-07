package org.intranet.graphics.raytrace;

public class Intersection
{
	private double distance;
	public double getDistance() { return distance; }

	private Shape object;
	public Shape getObject() { return object; }

	private double u;
	public double getU() { return u; }

	private double v;
	public double getV() { return v; }

	public Intersection(double distance, Shape shape)
	{
		this.distance = distance;
		this.object = shape;
	}

	public Intersection(Double distance, Shape shape, double u, double v)
	{
		this(distance, shape);
		this.u = u;
		this.v = v;
	}
}
