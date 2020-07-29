package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.Color;

public interface Light
{
	Point getPosition();
	Color getIntensity();

	double intensityAt(Point pt, World world);
}
