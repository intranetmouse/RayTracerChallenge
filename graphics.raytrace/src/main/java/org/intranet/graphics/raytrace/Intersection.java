package org.intranet.graphics.raytrace;

public class Intersection
{
	private double distance;
	public double getDistance() { return distance; }

	private SceneObject object;
	public SceneObject getObject() { return object; }

	public Intersection(double distance, SceneObject sphere)
	{
		this.distance = distance;
		this.object = sphere;
	}
}
