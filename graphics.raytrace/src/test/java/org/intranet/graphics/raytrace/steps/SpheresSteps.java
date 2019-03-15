package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Sphere;
import org.intranet.graphics.raytrace.Tuple;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SpheresSteps
	extends StepsParent
{
	public SpheresSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← sphere\\(\\)")
	public void sSphere(String sphereName)
	{
		data.put(sphereName, new Sphere());
	}


	@When(wordPattern + " ← intersect\\(" + twoWordPattern + "\\)")
	public void xsIntersectSR(String intersectionName, String sphereName, String rayName)
	{
		Sphere sphere = data.getSphere(sphereName);
		Ray ray = data.getRay(rayName);
		IntersectionList intersections = sphere.intersections(ray);
		data.put(intersectionName, intersections);
	}

	@Then(wordPattern + "\\[" + intPattern + "\\] = " + doublePattern)
	public void xs(String intersectionName, int index, double expectedValue)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionName);
		Intersection intersection = ilist.get(index);
		double value = intersection.getDistance();
		Assert.assertEquals(expectedValue, value, Tuple.EPSILON);
	}

}
