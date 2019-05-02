package org.intranet.graphics.raytrace.steps;

import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;

import org.intranet.graphics.raytrace.AcrossDownTraversal;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.ScatteredTraversal;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PixelCoordinateSteps
	extends StepsParent
{

	public PixelCoordinateSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← (acrossDownTraversal|scatteredTraversal)\\(" + twoIntsPattern + "\\)")
	public void tAcrossDownTraversal(String traversalName, String traversalType, int width,
		int height)
	{
		AbstractSpliterator<PixelCoordinate> traversal =
			"acrossDownTraversal".equals(traversalType)
			? new AcrossDownTraversal(width, height)
			: new ScatteredTraversal(width, height, null);
		data.put(traversalName, traversal);
	}

	boolean found = false;
	PixelCoordinate pixel = null;

	@When(wordPattern + " ← getPixelCoordinate\\(" + wordPattern + "\\)")
	public void firstGetPixelCoordinateT(String pixelCoordName,
		String traversalName)
	{
		Spliterator<PixelCoordinate> traversal = data.getPixelCoordinateSpliterator(traversalName);
		pixel = null;
		if (traversal.tryAdvance(e -> pixel = e))
			data.put(pixelCoordName, pixel);
	}

	@Then(wordPattern + " is null")
	public void thirdIsNull(String pixelCoordinateName)
	{
		PixelCoordinate coord = data.getPixelCoordinate(pixelCoordinateName);
		Assert.assertNull(coord);
	}

}
