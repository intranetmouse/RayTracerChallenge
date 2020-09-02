package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Pair;
import org.intranet.graphics.raytrace.primitive.Point;

public interface UvMap
{
	Pair<Double> map(Point p);
}
