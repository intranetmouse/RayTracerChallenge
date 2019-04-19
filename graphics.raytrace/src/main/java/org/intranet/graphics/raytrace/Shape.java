package org.intranet.graphics.raytrace;

public abstract class Shape
{
	public abstract Vector normalAt(Point point);
	public final IntersectionList intersections(Ray ray)
	{
		Ray localRay = ray.transform(transform.inverse());
		return localIntersections(localRay);
	}

	public abstract IntersectionList localIntersections(Ray ray);

	private Material material = new Material();
	public final Material getMaterial() { return material; }
	public final void setMaterial(Material value) { material = value; }

	Matrix transform = Matrix.identity(4);
	public final Matrix getTransform() { return transform; }
	public final void setTransform(Matrix value) { transform = value; }
}
