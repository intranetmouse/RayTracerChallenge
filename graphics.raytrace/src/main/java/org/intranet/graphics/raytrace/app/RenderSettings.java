package org.intranet.graphics.raytrace.app;

import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;
import org.intranet.graphics.raytrace.ui.swing.resolution.Resolution;

public final class RenderSettings
{
	private final RenderSettingsListener listener;
	public RenderSettings(RenderSettingsListener l)
	{
		listener = l;
	}

	private Resolution res = Resolution.HDTV_360p;
	public Resolution getResolution() { return res; }
	public void setResolution(Resolution e)
	{
		res = e;
		listener.resolutionChanged(res);
	}

	private boolean parallel = true;
	public boolean isParallel() { return parallel; }
	public void setParallel(boolean value) { parallel = value; }

	private CanvasTraversalType traversalType = CanvasTraversalType.QuandrantsRandom;
	public CanvasTraversalType getTraversalType() { return traversalType; }
	public void setTraversalType(CanvasTraversalType value) { traversalType = value; }
}