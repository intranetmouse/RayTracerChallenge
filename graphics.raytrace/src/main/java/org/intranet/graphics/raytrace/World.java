package org.intranet.graphics.raytrace;

import java.util.ArrayList;
import java.util.List;

public class World
{
	List<PointLight> lightSources = new ArrayList<>();
	public List<PointLight> getLightSources()
	{ return lightSources; }

	List<SceneObject> sceneObjects = new ArrayList<>();
	public List<SceneObject> getSceneObjects()
	{
		return sceneObjects;
	}

	public static World defaultWorld()
	{
		World world = new World();
		world.lightSources
			.add(new PointLight(new Point(-10, 10, -10), new Color(1, 1, 1)));

		Sphere s1 = new Sphere();
		Material m1 = s1.getMaterial();
		m1.setColor(new Color(0.8, 1.0, 0.6));
		m1.setDiffuse(0.7);
		m1.setSpecular(0.2);
		world.sceneObjects.add(s1);

		Sphere s2 = new Sphere();
		s2.setTransform(Matrix.newScaling(0.5, 0.5, 0.5));
		world.sceneObjects.add(s2);

		return world;
	}

}
