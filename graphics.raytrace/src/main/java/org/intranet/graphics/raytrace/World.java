package org.intranet.graphics.raytrace;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.primitive.Ray;

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

	public IntersectionList intersect(Ray ray, boolean omitShadowless)
	{
		List<Intersection> intersections = new ArrayList<>();
		for (Shape sceneObject : sceneObjects)
		{
			if (!omitShadowless || sceneObject.isCastShadow())
			{
				IntersectionList il = sceneObject.intersections(ray);
				intersections.addAll(il.getIntersections());
			}
		}
		intersections.sort((o1, o2) -> IntersectionList
			.compareDouble(o1.getDistance() - o2.getDistance()));
		return new IntersectionList(intersections);
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
