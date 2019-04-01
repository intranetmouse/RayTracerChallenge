package org.intranet.graphics.raytrace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
		this.intersections = intersections;
		intersections.sort((o1, o2) -> compareDouble(o1.getDistance() - o2.getDistance()));
	}
	public IntersectionList()
	{
		intersections = Collections.emptyList();
	}

	public static final int compareDouble(double x)
	{
		return x > 0 ? 1 : x < 0 ? -1 : 0;
	}

	public Intersection hit()
	{
		Intersection intersection = null;
		for (Intersection i : intersections)
		{
			double distance = i.getDistance();
			if (intersection != null && (distance > 0 && distance < intersection.getDistance()))
				intersection = i;
		}
		return intersection;
	}
}
