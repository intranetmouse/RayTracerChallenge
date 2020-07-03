package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.shape.TubeLike;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CylinderSteps
	extends StepsParent
{
	public CylinderSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier}.minimum ← {dbl}")
	public void objAssignMinimum(String objectName, double value)
	{
		TubeLike shape = data.getTubelike(objectName);
		((TubeLike)shape).setMinimum(value);
	}

	@Given("{identifier}.maximum ← {dbl}")
	public void objAssignMaximum(String objectName, double value)
	{
		TubeLike shape = data.getTubelike(objectName);
		shape.setMaximum(value);
	}

	@Given("{identifier}.minimum = {dbl}")
	public void objCheckMinimum(String objectName, double value)
	{
		TubeLike shape = data.getTubelike(objectName);
		((TubeLike)shape).setMinimum(value);
	}

	@Given("{identifier}.closed ← {boolean}")
	public void cylClosedIsBoolean(String shapeName, Boolean value)
	{
		TubeLike tubeLikeShape = data.getTubelike(shapeName);
		tubeLikeShape.setClosed(value);
	}

	@Then("{identifier}.closed = {boolean}")
	public void cylIsClosed(String shapeName, Boolean value)
	{
		TubeLike tubeLikeShape = data.getTubelike(shapeName);
		Assert.assertEquals(value, tubeLikeShape.isClosed());
		tubeLikeShape.setClosed(value);
	}
}
