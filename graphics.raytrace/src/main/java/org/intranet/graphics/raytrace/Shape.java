package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;

public abstract class Shape
{
	final public Vector normalAt(Point worldPoint)
	{
		Matrix inverse = transform.inverse();

		Point localPoint = inverse.multiply(worldPoint);

		Vector localNormalVector = localNormalAt(localPoint, inverse);

		Vector worldNormal = inverse.transpose().multiply(localNormalVector);

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

	private Matrix transform = Matrix.identity(4);
	public final Matrix getTransform() { return transform; }
	public final void setTransform(Matrix value) { transform = value; }

	@Override
	public final boolean equals(Object other)
	{
		Shape otherSphere = (Shape)other;
		if (!transform.equals(otherSphere.transform))
			return false;
		if (!getMaterial().equals(otherSphere.getMaterial()))
			return false;
		return true;
	}
	protected abstract boolean shapeEquals(Object other);
}
