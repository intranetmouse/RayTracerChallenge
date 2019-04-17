package org.intranet.graphics.raytrace;

public class Intersection
{
	private double distance;
	public double getDistance() { return distance; }

	private Shape object;
	public Shape getObject() { return object; }

	public Intersection(double distance, Shape sphere)
	{
		this.distance = distance;
		this.object = sphere;
	}
}
