package org.intranet.graphics.raytrace;

public abstract class Shape
{
	final public Vector normalAt(Point point)
	{
		Matrix inverse = transform.inverse();

		Point localPoint = inverse.multiply(point);

		Vector shapeNormalVector = localNormalAt(localPoint, inverse);

		Vector worldNormal = inverse.transpose().multiply(shapeNormalVector);

		Vector v = new Vector(worldNormal.getX(), worldNormal.getY(),
			worldNormal.getZ());
		return v.normalize();
	}

	public final IntersectionList intersections(Ray ray)
	{
		Ray localRay = ray.transform(transform.inverse());
		savedRay = localRay;
		return localIntersections(localRay);
	}

	private Ray savedRay;
	public Ray getSavedRay() { return savedRay; }

	public abstract IntersectionList localIntersections(Ray ray);
	protected abstract Vector localNormalAt(Point point, Matrix inverse);

	private Material material = new Material();
	public final Material getMaterial() { return material; }
	public final void setMaterial(Material value) { material = value; }

	Matrix transform = Matrix.identity(4);
	public final Matrix getTransform() { return transform; }
	public final void setTransform(Matrix value) { transform = value; }
}
