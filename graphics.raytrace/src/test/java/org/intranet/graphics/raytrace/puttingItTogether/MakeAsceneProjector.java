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

public final class MakeAsceneProjector
	implements Projector
{
	@Override
	public String getName()
	{ return "Make a Scene"; }

	@Override
	public void projectToCanvas(Canvas canvas, boolean parallel)
	{
		World world = makeWorld();

		Camera camera = new Camera(100, 50, Math.PI / 3);
		camera.setTransform(Matrix.newView(new Point(0, 1.5, -5),
			new Point(0, 1, 0), new Vector(0, 1, 0)));

		camera.render(world, canvas, parallel);
	}

	private World makeWorld()
	{
		World world = new World();

		Material material = new Material();
		material.setColor(new Color(1, 0.9, 0.9));
		material.setSpecular(0);

		Sphere floor = createFloor(material);
		Sphere leftWall = createLeftWall(material);
		Sphere rightWall = createRightWall(material);

		world.addSceneObjects(floor, leftWall, rightWall);

		Sphere middle = createMiddleSphere();
		Sphere right = createRightSphere();
		Sphere left = createLeftSphere();

		world.addSceneObjects(middle, right, left);

		world.addLight(new PointLight(new Point(-10, 10, -10), new Color(1, 1, 1)));

		return world;
	}

	private Sphere createLeftSphere()
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

	private Sphere createRightSphere()
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

	private Sphere createMiddleSphere()
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

	private Sphere createRightWall(Material material)
	{
		Sphere rightWall = new Sphere();
		rightWall.setTransform(Matrix.newTranslation(0, 0, 5)
			.multiply(Matrix.newRotationY(Math.PI / 4))
			.multiply(Matrix.newRotationX(Math.PI / 2))
			.multiply(Matrix.newScaling(10, 0.1, 10))
			);
		rightWall.setMaterial(material);
		return rightWall;
	}

	private Sphere createLeftWall(Material material)
	{
		Sphere leftWall = new Sphere();
		leftWall.setTransform(Matrix.newTranslation(0, 0, 5)
			.multiply(Matrix.newRotationY(-Math.PI / 4))
			.multiply(Matrix.newRotationX(Math.PI / 2))
			.multiply(Matrix.newScaling(10, 0.01, 10))
			);
		leftWall.setMaterial(material);
		return leftWall;
	}

	private Sphere createFloor(Material material)
	{
		Sphere floor = new Sphere();
		floor.setTransform(Matrix.newScaling(10, 0.01, 10));
		floor.setMaterial(material);
		return floor;
	}
}