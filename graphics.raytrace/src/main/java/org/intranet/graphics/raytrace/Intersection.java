package org.intranet.graphics.raytrace;

public class Intersection
{
	private double distance;
	public double getDistance() { return distance; }

	private Sphere object;
	public Sphere getObject() { return object; }

	public Intersection(double distance, Sphere sphere)
	{
		this.distance = distance;
		this.object = sphere;
	}
}
