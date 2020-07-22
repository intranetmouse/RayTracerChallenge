package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;

public interface ShapeParent
{
	Point worldToObject(Point point);
	Vector normalToWorld(Vector normal);
	BoundingBox getBoundingBox();
}