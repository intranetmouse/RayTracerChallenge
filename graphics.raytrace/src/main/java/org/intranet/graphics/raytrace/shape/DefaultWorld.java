package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;

public final class DefaultWorld
{
	public static World defaultWorld()
	{
		World world = new World();
		world.getLightSources()
			.add(new PointLight(new Point(-10, 10, -10), new Color(1, 1, 1)));

		Sphere s1 = new Sphere();
		Material m1 = s1.getMaterial();
		m1.setColor(new Color(0.8, 1.0, 0.6));
		m1.setDiffuse(0.7);
		m1.setSpecular(0.2);
		world.addSceneObjects(s1);

		Sphere s2 = new Sphere();
		s2.setTransform(Matrix.newScaling(0.5, 0.5, 0.5));
		world.addSceneObjects(s2);

		return world;
	}
}