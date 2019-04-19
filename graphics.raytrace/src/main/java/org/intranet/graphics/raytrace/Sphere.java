package org.intranet.graphics.raytrace;

public class Sphere
	extends Shape
{
	public Sphere()
	{

	}

	@Override
	public boolean equals(Object other)
	{
		if (other == null || !(other instanceof Sphere))
			return false;
		Sphere otherSphere = (Sphere)other;
		if (!transform.equals(otherSphere.transform))
			return false;
		if (!getMaterial().equals(otherSphere.getMaterial()))
			return false;
		return true;
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
	public Vector normalAt(Point point)
	{
		Matrix inverse = transform.inverse();
		Point objectPoint = inverse.multiply(point);
		Vector objectNormalVector = objectPoint.subtract(new Point(0, 0, 0));
		Vector worldNormal = inverse.transpose().multiply(objectNormalVector);
		Vector v = new Vector(worldNormal.getX(), worldNormal.getY(), worldNormal.getZ());
		return v.normalize();
	}

	@Override
	public String toString()
	{
		return String.format("%s:[xform=%s,mat=%s]", getClass().getSimpleName(),
			transform, getMaterial());
	}
}
