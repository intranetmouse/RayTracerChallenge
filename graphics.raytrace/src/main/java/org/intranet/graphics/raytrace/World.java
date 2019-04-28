package org.intranet.graphics.raytrace;

import java.util.ArrayList;
import java.util.List;

public class World
{
	List<PointLight> lightSources = new ArrayList<>();
	public List<PointLight> getLightSources()
	{ return lightSources; }
	public void addLight(PointLight pointLight)
	{ lightSources.add(pointLight); }

	List<Shape> sceneObjects = new ArrayList<>();
	public List<Shape> getSceneObjects()
	{
		return sceneObjects;
	}

	public IntersectionList intersect(Ray ray)
	{
		List<Intersection> intersections = new ArrayList<>();
		for (Shape sceneObject : sceneObjects)
		{
			IntersectionList il = sceneObject.intersections(ray);
			intersections.addAll(il.getIntersections());
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
}
