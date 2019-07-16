package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.Transformable;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;

public abstract class Pattern
	implements Transformable
{
	public Pattern()
	{
	}

	public abstract Color colorAt(Point point);

	private Matrix transform = Matrix.identity(4);
	@Override
	public void setTransform(Matrix mtx) { transform = mtx; }
	@Override
	public Matrix getTransform() { return transform; }
}
