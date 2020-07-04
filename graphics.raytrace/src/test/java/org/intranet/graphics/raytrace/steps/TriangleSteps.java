package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Triangle;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TriangleSteps
	extends StepsParent
{
	public TriangleSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← triangle\\({identifier}, {identifier}, {identifier})")
	public void createTriangle(String triangleName, String p1Name, String p2Name,
		String p3Name)
	{
		Point p1 = data.getPoint(p1Name);
		Point p2 = data.getPoint(p2Name);
		Point p3 = data.getPoint(p3Name);
		Triangle triangle = new Triangle(p1, p2, p3);
		data.put(triangleName, triangle);
	}

	@Then("{identifier}.p1 = {identifier}")
	public void testTriangleP1EqPoint(String triangleName, String expectedPointName)
	{
		Point expectedPoint = data.getPoint(expectedPointName);
		Triangle triangle = (Triangle)data.getShape(triangleName);
		Point actualPoint = triangle.getP1();
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Then("{identifier}.p2 = {identifier}")
	public void testTriangleP2EqPoint(String triangleName, String expectedPointName)
	{
		Point expectedPoint = data.getPoint(expectedPointName);
		Triangle triangle = (Triangle)data.getShape(triangleName);
		Point actualPoint = triangle.getP2();
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Then("{identifier}.p3 = {identifier}")
	public void testTriangleP3EqPoint(String triangleName, String expectedPointName)
	{
		Point expectedPoint = data.getPoint(expectedPointName);
		Triangle triangle = (Triangle)data.getShape(triangleName);
		Point actualPoint = triangle.getP3();
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Then("{identifier}.e1 = {vector}")
	public void testTriangleE1EqVector(String triangleName, Vector expectedVector)
	{
		Triangle triangle = (Triangle)data.getShape(triangleName);
		Vector actualVector = triangle.getE1();
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier}.e2 = {vector}")
	public void testTriangleE2EqVector(String triangleName, Vector expectedVector)
	{
		Triangle triangle = (Triangle)data.getShape(triangleName);
		Vector actualVector = triangle.getE2();
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier}.normal = {vector}")
	public void testTriangleNormalEqVector(String triangleName, Vector expectedVector)
	{
		Triangle triangle = (Triangle)data.getShape(triangleName);
		Vector actualVector = triangle.getNormal();
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Given("{identifier} ← triangle\\({point}, {point}, {point})")
	public void setTrianglePointPointPoint(String triangleName, Point p1,
		Point p2, Point p3)
	{
		Triangle t = new Triangle(p1, p2, p3);
		data.put(triangleName, t);
	}

	@Then("{identifier} = {identifier}.normal")
	public void testTriangleNormal(String actualNormalName, String triangleName)
	{
		Triangle t = (Triangle)data.getShape(triangleName);
		Vector expectedNormal = t.getNormal();

		Vector actualNormal = data.getVector(actualNormalName);

		Assert.assertEquals(expectedNormal, actualNormal);
	}
}
