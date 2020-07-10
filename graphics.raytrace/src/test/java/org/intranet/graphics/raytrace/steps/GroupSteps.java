package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.shape.Cube.Pair;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GroupSteps
	extends StepsParent
{
	public GroupSteps(RaytraceData data)
	{
		super(data);
	}

	@When("add_child\\({identifier}, {identifier})")
	public void add_childGS(String groupName, String childShapeName)
	{
		Group g = (Group)data.getShape(groupName);
		Shape s = data.getShape(childShapeName);

		g.addChild(s);
	}

	@Then("{identifier} includes {identifier}")
	public void gIncludesS(String groupName, String childShapeName)
	{
		Group g = (Group)data.getShape(groupName);
		Shape s = data.getShape(childShapeName);

		Assert.assertTrue(g.contains(s));
	}

	@Given("{identifier} ← {shape} of [{identifier}, {identifier}, {identifier}]")
	public void gGroupOfS1S2S3(String destGroupName, Shape s, String shape1Name,
		String shape2Name, String shape3Name)
	{
		Shape s1 = data.getShape(shape1Name);
		Shape s2 = data.getShape(shape2Name);
		Shape s3 = data.getShape(shape3Name);

		Group g = (Group)s;
		g.addChild(s1);
		g.addChild(s2);
		g.addChild(s3);

		data.put(destGroupName, g);
	}

	@When("\\({identifier}, {identifier}) ← partition_children\\({identifier})")
	public void leftRightPartition_childrenG(String leftStr, String rightStr,
		String groupName)
	{
		Group g = (Group)data.getShape(groupName);

		Pair<List<Shape>> partitions = g.partitionChildren();

		data.putShapeList(leftStr, partitions.getFirst());
		data.putShapeList(rightStr, partitions.getSecond());
	}

	@Then("{identifier} is a group of [{identifier}]")
	public void gIsAGroupOfS3(String groupName, String expectedChildShapeName)
	{
		Group group = (Group)data.getShape(groupName);
		List<Shape> children = group.getChildren();
		Assert.assertEquals(1, children.size());
		Shape expectedChild = data.getShape(expectedChildShapeName);
		Assert.assertEquals(expectedChild, children.get(0));
	}

	@Then("{identifier} = [{identifier}]")
	public void leftS1(String shapeListName, String expectedShapeName)
	{
		List<Shape> shapeList = data.getShapeList(shapeListName);

		Assert.assertEquals(1, shapeList.size());

		Shape expectedShape = data.getShape(expectedShapeName);

		Assert.assertEquals(expectedShape, shapeList.get(0));
	}

	@When("make_subgroup\\({identifier}, \\[{identifier}, {identifier}])")
	public void make_subgroupGS1S2(String groupName, String shape1Name, String shape2Name)
	{
		Group g = (Group)data.getShape(groupName);
		Shape s1 = data.getShape(shape1Name);
		Shape s2 = data.getShape(shape2Name);

		g.createSubgroup(s1, s2);
	}

	@Then("{identifier}[{int}] is a group of [{identifier}, {identifier}]")
	public void groupChildNIsAGroupContainingTwoShapes(String groupName,
		Integer index, String shape1Name, String shape2Name)
	{
		Group g = (Group)data.getShape(groupName);

		// Casting will assert it is a group
		Group sub = (Group)g.getChildren().get(index);

		Shape s1 = data.getShape(shape1Name);
		Shape s2 = data.getShape(shape2Name);

		Assert.assertEquals(s1, sub.getChildren().get(0));
		Assert.assertEquals(s2, sub.getChildren().get(1));
	}

	@When("divide\\({identifier}, {int})")
	public void divideGroupNTimes(String groupName, Integer numDivisions)
	{
		Shape g = data.getShape(groupName);

		g.divide(numDivisions);
	}

	@Then("{identifier}[{int}] = {identifier}")
	public void gS3(String groupName, Integer index, String expectedShapeName)
	{
		Group g = (Group)data.getShape(groupName);

		Shape child = g.getChildren().get(index);

		Shape expectedShape = data.getShape(expectedShapeName);

		Assert.assertEquals(expectedShape, child);
	}

	@Then("{identifier} ← {identifier}[{int}]")
	public void setShapeToGroupChildN(String shapeName, String groupName,
		Integer index)
	{
		Group g = (Group)data.getShape(groupName);
		Shape s = g.getChildren().get(index);

		data.put(shapeName, s);
	}

	@Then("{identifier} is a group")
	public void shapeIsAGroup(String shapeName)
	{
		Group g = (Group)data.getShape(shapeName);
	}

	@Then("{identifier} is a sphere")
	public void shapeIsASphere(String shapeName)
	{
		Sphere g = (Sphere)data.getShape(shapeName);
	}

	@Then("{identifier}[{int}] is a group of [{identifier}]")
	public void subgroupIsAGroupOfS1(String groupName, Integer index,
		String shapeName)
	{
		Group g = (Group)data.getShape(groupName);

		// Casting asserts it is a Group
		Group subGroup = (Group)g.getChildren().get(index);

		Assert.assertEquals(1, subGroup.getChildren().size());

		Shape expectedSubGroupChild = data.getShape(shapeName);
		Assert.assertEquals(expectedSubGroupChild, subGroup.getChildren().get(0));
	}

	@Given("{identifier} ← {shape} of [{identifier}, {identifier}]")
	public void setGroupOfChildren(String groupName, Shape shapeGroup, String child1Name, String child2Name)
	{
		Shape child1 = data.getShape(child1Name);
		Shape child2 = data.getShape(child2Name);
		Group group = (Group)shapeGroup;
		group.addChild(child1);
		group.addChild(child2);
		data.put(groupName, group);
	}
}
