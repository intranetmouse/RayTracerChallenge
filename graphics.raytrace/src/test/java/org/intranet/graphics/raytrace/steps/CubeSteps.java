package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.shape.Cube;

import io.cucumber.java.en.Given;

public class CubeSteps extends StepsParent
{
	public CubeSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← cube\\(\\)")
	public void cCube(String shapeName)
	{
		data.put(shapeName, new Cube());
	}
}
