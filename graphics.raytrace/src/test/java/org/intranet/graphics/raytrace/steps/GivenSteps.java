package org.intranet.graphics.raytrace.steps;

import cucumber.api.java.en.Given;

public class GivenSteps
	extends StepsParent
{
	public GivenSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← " + intPattern)
	public void hsize(String varName, int value)
	{
		data.put(varName, value);
	}

	@Given(wordPattern + " ← π\\/" + doublePattern)
	public void fieldOfViewPi(String varName, double divisor)
	{
		double value = Math.PI / divisor;
		data.put(varName, value);
	}

}
