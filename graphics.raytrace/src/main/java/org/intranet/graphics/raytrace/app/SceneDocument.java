package org.intranet.graphics.raytrace.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators.AbstractSpliterator;

import javax.swing.Action;

import org.intranet.app.Document;
import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.CameraViewPort;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.surface.map.Canvas;
import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;

public final class SceneDocument
	implements Document
{
	private World world;
	public World getWorld() { return world; }

	private List<Action> docActions = new ArrayList<>();

	private File file;

	public SceneDocument(File file)
	{
		this.file = file;
		try
		{
			loadWorld();
		}
		catch (FileNotFoundException e)
		{
			// FIXME: Listener?
			e.printStackTrace();
			file = null;
		}
	}

	private void loadWorld()
		throws FileNotFoundException
	{
		FileInputStream ymlStream = new FileInputStream(file);
		File relativePath = file.getParentFile();
		YamlWorldParser parser = new YamlWorldParser(ymlStream, relativePath);
		world = parser.getWorld();
	}

	@Override
	public List<Action> getDocumentActions()
	{
		return docActions;
	}


	void render(RenderSettings renderSettings, Canvas canvas,
		RayTraceStatistics stats)
	{
		canvas.clear();
		if (world == null)
			return;

		Camera camera = world.getCamera();
		CameraViewPort viewPort = camera.getViewPort();
		viewPort.setHsize(canvas.getWidth());
		camera.getViewPort().setVsize(canvas.getHeight());
		camera.getViewPort().updatePixelSize();

		boolean parallel = renderSettings.isParallel();
		CanvasTraversalType traversalType = renderSettings.getTraversalType();
		AbstractSpliterator<PixelCoordinate> traversal =
			traversalType.getTraversal(canvas);

		Runnable renderer = () -> {
			Tracer.render(camera, viewPort, world, canvas, parallel, traversal,
				stats);
		};
		new Thread(renderer).start();
	}
}