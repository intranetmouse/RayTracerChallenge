package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Transformable;

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
