package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.junit.Assert;

import io.cucumber.java.en.Then;

public class PatternSteps
	extends StepsParent
{
	public PatternSteps(RaytraceData data)
	{
		super(data);
	}

	@Then("{identifier}.a = {identifier}")
	public void patternA_eqColor(String patternName, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);
		StripePattern pattern = (StripePattern)data.getPattern(patternName);
		Color actualColor = pattern.getA();
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("{identifier}.b = {identifier}")
	public void patternB_eqColor(String patternName, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);
		StripePattern pattern = (StripePattern)data.getPattern(patternName);
		Color actualColor = pattern.getB();
		Assert.assertEquals(expectedColor, actualColor);
	}
}