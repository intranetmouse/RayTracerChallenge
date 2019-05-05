package org.intranet.graphics.raytrace.puttingItTogether;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;

public final class SceneWithPlanesProjector
	extends WorldProjector
{
	public SceneWithPlanesProjector()
	{ super("Plane Walls Scene"); }


	@Override
	protected World makeWorld()
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

		world.addSceneObjects(createMiddleSphere(), createRightSphere(), createLeftSphere());

		world.addLight(createLight());

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