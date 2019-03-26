package org.intranet.graphics.raytrace.puttingItTogether;

import org.intranet.graphics.raytrace.Canvas;

public interface Projector
{
	String getName();
	void projectToCanvas(Canvas canvas);
}