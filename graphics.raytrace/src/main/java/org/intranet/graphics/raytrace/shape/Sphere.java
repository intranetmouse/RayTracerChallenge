package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Sphere
	extends Shape
{
	public Sphere()
	{
	}

	@Override
	public boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Sphere))
			return false;
		return super.equals(other);
	}

	@Override
	public IntersectionList localIntersections(Ray ray)
	{
		Vector sphereToRay = ray.getOrigin().subtract(new Point(0, 0, 0));
		double a = ray.getDirection().dot(ray.getDirection());
		double b = 2 * ray.getDirection().dot(sphereToRay);
		double c = sphereToRay.dot(sphereToRay) - 1;

		double discriminant = b * b - 4 * a * c;
		if (discriminant < 0)
			return IntersectionList.emptyList;

		double sqrtDiscriminant = Math.sqrt(discriminant);

		double twoA = 2 * a;
		double t1 = (-b - sqrtDiscriminant) / twoA;
		Intersection i1 = new Intersection(t1, this);
		double t2 = (-b + sqrtDiscriminant) / twoA;
		Intersection i2 = new Intersection(t2, this);
		return new IntersectionList(i1, i2);
	}

	@Override
	protected Vector localNormalAt(Point point)
	{
		return point.subtract(new Point(0, 0, 0));
	}

	@Override
	public String toString()
	{
		return String.format("%s:[xform=%s,mat=%s]", getClass().getSimpleName(),
			getTransform(), getMaterial());
	}
}
