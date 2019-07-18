package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Point;

public abstract class Pattern
{
	public Pattern()
	{
	}

	public abstract Color colorAt(Point p);
}
