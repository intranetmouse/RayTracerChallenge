package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.shape.Group;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GroupSteps
	extends StepsParent
{
	public GroupSteps(RaytraceData data)
	{
		super(data);
	}

	@When("^add_child\\(" + wordPattern + ", " + wordPattern + "\\)$")
	public void add_childGS(String groupName, String childShapeName)
	{
		Group g = (Group)data.getShape(groupName);
		Shape s = data.getShape(childShapeName);

		g.addChild(s);
	}

	@Then(wordPattern + " includes " + wordPattern)
	public void gIncludesS(String groupName, String childShapeName)
	{
		Group g = (Group)data.getShape(groupName);
		Shape s = data.getShape(childShapeName);

		Assert.assertTrue(g.contains(s));
	}
}
