package org.intranet.graphics.raytrace.steps;

import java.util.Arrays;
import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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

	public static final String indexShapePattern = doublePattern + ":" + wordPattern;
	public static final String twoIndexShapePatterns = indexShapePattern + ", " + indexShapePattern;
	public static final String sixIndexShapePatterns = twoIndexShapePatterns + ", " + twoIndexShapePatterns + ", " + twoIndexShapePatterns;
	@Given(wordPattern + " ← intersections\\(" + sixIndexShapePatterns + "\\)")
	public void xsIntersectionsABCBCA(String intersectionListName,
		double shape1Dist, String shape1Name,
		double shape2Dist, String shape2Name,
		double shape3Dist, String shape3Name,
		double shape4Dist, String shape4Name,
		double shape5Dist, String shape5Name,
		double shape6Dist, String shape6Name)
	{
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(shape1Dist, shape1);

		Shape shape2 = data.getShape(shape2Name);
		Intersection i2 = new Intersection(shape2Dist, shape2);

		Shape shape3 = data.getShape(shape3Name);
		Intersection i3 = new Intersection(shape3Dist, shape3);

		Shape shape4 = data.getShape(shape4Name);
		Intersection i4 = new Intersection(shape4Dist, shape4);

		Shape shape5 = data.getShape(shape5Name);
		Intersection i5 = new Intersection(shape5Dist, shape5);

		Shape shape6 = data.getShape(shape6Name);
		Intersection i6 = new Intersection(shape6Dist, shape6);

		IntersectionList ilist = new IntersectionList(i1, i2, i3, i4, i5, i6);
		data.put(intersectionListName, ilist);
	}

	@Given(wordPattern + " ← intersections\\(" + twoIndexShapePatterns + "\\)")
	public void xsIntersectionsShapeShape(String intersectionListName,
		double shape1Dist, String shape1Name,
		double shape2Dist, String shape2Name)
	{
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(shape1Dist, shape1);

		Shape shape2 = data.getShape(shape2Name);
		Intersection i2 = new Intersection(shape2Dist, shape2);

		IntersectionList ilist = new IntersectionList(i1, i2);
		data.put(intersectionListName, ilist);
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
		Shape sphere = data.getShape(sphereName);
		Intersection intersection = new Intersection(distance, sphere);
		data.put(intersectionName, intersection);
	}

	@When("^" + wordPattern + " ← intersection\\(√" + doublePattern + ", " + wordPattern + "\\)$")
	public void iIntersectionSqrRt(String intersectionName, double distance,
		String shapeName)
	{
		distance = Math.sqrt(distance);
		Shape sphere = data.getShape(shapeName);
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

		List<Intersection> intersectionArray = Arrays.asList(intersection);

		prepareComputations(computationsName, rayName, intersection,
			intersectionArray);
	}

	private void prepareComputations(String computationsName, String rayName,
		Intersection intersection, List<Intersection> intersectionArray)
	{
		Ray ray = data.getRay(rayName);

		IntersectionComputations comps = new IntersectionComputations(
			intersection, ray, intersectionArray);
		data.put(computationsName, comps);
	}

	@When(wordPattern + " ← prepare_computations\\(" + wordPattern + "\\[" + intPattern + "\\], " + wordPattern + ", " + wordPattern + "\\)")
	public void compsPrepare_computationsXsRXs(String computationsName,
		String intersectionListName, int intersectionIndex, String rayName,
		String intersectionList2Name)
	{
		Assert.assertEquals(intersectionListName, intersectionList2Name);

		IntersectionList intersectionList = data.getIntersectionList(intersectionListName);
		Intersection intersection = intersectionList.get(intersectionIndex);

		List<Intersection> intersectionArray = intersectionList.getIntersections();

		prepareComputations(computationsName, rayName, intersection,
			intersectionArray);
	}

	@When(wordPattern + " ← prepare_computations\\(" + threeWordPattern + "\\)")
	public void compsPrepare_computationsIRXs(String computationsName,
		String intersectionName, String rayName, String intersectionListName)
	{
		Intersection intersection = data.getIntersection(intersectionName);

		IntersectionList intersectionList = data.getIntersectionList(intersectionListName);
		List<Intersection> intersectionArray = intersectionList.getIntersections();

		prepareComputations(computationsName, rayName, intersection,
			intersectionArray);
	}

	@Then(wordPattern + "\\[" + intPattern + "\\].object = " + wordPattern)
	public void intersectionSetObject(String intersectionListName,
		int intersectionIdx, String objectName)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection intersection = ilist.get(intersectionIdx);
		Shape t = intersection.getObject();

		Shape expectedObject = data.getShape(objectName);
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

	@Then(wordPattern + ".under_point.z > EPSILON\\/" + intPattern)
	public void compsUnder_pointZEPSILON(String compsName, Integer divisor)
	{
		IntersectionComputations comps = data.getComputations(compsName);

		Assert.assertTrue(comps.getUnderPoint().getZ() > Tuple.EPSILON / divisor);
	}

	@Then(wordPattern + ".point.z < " + wordPattern + ".under_point.z")
	public void compsPointZCompsUnder_pointZ(String compsName1, String compsName2)
	{
		IntersectionComputations comps1 = data.getComputations(compsName1);
		IntersectionComputations comps2 = data.getComputations(compsName2);

		Assert.assertTrue(comps1.getPoint().getZ() < comps2.getUnderPoint().getZ());
	}
}
