package org.intranet.graphics.raytrace.puttingItTogether.worldProjector;

import java.util.Spliterators.AbstractSpliterator;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.CameraViewPort;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.puttingItTogether.projector.Projector;
import org.intranet.graphics.raytrace.surface.map.Canvas;
import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;

public abstract class WorldProjector
	implements Projector
{
	protected World world = new World();

	public WorldProjector(String name)
	{
		this.name = name;
	}

	private CanvasTraversalType traversalType = CanvasTraversalType.AcrossDown;
	public CanvasTraversalType getTraversalType() { return traversalType; }
	public void setTraversalType(CanvasTraversalType value) { traversalType = value; }

	@Override
	public final void projectToCanvas(Canvas canvas, boolean parallel)
	{
		fillWorld();

		Camera camera = world.getCamera(); // new Camera(canvas.getWidth(), canvas.getHeight(), Math.PI / 3);
		if (camera == null)
			throw new RuntimeException(
				"Projector " + name + " failed to make a camera.");
		CameraViewPort viewPort = camera.getViewPort();
		viewPort.setHsize(canvas.getWidth());
		camera.getViewPort().setVsize(canvas.getHeight());
		camera.getViewPort().updatePixelSize();
		RayTraceStatistics stats = new RayTraceStatistics();
		AbstractSpliterator<PixelCoordinate> traversal =
			traversalType.getTraversal(canvas);
		Tracer.render(camera, camera.getViewPort(), world, canvas, parallel,
			traversal, stats);
	}

	private String name;
	@Override
	public final String getName() { return name; }

	protected abstract void fillWorld();
}