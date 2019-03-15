package org.intranet.graphics.raytrace;

import org.junit.Assert;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class IntersectionsSteps
	extends StepsParent
{
	public IntersectionsSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← intersections\\(" + wordPattern + "\\)")
	public void xsIntersectionsI(String intersectionsName,
		String intersectionName)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		IntersectionList ilist = new IntersectionList(intersection);
		data.put(intersectionsName, ilist);
	}

	@When(wordPattern + " ← intersections\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void xsIntersectionsII(String intersectionsName,
		String intersection1Name, String intersection2Name)
	{
		Intersection intersection1 = data.getIntersection(intersection1Name);
		Intersection intersection2 = data.getIntersection(intersection2Name);
		IntersectionList ilist = new IntersectionList(intersection1, intersection2);
		data.put(intersectionsName, ilist);
	}

	@When("^" + wordPattern + " ← intersection\\(" + doublePattern + ", " + wordPattern + "\\)$")
	public void iIntersectionS(String intersectionName, double distance,
		String sphereName)
	{
		Sphere sphere = data.getSphere(sphereName);
		Intersection intersection = new Intersection(distance, sphere);
		data.put(intersectionName, intersection);
	}

	@When(wordPattern + " ← hit\\(" + wordPattern + "\\)")
	public void iHitXs(String intersectionName, String intersectionListName)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection hit = ilist.hit();
		data.put(intersectionName, hit);
	}


	@Then(wordPattern + ".count = " + intPattern)
	public void xsCount(String intersectionName, int numIntersections)
	{
		IntersectionList intersectionList = data.getIntersectionList(intersectionName);
		if (intersectionList != null)
		{
			Assert.assertEquals(numIntersections, intersectionList.count());
			return;
		}

		throw new PendingException();
	}

	@Then(wordPattern + ".t = " + doublePattern)
	public void iT(String intersectionName, double value)
	{
		Intersection intersection = data.getIntersection(intersectionName);

		Assert.assertEquals(value, intersection.getDistance(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".object = " + wordPattern)
	public void iObjectS(String intersectionName, String sphereName)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		Sphere sphere = data.getSphere(sphereName);
		Assert.assertEquals(sphere, intersection.getObject());
	}

	@Then(wordPattern + "\\[" + intPattern + "\\].t = " + doublePattern)
	public void xsT(String intersectionListName, int intersectionIdx,
		double expectedValue)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection intersection = ilist.get(intersectionIdx);
		double t = intersection.getDistance();
		Assert.assertEquals(t, expectedValue, Tuple.EPSILON);
	}

	@Then(wordPattern + " is nothing")
	public void iIsNothing(String intersectionName)
	{
		Intersection i = data.getIntersection(intersectionName);
		Assert.assertNull(i);
	}

	@Given(wordPattern + " ← intersections\\(" + wordPattern + ", " +
			wordPattern + ", " + wordPattern + ", " + wordPattern + "\\)")
	public void xsIntersectionsIIII(String intersectionListName, String int1,
		String int2, String int3, String int4)
	{
		Intersection i1 = data.getIntersection(int1);
		Intersection i2 = data.getIntersection(int2);
		Intersection i3 = data.getIntersection(int3);
		Intersection i4 = data.getIntersection(int4);

		IntersectionList ilist = new IntersectionList(i1, i2, i3, i4);
		data.put(intersectionListName, ilist);
	}

}
