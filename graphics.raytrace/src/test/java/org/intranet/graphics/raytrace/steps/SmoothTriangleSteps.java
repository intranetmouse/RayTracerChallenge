package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Triangle;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SmoothTriangleSteps
	extends StepsParent
{
	public SmoothTriangleSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier} ← smooth_triangle\\({identifier}, {identifier}, {identifier}, {identifier}, {identifier}, {identifier})")
	public void setSmoothTriangleP1P2P3N1N2N3(String triangleName,
		String point1Name, String point2Name, String point3Name,
		String normal1Name, String normal2Name, String normal3Name)
	{
		Point p1 = data.getPoint(point1Name);
		Point p2 = data.getPoint(point2Name);
		Point p3 = data.getPoint(point3Name);

		Vector v1 = data.getVector(normal1Name);
		Vector v2 = data.getVector(normal2Name);
		Vector v3 = data.getVector(normal3Name);

		Triangle smoothTriangle = new Triangle(p1, p2, p3, v1, v2, v3);

		data.putShape(triangleName, smoothTriangle);
	}

	@Then("{identifier}.n1 = {identifier}")
	public void testTriangleN1EqPoint(String triangleName, String expectedVectorName)
	{
		Vector expectedVector = data.getVector(expectedVectorName);
		Shape shape = data.getShape(triangleName);
		Triangle triangle = (Triangle)shape;
		Vector actualVector = triangle.getN1();
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier}.n2 = {identifier}")
	public void testTriangleN2EqPoint(String triangleName, String expectedVectorName)
	{
		Vector expectedVector = data.getVector(expectedVectorName);
		Shape shape = data.getShape(triangleName);
		Triangle triangle = (Triangle)shape;
		Vector actualVector = triangle.getN2();
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier}.n3 = {identifier}")
	public void testTriangleN3EqPoint(String triangleName, String expectedVectorName)
	{
		Vector expectedVector = data.getVector(expectedVectorName);
		Shape shape = data.getShape(triangleName);
		Triangle triangle = (Triangle)shape;
		Vector actualVector = triangle.getN3();
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier}[{int}].u = {double}")
	public void assertIntersectionU(String intersectionsName, Integer idx, Double expectedValue)
	{
		IntersectionList intersections = data.getIntersectionList(intersectionsName);
		Intersection intersection = intersections.get(idx);

		Assert.assertEquals(expectedValue, intersection.getU(), Tuple.EPSILON);
	}

	@Then("{identifier}[{int}].v = {double}")
	public void assertIntersectionV(String intersectionsName, Integer idx, Double expectedValue)
	{
		IntersectionList intersections = data.getIntersectionList(intersectionsName);

		Intersection intersection = intersections.get(idx);

		Assert.assertEquals(expectedValue, intersection.getV(), Tuple.EPSILON);
	}

	@When("{identifier} ← normal_at\\({identifier}, {point}, {identifier})")
	public void nNormal_atTriPointI(String normalName, String shapeName,
		Point pt, String intersectionName)
	{
		Triangle s = (Triangle)data.getShape(shapeName);
		Intersection intersection = data.getIntersection(intersectionName);

		Vector normal = s.normalAt(pt, intersection);
		data.putVector(normalName, normal);
	}
}
