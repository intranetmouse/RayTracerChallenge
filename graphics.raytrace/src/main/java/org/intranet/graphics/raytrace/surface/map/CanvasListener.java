package org.intranet.graphics.raytrace.surface.map;

import org.intranet.graphics.raytrace.primitive.Color;

public interface CanvasListener
{
	void resized(int x, int y);
	void initialized();
	void pixelUpdated(int x, int y, Color color);
	void completed();
}