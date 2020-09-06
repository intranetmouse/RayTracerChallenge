package org.intranet.graphics.raytrace.surface.map;

import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;

public interface UvMap
{
	DoublePair map(Point p);
}
