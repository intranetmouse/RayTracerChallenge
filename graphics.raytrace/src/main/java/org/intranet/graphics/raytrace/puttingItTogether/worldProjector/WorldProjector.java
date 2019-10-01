package org.intranet.graphics.raytrace.puttingItTogether.worldProjector;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.puttingItTogether.projector.Projector;
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
		camera.setHsize(canvas.getWidth());
		camera.setVsize(canvas.getHeight());
		camera.updatePixelSize();
		camera.render(world, canvas, parallel, traversalType.getTraversal(canvas));
	}

	private String name;
	@Override
	public final String getName() { return name; }

	protected abstract void fillWorld();
}