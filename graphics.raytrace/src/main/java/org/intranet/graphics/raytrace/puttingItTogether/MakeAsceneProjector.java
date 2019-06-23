package org.intranet.graphics.raytrace.puttingItTogether;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;

public final class MakeAsceneProjector
	extends ThreeSphereProjector
{
	public MakeAsceneProjector()
	{ super("Make a Scene"); }

	@Override
	protected void fillWorld()
	{
		Material material = new Material();
		material.setColor(new Color(1, 0.9, 0.9));
		material.setSpecular(0);

		Sphere floor = createFloor(material);
		Sphere leftWall = createLeftWall(material);
		Sphere rightWall = createRightWall(material);

		world.addSceneObjects(floor, leftWall, rightWall);
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