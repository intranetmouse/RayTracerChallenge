package org.intranet.graphics.raytrace;

import java.util.ArrayList;
import java.util.List;

public class World
{
	List<Light> lightSources = new ArrayList<>();
	public List<Light> getLightSources()
	{ return lightSources; }
	public void addLight(Light pointLight)
	{ lightSources.add(pointLight); }

	List<Shape> sceneObjects = new ArrayList<>();
	public List<Shape> getSceneObjects()
	{
		return sceneObjects;
	}

	public void addSceneObjects(Shape ... objects)
	{
		for (Shape object : objects)
			sceneObjects.add(object);
	}

	private Camera camera;
	public Camera getCamera() { return camera; }
	public void setCamera(Camera value) { camera = value; }
}
