package org.intranet.graphics.raytrace;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;

public interface Light
{
	List<Point> getSamples();
	Color getIntensity();

	double intensityAt(Point pt, World world);
}
