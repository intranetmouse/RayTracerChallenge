package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;

public abstract class Pattern
{
	public Pattern()
	{
	}

	public abstract Color patternAt(Point point);

	private Matrix transform = Matrix.identity(4);
	public void setTransform(Matrix mtx) { transform = mtx; }
	public Matrix getTransform() { return transform; }
}
