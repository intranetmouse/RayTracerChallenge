package org.intranet.graphics.raytrace.steps;

import java.util.Arrays;
import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Ray;
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

	@Given("{identifier} ← intersections\\({identifier})")
	public void xsIntersectionsI(String intersectionsName,
		String intersectionName)
	{
		Intersection intersection = data.getIntersection(intersectionName);
		IntersectionList ilist = new IntersectionList(intersection);
		data.put(intersectionsName, ilist);
	}

	@Given("{identifier} ← intersections\\({identifier}, {identifier}, {identifier}, {identifier})")
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
	public static final String fourIndexShapePatterns = twoIndexShapePatterns + ", " + twoIndexShapePatterns;
	public static final String sixIndexShapePatterns = twoIndexShapePatterns + ", " + twoIndexShapePatterns + ", " + twoIndexShapePatterns;

	@Given("{identifier} ← intersections\\({dbl}:{identifier}, {dbl}:{identifier}, {dbl}:{identifier}, {dbl}:{identifier}, {dbl}:{identifier}, {dbl}:{identifier})")
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

	// Kept as regex because / is not escapable in Cucumber expressions
	// https://cucumber.io/docs/cucumber/cucumber-expressions/
	@Given("^" + wordPattern + " ← intersections\\(" +
		indexShapePattern + "\\)$")
	public void xsIntersectionsABCB(String intersectionListName,
		double shape1Dist, String shape1Name)
	{
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(shape1Dist, shape1);

		IntersectionList ilist = new IntersectionList(i1);
		data.put(intersectionListName, ilist);
	}

	// Kept as regex because / is not escapable in Cucumber expressions
	// https://cucumber.io/docs/cucumber/cucumber-expressions/
	@Given("^" + wordPattern + " ← intersections\\(" +
		fourIndexShapePatterns + "\\)$")
	public void xsIntersectionsABCB(String intersectionListName,
		double shape1Dist, String shape1Name,
		double shape2Dist, String shape2Name,
		double shape3Dist, String shape3Name,
		double shape4Dist, String shape4Name)
	{
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(shape1Dist, shape1);

		Shape shape2 = data.getShape(shape2Name);
		Intersection i2 = new Intersection(shape2Dist, shape2);

		Shape shape3 = data.getShape(shape3Name);
		Intersection i3 = new Intersection(shape3Dist, shape3);

		Shape shape4 = data.getShape(shape4Name);
		Intersection i4 = new Intersection(shape4Dist, shape4);

		IntersectionList ilist = new IntersectionList(i1, i2, i3, i4);
		data.put(intersectionListName, ilist);
	}

	// Kept as regex because / is not escapable in Cucumber expressions
	// https://cucumber.io/docs/cucumber/cucumber-expressions/
	@Given("^" + wordPattern + " ← intersections\\(-√2\\/2:" + wordPattern
		+ ", √2\\/2:" + wordPattern + "\\)$")
	public void xsIntersectionsShapeShape(String intersectionListName,
		String shape1Name, String shape2Name)
	{
		double sqrtTwoDivTwo = Math.sqrt(2) / 2;
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(-sqrtTwoDivTwo, shape1);

		Shape shape2 = data.getShape(shape2Name);
		Intersection i2 = new Intersection(sqrtTwoDivTwo, shape2);

		IntersectionList ilist = new IntersectionList(i1, i2);
		data.put(intersectionListName, ilist);
	}

	@Given("{identifier} ← intersections\\(√2:{identifier})")
	public void xsIntersectionsShape(String intersectionListName,
		String shape1Name)
	{
		double sqrtTwo = Math.sqrt(2);
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(sqrtTwo, shape1);

		IntersectionList ilist = new IntersectionList(i1);
		data.put(intersectionListName, ilist);
	}

	@Given("{identifier} ← intersections\\({dbl}:{identifier}, {dbl}:{identifier})")
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

	@Given("{identifier} ← intersections\\({dbl}, {identifier})")
	public void xsIntersectionsShape(String intersectionListName,
		double shape1Dist, String shape1Name)
	{
		Shape shape1 = data.getShape(shape1Name);
		Intersection i1 = new Intersection(shape1Dist, shape1);

		IntersectionList ilist = new IntersectionList(i1);
		data.put(intersectionListName, ilist);
	}

	@When("{identifier} ← intersections\\({identifier}, {identifier})")
	public void xsIntersectionsII(String intersectionsName,
		String intersection1Name, String intersection2Name)
	{
		Intersection intersection1 = data.getIntersection(intersection1Name);
		Intersection intersection2 = data.getIntersection(intersection2Name);
		IntersectionList ilist = new IntersectionList(intersection1, intersection2);
		data.put(intersectionsName, ilist);
	}

	@When("{identifier} ← hit\\({identifier})")
	public void iHitXs(String intersectionName, String intersectionListName)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection hit = ilist.hit();
		data.put(intersectionName, hit);
	}

	@When("{identifier} ← prepare_computations\\({identifier}, {identifier})")
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

	@When("{identifier} ← prepare_computations\\({identifier}\\[{int}], {identifier}, {identifier})")
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

	@When("{identifier} ← prepare_computations\\({identifier}, {identifier}, {identifier})")
	public void compsPrepare_computationsIRXs(String computationsName,
		String intersectionName, String rayName, String intersectionListName)
	{
		Intersection intersection = data.getIntersection(intersectionName);

		IntersectionList intersectionList = data.getIntersectionList(intersectionListName);
		List<Intersection> intersectionArray = intersectionList.getIntersections();

		prepareComputations(computationsName, rayName, intersection,
			intersectionArray);
	}

	@When("{identifier} ← schlick\\({identifier})")
	public void reflectanceSchlickComps(String doubleName,
		String computationsName)
	{
		IntersectionComputations comps = data.getComputations(computationsName);

		data.put(doubleName, comps.schlick());
	}



	@Then("{identifier}[{int}].object = {identifier}")
	public void intersectionSetObject(String intersectionListName,
		int intersectionIdx, String objectName)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection intersection = ilist.get(intersectionIdx);
		Shape t = intersection.getObject();

		Shape expectedObject = data.getShape(objectName);
		Assert.assertEquals(t, expectedObject);
	}

	@Then("{identifier}[{int}].t = {dbl}")
	public void xsT(String intersectionListName, int intersectionIdx,
		double expectedValue)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		Intersection intersection = ilist.get(intersectionIdx);
		double t = intersection.getDistance();
		Assert.assertEquals(expectedValue, t, Tuple.EPSILON);
	}

	@Then("{identifier}.under_point.z > EPSILON\\/{int}")
	public void compsUnder_pointZEPSILON(String compsName, Integer divisor)
	{
		IntersectionComputations comps = data.getComputations(compsName);

		Assert.assertTrue(comps.getUnderPoint().getZ() > Tuple.EPSILON / divisor);
	}

	@Then("{identifier}.point.z < {identifier}.under_point.z")
	public void compsPointZCompsUnder_pointZ(String compsName1, String compsName2)
	{
		IntersectionComputations comps1 = data.getComputations(compsName1);
		IntersectionComputations comps2 = data.getComputations(compsName2);

		Assert.assertTrue(comps1.getPoint().getZ() < comps2.getUnderPoint().getZ());
	}
}
