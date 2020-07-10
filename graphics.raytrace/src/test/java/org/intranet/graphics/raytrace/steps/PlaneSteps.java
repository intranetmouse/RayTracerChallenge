package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Group;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public final class PlaneSteps
	extends StepsParent
{
	public PlaneSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier} ← local_normal_at\\({identifier}, {point})")
	public void nLocal_normal_atPPoint(String normalVectorName, String shapeName,
		Point normalLocation)
	{
		localNormalAt(normalVectorName, shapeName, normalLocation);
	}

	@When("{identifier} ← local_normal_at\\({identifier}, {identifier})")
	public void normalLocal_normal_atCP(String normalVectorName,
		String shapeName, String pointName)
	{
		Point normalLocation = data.getPoint(pointName);

		localNormalAt(normalVectorName, shapeName, normalLocation);
	}

	private void localNormalAt(String normalVectorName, String shapeName,
		Point normalLocation)
	{
		Shape shape = data.getShape(shapeName);

		Vector normalVector = shape.testLocalNormalAt(normalLocation);
		data.put(normalVectorName, normalVector);
	}


	@When("{identifier} ← local_intersect\\({identifier}, {identifier})")
	public void xsLocal_intersectPR(String intersectionsName, String shapeName,
		String rayName)
	{
		Ray ray = data.getRay(rayName);
		Shape shape = data.getShape(shapeName);
		IntersectionList ilist = shape.intersections(ray);
System.out.println("PlaneSteps local_intersect adding IntersectionList named " + intersectionsName);
		data.put(intersectionsName, ilist);
	}

	@Then("{identifier} is empty")
	public void xsIsEmpty(String objectName)
	{
		IntersectionList intersections = data.getIntersectionList(
			objectName);
		if (intersections != null)
		{
			Assert.assertEquals(0, intersections.count());
			return;
		}
		Shape shape = data.getShape(objectName);
		if (shape != null)
		{
			Assert.assertTrue(((Group)shape).isEmpty());
			return;
		}
		Assert.fail("unrecognized object name " + objectName);
	}

	@Then("{identifier} is not empty")
	public void xsIsNotEmpty(String objectName)
	{
		IntersectionList intersections = data.getIntersectionList(
			objectName);
		if (intersections != null)
		{
			Assert.assertTrue(intersections.count() > 0);
			return;
		}
		Shape shape = data.getShape(objectName);
		if (shape != null)
		{
			Assert.assertFalse(((Group)shape).isEmpty());
			return;
		}
		Assert.fail("unrecognized object name " + objectName);
	}
}
