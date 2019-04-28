package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Point;

public interface Light
{
	Point getPosition();
	Color getIntensity();
}
