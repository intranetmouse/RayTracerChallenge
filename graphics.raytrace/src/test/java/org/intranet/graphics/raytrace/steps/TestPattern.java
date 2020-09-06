package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.Pattern;

public final class TestPattern
	extends Pattern
{
	@Override
	public Color colorAt(Point point)
	{
		return new Color(point.getX(), point.getY(), point.getZ());
	}
}