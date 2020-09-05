package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Pair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public final class BoundingBoxSteps
	extends StepsParent
{
	public BoundingBoxSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← bounding_box\\(empty)")
	public void boxBounding_boxEmpty(String boxName)
	{
		BoundingBox box = new BoundingBox();
		data.putBoundingBox(boxName, box);
	}

	@Given("{identifier} ← bounding_box\\(min={point} max={point})")
	public void boxBounding_boxMinPointMaxPoint(String boxName, Point minPoint,
		Point maxPoint)
	{
		BoundingBox box = new BoundingBox(minPoint, maxPoint);
		data.putBoundingBox(boxName, box);

	}

	@When("{identifier} ← bounds_of\\({identifier})")
	public void boxBounds_ofShape(String boxName, String shapeName)
	{
		Shape s = data.getShape(shapeName);
		BoundingBox box = s.getBoundingBox();
		data.putBoundingBox(boxName, box);
	}

	@Then("box_contains_point\\({identifier}, {identifier}) is {boolean}")
	public void boxContainsPointIsTrue(String boxName, String pointName,
		boolean expectedResult)
	{
		BoundingBox box = data.getBoundingBox(boxName);
		Point p = data.getPoint(pointName);

		Assert.assertEquals(expectedResult, box.containsPoint(p));
	}

	@Then("box_contains_box\\({identifier}, {identifier}) is {boolean}")
	public void boxContainsBoxIsTrue(String boxName, String otherBoxName,
		boolean expectedResult)
	{
		BoundingBox box = data.getBoundingBox(boxName);
		BoundingBox otherBox = data.getBoundingBox(otherBoxName);

		Assert.assertEquals(expectedResult, box.containsBox(otherBox));
	}

	@Then("intersects\\({identifier}, {identifier}) is {boolean}")
	public void intersectsBoxRIsTrue(String boxName, String rayName, boolean expectedResult)
	{
		BoundingBox box = data.getBoundingBox(boxName);
		Ray ray = data.getRay(rayName);

		Assert.assertEquals(expectedResult, box.intersects(ray));
	}

	@When("{identifier} ← parent_space_bounds_of\\({identifier})")
	public void boxParent_space_bounds_ofShape(String boundingBoxName, String shapeName)
	{
		Shape s = data.getShape(shapeName);

		BoundingBox box = s.getParentSpaceBounds();

		data.putBoundingBox(boundingBoxName, box);
	}

	@When("\\({identifier}, {identifier}) ← split_bounds\\({identifier})")
	public void leftRightSplit_boundsBox(String firstBoxName, String secondBoxName, String originalBoxName)
	{
		BoundingBox originalBox = data.getBoundingBox(originalBoxName);

		Pair<BoundingBox> split = originalBox.split();
		data.putBoundingBox(firstBoxName, split.getFirst());
		data.putBoundingBox(secondBoxName, split.getSecond());
	}
}