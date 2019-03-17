package org.intranet.graphics.raytrace;

public class Sphere
{
	Matrix transform = Matrix.identity(4);
	public Matrix getTransform() { return transform; }
	public void setTransform(Matrix value) { transform = value; }

	public Sphere()
	{
		
	}

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
}
