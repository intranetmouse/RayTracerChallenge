package org.intranet.graphics.raytrace.app;

import java.io.File;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.intranet.app.App;

public final class RayTracingApp
	extends App<SceneDocument>
{
	@Override
	public String getDocType() { return "Scene"; }
	@Override
	public char getDocTypeMnemonic()
	{ return 's'; }
	@Override
	public String getTitle() { return "Tracing Rays"; }

	@Override
	public SceneDocument openFile(File file)
	{
		return new SceneDocument(file);
	}
	@Override
	protected FileFilter getFileFilter()
	{ return new FileNameExtensionFilter("3d YML", "yml", "yaml"); }
}