package org.intranet.graphics.raytrace;

public final class IntersectionComputations
{
	public double getDistance() { return intersection.getDistance(); }

	public SceneObject getObject() { return intersection.getObject(); }

	private Point point;
	public Point getPoint() { return point; }

	private Vector eyeVector;
	public Vector getEyeVector() { return eyeVector; }

	private Vector normalVector;
	public Vector getNormalVector() { return normalVector; }

	private Intersection intersection;

	public IntersectionComputations(Intersection intersection,
		Ray ray)
	{
		this.intersection = intersection;
		// copy the intersection's properties, for convenience
		// precompute some useful values
		this.point = ray.position(getDistance());
		this.eyeVector = ray.getDirection().normalize().negate();
		this.normalVector = getObject().normalAt(point);
	}
}