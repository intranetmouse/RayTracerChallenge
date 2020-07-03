package org.intranet.graphics.raytrace.steps;

import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.traversal.AcrossDownTraversal;
import org.intranet.graphics.raytrace.traversal.ScatteredTraversal;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PixelCoordinateSteps
	extends StepsParent
{

	public PixelCoordinateSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← acrossDownTraversal\\({int}, {int})")
	public void tAcrossDownTraversal(String traversalName,
		int width, int height)
	{
		AbstractSpliterator<PixelCoordinate> traversal = new AcrossDownTraversal(
			width, height);
		data.put(traversalName, traversal);
	}
	@Given("{identifier} ← scatteredTraversal\\({int}, {int})")
	public void tScatteredTraversal(String traversalName,
		int width, int height)
	{
		AbstractSpliterator<PixelCoordinate> traversal = new ScatteredTraversal(
			width, height, null);
		data.put(traversalName, traversal);
	}

	boolean found = false;
	PixelCoordinate pixel = null;

	@When("{identifier} ← getPixelCoordinate\\({identifier})")
	public void firstGetPixelCoordinateT(String pixelCoordName,
		String traversalName)
	{
		Spliterator<PixelCoordinate> traversal = data.getPixelCoordinateSpliterator(
			traversalName);
		pixel = null;
		if (traversal.tryAdvance(e -> pixel = e))
			data.put(pixelCoordName, pixel);
	}

	@Then("{identifier} is null")
	public void thirdIsNull(String pixelCoordinateName)
	{
		PixelCoordinate coord = data.getPixelCoordinate(pixelCoordinateName);
		Assert.assertNull(coord);
	}

	@Then("{identifier}.height = {int}")
	public void assertHeightEqualsInt(String objectName, int expectedValue)
	{
		Canvas canvas = data.getCanvas(objectName);
		if (canvas == null)
			Assert.fail("Unrecognized object name " + objectName);

		Assert.assertEquals((int)expectedValue, canvas.getHeight());
	}
}
