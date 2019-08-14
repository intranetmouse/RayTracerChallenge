package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.shape.Cylinder;

import io.cucumber.java.en.Given;

public class CylinderSteps
	extends StepsParent
{
	public CylinderSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← cylinder\\(\\)")
	public void cCylinder(String shapeName)
	{
		data.put(shapeName, new Cylinder());
	}
}
