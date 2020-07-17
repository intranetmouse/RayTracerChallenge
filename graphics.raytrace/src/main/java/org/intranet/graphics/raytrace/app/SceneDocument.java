package org.intranet.graphics.raytrace.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators.AbstractSpliterator;

import javax.swing.Action;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
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
		YamlWorldParser parser = new YamlWorldParser(new FileInputStream(file), file.getParentFile());
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
		camera.setHsize(canvas.getWidth());
		camera.setVsize(canvas.getHeight());
		camera.updatePixelSize();

		boolean parallel = renderSettings.isParallel();
		CanvasTraversalType traversalType = renderSettings.getTraversalType();
		AbstractSpliterator<PixelCoordinate> traversal =
			traversalType.getTraversal(canvas);

		Runnable renderer = () -> {
			camera.render(world, canvas, parallel, traversal, stats);
		};
		new Thread(renderer).start();
	}
}