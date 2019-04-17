package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Tuple;
import org.junit.Assert;

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
		Shape sphere = data.getSceneObject(sphereName);
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

	@When(wordPattern + " ← prepare_computations\\(" + wordPattern + ", " +
		wordPattern + "\\)")
	public void compsPrepare_computationsIR(String computationsName,
		String intersectionName, String rayName)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		Ray ray = data.getRay(rayName);

		IntersectionComputations comps = new IntersectionComputations(
			intersection, ray);
		data.put(computationsName, comps);
	}


	@Then(wordPattern + ".object = " + wordPattern)
	public void iObjectS(String intersectionName, String sphereName)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		Shape sphere = data.getSceneObject(sphereName);
		Assert.assertEquals(sphere, intersection.getObject());
	}

	@Then(wordPattern + ".inside = " + wordPattern)
	public void objPropertyEqualsBoolean(String actualObjName,
		String expectedBoolean)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);

		boolean isFalseExpected = !"false".equalsIgnoreCase(expectedBoolean);

		Assert.assertEquals(isFalseExpected, actualComps.isInside());
	}

	@Then(wordPattern + "\\[" + intPattern + "\\].object = " + wordPattern)
	public void intersectionSetObject(String intersectionListName,
		int intersectionIdx, String objectName)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection intersection = ilist.get(intersectionIdx);
		Shape t = intersection.getObject();

		Shape expectedObject = data.getSceneObject(objectName);
		Assert.assertEquals(t, expectedObject);
	}

	@Then(wordPattern + "\\[" + intPattern + "\\].t = " + doublePattern)
	public void xsT(String intersectionListName, int intersectionIdx,
		double expectedValue)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection intersection = ilist.get(intersectionIdx);
		double t = intersection.getDistance();
		Assert.assertEquals(expectedValue, t, Tuple.EPSILON);
	}

	@Given(wordPattern + " ← intersections\\(" + fourWordPattern + "\\)")
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
