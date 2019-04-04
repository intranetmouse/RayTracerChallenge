package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.SceneObject;
import org.intranet.graphics.raytrace.Tuple;
import org.intranet.graphics.raytrace.Vector;
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
		SceneObject sphere = data.getSceneObject(sphereName);
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
		SceneObject sphere = data.getSceneObject(sphereName);
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
		SceneObject t = intersection.getObject();

		SceneObject expectedObject = data.getSceneObject(objectName);
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

	@Then(wordPattern + " is nothing")
	public void iIsNothing(String intersectionName)
	{
		Intersection i = data.getIntersection(intersectionName);
		Assert.assertNull(i);
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


	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern + "\\("
		+ threeDoublesPattern + "\\)")
	public void objPropEqualsTuple(String expectedObjName, String propertyName,
		String objType, double x, double y, double z)
	{
		Tuple expected = "point".equals(objType) ? new Point(x, y, z) :
			"vector".equals(objType) ? new Vector(x, y, z) :
			null;
		Assert.assertNotNull("Unrecognized object type " + objType, expected);

		IntersectionComputations comps = data.getComputations(expectedObjName);
		Object value = "point".equals(propertyName) ? comps.getPoint() :
			"eyev".equals(propertyName) ? comps.getEyeVector() :
			"normalv".equals(propertyName) ? comps.getNormalVector() :
			null;
		Assert.assertNotNull("Property name does not match: " + propertyName,
			value);

		Assert.assertEquals(expected, value);
	}

	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern + "\\." + wordPattern)
	public void compsTIT(String actualObjName, String actualObjVariableName,
		String expectedObjName, String expectedObjVariableName)
	{
//System.out.printf("obj=%s, var=%s, expected obj=%s, var=%s\n", actualObjName, actualObjVariableName, expectedObjName, expectedObjVariableName);
		Assert.assertEquals(expectedObjVariableName, actualObjVariableName);

		IntersectionComputations actualComps = data.getComputations(actualObjName);
		if (actualComps != null)
		{
			IntersectionComputations expectedComps = data.getComputations(expectedObjName);

			if (expectedComps != null)
			{
				switch (actualObjVariableName)
				{
					case "t":
						double expectedDistance = expectedComps.getDistance();
						double actualDistance = actualComps.getDistance();
						Assert.assertEquals(expectedDistance,
							actualDistance, Tuple.EPSILON);
						return;
					default:
						throw new cucumber.api.PendingException(
							"Unknown variable name " + actualObjVariableName +
							" on obj name=" + actualObjName);
				}
			}

			Intersection expectedIntersection = data.getIntersection(expectedObjName);
			if (expectedIntersection != null)
			{
				switch (actualObjVariableName)
				{
					case "t":
						double expectedDistance = expectedIntersection.getDistance();
						double actualDistance = actualComps.getDistance();
						Assert.assertEquals(expectedDistance,
							actualDistance, Tuple.EPSILON);
						return;
					case "object":
						SceneObject expectedObject = expectedIntersection.getObject();
						SceneObject actualObject = actualComps.getObject();
						Assert.assertEquals(expectedObject, actualObject);
						return;
					default:
						throw new cucumber.api.PendingException(
							"Unknown variable name " + actualObjVariableName +
							" on obj name=" + actualObjName);
				}
			}
		}
		throw new cucumber.api.PendingException(
			"Unknown data type for variable " + actualObjName);
	}

}
