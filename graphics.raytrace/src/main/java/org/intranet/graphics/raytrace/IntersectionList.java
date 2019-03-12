package org.intranet.graphics.raytrace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IntersectionList
{
	public static IntersectionList emptyList = new IntersectionList();

	private List<Intersection> intersections;
	public int count() { return intersections.size(); }
	public Intersection get(int idx) { return intersections.get(idx); }

	public IntersectionList(Intersection ... i)
	{
		intersections = Arrays.asList(i);
	}
	public IntersectionList()
	{
		intersections = Collections.emptyList();
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
