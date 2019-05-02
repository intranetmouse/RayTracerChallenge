package org.intranet.graphics.raytrace.puttingItTogether;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;

public abstract class WorldProjector
	implements Projector
{
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
		World world = makeWorld();

		Camera camera = new Camera(canvas.getWidth(), canvas.getHeight(), Math.PI / 3);
		camera.setTransform(Matrix.newView(new Point(0, 1.5, -5),
			new Point(0, 1, 0), new Vector(0, 1, 0)));

		camera.render(world, canvas, parallel, traversalType.getTraversal(canvas));
	}

	private String name;
	@Override
	public final String getName() { return name; }

	protected abstract World makeWorld();


	protected PointLight createLight()
	{
		return new PointLight(new Point(-8, 7, -10), new Color(1, 1, 1));
	}


	protected Sphere createLeftSphere()
	{
		Sphere left = new Sphere();
		left.setTransform(Matrix.newTranslation(-1.5, 0.33, -0.75)
			.multiply(Matrix.newScaling(0.33, 0.33, 0.33))
			);
		Material leftMaterial = new Material();
		leftMaterial.setColor(new Color(1, 0.8, 0.1));
		leftMaterial.setDiffuse(0.7);
		leftMaterial.setAmbient(0.3);
		left.setMaterial(leftMaterial);
		return left;
	}


	protected Sphere createRightSphere()
	{
		Sphere right = new Sphere();
		right.setTransform(Matrix.newTranslation(1.5, 0.5, -0.5)
			.multiply(Matrix.newScaling(0.5, 0.5, 0.5))
			);
		Material rightMaterial = new Material();
		rightMaterial.setColor(new Color(0.5, 1, 0.1));
		rightMaterial.setDiffuse(0.7);
		rightMaterial.setAmbient(0.3);
		right.setMaterial(rightMaterial);
		return right;
	}


	protected Sphere createMiddleSphere()
	{
		Sphere middle = new Sphere();
		middle.setTransform(Matrix.newTranslation(-0.5, 1, 0.5));
		Material middleMaterial = new Material();
		middleMaterial.setColor(new Color(0.1, 1, 0.5));
		middleMaterial.setDiffuse(0.7);
		middleMaterial.setAmbient(0.3);
		middle.setMaterial(middleMaterial);
		return middle;
	}
}