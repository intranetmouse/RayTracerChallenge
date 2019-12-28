package org.intranet.graphics.raytrace.app;

import java.io.File;

public final class RayTracingApp
	extends App<SceneDocument>
{
	@Override
	public String getDocType() { return "Scene"; }
	@Override
	public String getTitle() { return "Tracing Rays"; }

	@Override
	public SceneDocument openFile(File file)
	{
		SceneDocument sceneDocument = new SceneDocument(file);
		return sceneDocument;
	}
}