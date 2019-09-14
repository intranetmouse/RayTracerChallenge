package org.intranet.graphics.raytrace.primitive;

public interface Transformable
{
	Matrix getTransform();
	void setTransform(Matrix mtx);
}