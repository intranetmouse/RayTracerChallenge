package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.shape.BoundingBox;
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
		data.put(boxName, box);
	}

	@Given("{identifier} ← bounding_box\\(min={point} max={point})")
	public void boxBounding_boxMinPointMaxPoint(String boxName, Point minPoint,
		Point maxPoint)
	{
		BoundingBox box = new BoundingBox(minPoint, maxPoint);
		data.put(boxName, box);

	}

	@When("{identifier} ← bounds_of\\({identifier})")
	public void boxBounds_ofShape(String boxName, String shapeName)
	{
		Shape s = data.getShape(shapeName);
		BoundingBox box = s.getBoundingBox();
		data.put(boxName, box);
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
}