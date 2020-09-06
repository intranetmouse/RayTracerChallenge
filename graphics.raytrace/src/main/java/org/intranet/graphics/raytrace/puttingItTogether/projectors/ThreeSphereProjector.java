package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.light.PointLight;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Material;

public abstract class ThreeSphereProjector
	extends WorldProjector
{
	public ThreeSphereProjector(String name)
	{
		super(name);

		Camera camera = new Camera(0, 0, Math.PI / 3);
		camera.setTransform(Matrix.newView(new Point(0, 1.5, -5),
			new Point(0, 1, 0), new Vector(0, 1, 0)));
		world.setCamera(camera);
		world.addSceneObjects(createMiddleSphere(), createRightSphere(), createLeftSphere());

		world.addLight(createLight());
	}

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
