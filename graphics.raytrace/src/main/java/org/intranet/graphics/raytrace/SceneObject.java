package org.intranet.graphics.raytrace;

public interface SceneObject
{
	Vector normalAt(Point point);
	IntersectionList intersections(Ray ray);
	Material getMaterial();
	void setTransform(Matrix mtx);
	Object getTransform();
	void setMaterial(Material m);
}
