package org.intranet.graphics.raytrace;

public interface Shape
{
	Vector normalAt(Point point);
	IntersectionList intersections(Ray ray);

	Material getMaterial();
	void setMaterial(Material m);

	void setTransform(Matrix mtx);
	Object getTransform();
}
