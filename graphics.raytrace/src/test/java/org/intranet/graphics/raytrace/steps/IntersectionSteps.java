package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IntersectionSteps
	extends StepsParent
{
	public IntersectionSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier} ← intersection\\({dbl}, {identifier})")
	public void iIntersectionS(String intersectionName, double distance,
		String sphereName)
	{
		Shape sphere = data.getShape(sphereName);
		Intersection intersection = new Intersection(distance, sphere);
		data.put(intersectionName, intersection);
	}

	@When("{identifier} ← intersection\\(√{dbl}, {identifier})")
	public void iIntersectionSqrRt(String intersectionName, double distance,
		String shapeName)
	{
		distance = Math.sqrt(distance);
		Shape sphere = data.getShape(shapeName);
		Intersection intersection = new Intersection(distance, sphere);
		data.put(intersectionName, intersection);
	}

	@When("{identifier} ← intersection_with_uv\\({dbl}, {identifier}, {dbl}, {dbl})")
	public void iIntersection_with_uvS(String intersectionName,
		double distance, String shapeName, double u, double v)
	{
		Shape s = data.getShape(shapeName);
		Intersection intersection = new Intersection(distance, s, u, v);
		data.put(intersectionName, intersection);
	}

	@Then("{identifier}.u = {dbl}")
	public void iU(String intersectionName, Double double1)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		Assert.assertEquals(double1, intersection.getU(), Tuple.EPSILON);
	}

	@Then("{identifier}.v = {dbl}")
	public void iV(String intersectionName, Double double1)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		Assert.assertEquals(double1, intersection.getV(), Tuple.EPSILON);
	}
}