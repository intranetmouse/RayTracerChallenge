package org.intranet.graphics.raytrace.surface.pattern2d;

import org.intranet.graphics.raytrace.primitive.Color;

public interface UvPattern
{
	Color colorAt(double u, double v);
}
