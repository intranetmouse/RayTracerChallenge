package org.intranet.graphics.raytrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.intranet.graphics.raytrace.primitive.Ray;

public final class IntersectionList
{
	public static IntersectionList emptyList = new IntersectionList();

	private List<Intersection> intersections;
	public int count() { return intersections.size(); }
	public Intersection get(int idx) { return intersections.get(idx); }
	public List<Intersection> getIntersections() { return intersections; }

	public IntersectionList(Intersection ... i)
	{
		this(Arrays.asList(i));
	}
	public IntersectionList(List<Intersection> intersections)
	{
		intersections.size();
		this.intersections = intersections;
		intersections.sort((o1, o2) -> compareDouble(o1.getDistance() - o2.getDistance()));
	}
	public IntersectionList()
	{
		intersections = Collections.emptyList();
	}

	public IntersectionList(List<Shape> sceneObjects, Ray ray, boolean omitShadowless)
	{
		intersections = new ArrayList<>();
		for (Shape sceneObject : sceneObjects)
		{
			if (!omitShadowless || sceneObject.isCastShadow())
			{
				IntersectionList il = sceneObject.intersections(ray);
				intersections.addAll(il.getIntersections());
			}
		}
		intersections.sort((o1, o2) -> compareDouble(o1.getDistance() - o2.getDistance()));
	}

	public IntersectionList combineWith(IntersectionList other)
	{
		List<Intersection> all = new ArrayList<>(intersections);
		all.addAll(other.getIntersections());
		return new IntersectionList(all);
	}

	public static final int compareDouble(double x)
	{
		return x > 0 ? 1 : x < 0 ? -1 : 0;
	}

	public Intersection hit()
	{
		return intersections.stream()
			.filter(i -> i.getDistance() > 0)
			.min(Comparator.comparing(Intersection::getDistance))
			.orElse(null);
	}
}
