package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Plane;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Vector;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public final class PlaneSteps
	extends StepsParent
{
	public PlaneSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← plane\\(\\)")
	public void pPlane(String planeName)
	{
		data.put(planeName, new Plane());
	}

	@When(wordPattern + " ← local_normal_at\\(" + wordPattern + ", point\\(" +
		threeDoublesPattern + "\\)\\)")
	public void nLocal_normal_atPPoint(String normalVectorName, String shapeName,
		double x, double y, double z)
	{
		Shape shape = data.getShape(shapeName);
		Point normalLocation = new Point(x, y, z);

		Vector normalVector = shape.normalAt(normalLocation);
		data.put(normalVectorName, normalVector);
	}

	@When(wordPattern + " ← local_intersect\\(" + twoWordPattern + "\\)")
	public void xsLocal_intersectPR(String intersectionsName, String planeName,
		String rayName)
	{
		Ray ray = data.getRay(rayName);
		Shape shape = data.getShape(planeName);
		IntersectionList ilist = shape.intersections(ray);
		data.put(intersectionsName, ilist);
	}

	@Then(wordPattern + " is empty")
	public void xsIsEmpty(String intersectionListName)
	{
		IntersectionList intersections = data.getIntersectionList(
			intersectionListName);
		Assert.assertEquals(0, intersections.count());
	}

}
