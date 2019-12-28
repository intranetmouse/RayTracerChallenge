package org.intranet.graphics.raytrace.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.persistence.YamlWorldParser;

public final class SceneDocument
	implements Document
{
	private World world;
	public World getWorld() { return world; }

	private List<Action> docActions = new ArrayList<>();
	private final YamlWorldParser parser = new YamlWorldParser();

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
		world = parser.parse(new FileInputStream(this.file));
	}

	@Override
	public List<Action> getDocumentActions()
	{
		return docActions;
	}

	void render(RenderSettings renderSettings, Canvas canvas)
	{
		canvas.clear();
		if (world == null)
			return;

		Camera camera = world.getCamera();
		camera.setHsize(canvas.getWidth());
		camera.setVsize(canvas.getHeight());
		camera.updatePixelSize();


		Runnable renderer = () -> {
			camera.render(world, canvas, renderSettings.isParallel(),
				renderSettings.getTraversalType().getTraversal(canvas));
		};
		new Thread(renderer).start();
	}
}