package org.intranet.graphics.raytrace;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class IntersectionComputations
{
	public double getDistance() { return intersection.getDistance(); }

	public Shape getObject() { return intersection.getObject(); }

	private Point point;
	public Point getPoint() { return point; }

	private Vector eyeVector;
	public Vector getEyeVector() { return eyeVector; }

	private Vector normalVector;
	public Vector getNormalVector() { return normalVector; }

	private Intersection intersection;

	private boolean inside;
	public boolean isInside() { return inside; }

	private Point overPoint;
	public Point getOverPoint() { return overPoint; }

	public Color shadeHit(World world)
	{
		List<PointLight> lightSources = world.getLightSources();
		return lightSources.stream()
			.map(lightSource -> Tracer.lighting(getObject().getMaterial(),
				lightSource, overPoint, eyeVector, normalVector,
				Tracer.isShadowed(world, overPoint)))
			.reduce((a, b) -> a.add(b)).orElse(new Color(0, 0, 0));
	}

	public IntersectionComputations(Intersection intersection,
		Ray ray)
	{
		this.intersection = intersection;
		// copy the intersection's properties, for convenience
		// precompute some useful values
		this.point = ray.position(getDistance());
		this.eyeVector = ray.getDirection().normalize().negate();
		this.normalVector = getObject().normalAt(point);
		this.overPoint = point.add(normalVector.multiply(Tuple.EPSILON));

		if (normalVector.dot(eyeVector) < 0)
		{
			inside = true;
			normalVector = normalVector.negate();
		}
		// else inside = false;
	}
}