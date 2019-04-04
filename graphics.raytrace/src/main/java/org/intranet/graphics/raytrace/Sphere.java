package org.intranet.graphics.raytrace;

public class Sphere
	implements SceneObject
{
	Matrix transform = Matrix.identity(4);
	public Matrix getTransform() { return transform; }
	public void setTransform(Matrix value) { transform = value; }

	private Material material = new Material();
	@Override
	public Material getMaterial() { return material; }
	public void setMaterial(Material value) { material = value; }

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
		if (!material.equals(otherSphere.material))
			return false;
		return true;
	}

	@Override
	public IntersectionList intersections(Ray ray)
	{
		ray = ray.transform(transform.inverse());
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
			transform, material);
	}
}
