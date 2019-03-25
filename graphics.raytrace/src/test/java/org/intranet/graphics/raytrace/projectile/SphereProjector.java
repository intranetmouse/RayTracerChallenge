package org.intranet.graphics.raytrace.projectile;

import org.intranet.graphics.raytrace.Canvas;

public interface SphereProjector
{
	void projectToCanvas(SphereProjectionType projType, Canvas canvas);
}