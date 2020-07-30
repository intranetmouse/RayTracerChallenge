package org.intranet.graphics.raytrace;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.Color;

public interface Light
{
	List<Point> getSamples();
	Color getIntensity();

	double intensityAt(Point pt, World world);
}
