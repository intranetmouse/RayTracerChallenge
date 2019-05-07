package org.intranet.graphics.raytrace.puttingItTogether.projector;

import org.intranet.graphics.raytrace.Canvas;

public interface Projector
{
	String getName();
	void projectToCanvas(Canvas canvas, boolean parallel);
}