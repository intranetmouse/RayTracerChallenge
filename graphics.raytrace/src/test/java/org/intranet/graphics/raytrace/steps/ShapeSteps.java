package org.intranet.graphics.raytrace.steps;

import cucumber.api.java.en.Given;

public class ShapeSteps extends StepsParent
{
	public ShapeSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← test_shape\\(\\)")
	public void sTest_shape(String shapeName)
	{
		data.put(shapeName, new TestShape());
	}
}
