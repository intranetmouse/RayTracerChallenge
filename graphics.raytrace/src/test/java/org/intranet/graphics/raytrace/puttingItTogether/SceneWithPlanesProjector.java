package org.intranet.graphics.raytrace.puttingItTogether;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;

public final class SceneWithPlanesProjector
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

		double wallAmbient = 0.3;

		Material wallMaterial = makeMaterial(1, 0.9, 0.9, 0.3, wallAmbient);

		Material redMaterial = makeMaterial(1, 0.5, 0.5, 0, wallAmbient);

		Material greenMaterial = makeMaterial(0.5, 1, 0.5, 0, wallAmbient);

		Material blueMaterial = makeMaterial(0.5, 0.5, 1, 0, wallAmbient);

		Shape floor = createFloor(blueMaterial);
		Shape leftWall = createLeftWall(redMaterial);
		Shape rightWall = createRightWall(greenMaterial);

		world.addSceneObjects(floor, leftWall, rightWall);

		Sphere middle = createMiddleSphere();
		Sphere right = createRightSphere();
		Sphere left = createLeftSphere();

		world.addSceneObjects(middle, right, left);

		world.addLight(new PointLight(new Point(-8, 7, -10), new Color(1, 1, 1)));

		return world;
	}

	private static Material makeMaterial(double red, double green, double blue,
		double specular, double ambient)
	{
		Material material = new Material();
		material.setColor(new Color(red, green, blue));
		material.setSpecular(specular);
		material.setAmbient(ambient);
		return material;
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

	private Shape createRightWall(Material material)
	{
		Shape rightWall = new Plane();
		rightWall.setTransform(Matrix.newTranslation(0, 0, 5)
			.multiply(Matrix.newRotationY(Math.PI + Math.PI / 4))
			.multiply(Matrix.newRotationX(Math.PI / 2))
			);
		rightWall.setMaterial(material);
		return rightWall;
	}

	private Shape createLeftWall(Material material)
	{
		Shape leftWall = new Plane();
		leftWall.setTransform(Matrix.newTranslation(0, 0, 5)
			.multiply(Matrix.newRotationY(Math.PI + -Math.PI / 4))
			.multiply(Matrix.newRotationX(Math.PI / 2))
			);
		leftWall.setMaterial(material);
		return leftWall;
	}

	private Shape createFloor(Material material)
	{
		Shape floor = new Plane();
		floor.setMaterial(material);
		return floor;
	}
}