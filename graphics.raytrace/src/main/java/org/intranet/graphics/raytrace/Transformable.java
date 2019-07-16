package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Matrix;

public interface Transformable
{
	Matrix getTransform();
	void setTransform(Matrix mtx);
}