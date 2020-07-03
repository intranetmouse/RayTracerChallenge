package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.BoundingBox;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.Then.Thens;
import io.cucumber.java.en.When;
import io.cucumber.java.en.When.Whens;

public class RaysSteps
	extends StepsParent
{
	public RaysSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier} ← ray\\({identifier}, {identifier})")
	public void rRayOriginDirection(String rayName, String pointOriginName,
		String rayDirectionName)
	{
		Point point = data.getPoint(pointOriginName);
		Vector vector = data.getVector(rayDirectionName);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@Whens({@When("{identifier} ← ray\\({point}, {vector})"),
		@When("{identifier} ← ray\\({point}, {vectorSSN})"),
		@When("{identifier} ← ray\\({point}, {vectorNSS})"),
		@When("{identifier} ← ray\\({pointNNS}, {vector})")})
	public void rRayPointVector(String rayName, Point point, Vector vector)
	{
		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@Then("{identifier}.origin = {point}")
	public void rayOriginEqPoint(String actualRayName, Point expectedPoint)
	{
		Ray actualRay = data.getRay(actualRayName);
		Point actualPoint = actualRay.getOrigin();
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Then("{identifier}.origin = {identifier}")
	public void rayOriginEqIdentifier(String actualRayName, String expectedPointName)
	{
		Ray actualRay = data.getRay(actualRayName);
		Point actualPoint = actualRay.getOrigin();

		Point expectedPoint = data.getPoint(expectedPointName);
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Thens({@Then("{identifier}.direction = {vector}"),
		@Then("{identifier}.direction = {vectorSNS}")})
	public void rayDirectionEqVector(String actualRayName, Vector expectedVector)
	{
		Ray actualRay = data.getRay(actualRayName);
		Vector actualDirection = actualRay.getDirection();
		Assert.assertEquals(expectedVector, actualDirection);
	}

	@Then("{identifier}.direction = {identifier}")
	public void rayDirectionEqIdentifier(String actualRayName, String expectedVectorName)
	{
		Ray actualRay = data.getRay(actualRayName);
		Vector actualDirection = actualRay.getDirection();

		Vector expectedVector = data.getVector(expectedVectorName);
		Assert.assertEquals(expectedVector, actualDirection);
	}

	@When("{identifier} ← ray\\({point}, {identifier})")
	public void rRayPointVectorName(String rayName, Point point, String vectorName)
	{
		Vector vector = data.getVector(vectorName);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

//	@When("^" + wordPattern + " ← ray\\(point\\(" + threeDoublesPattern + "\\), vector\\(0, -√2/2, √2/2\\)\\)")
//	public void rRayPointVector(String rayName, double pointx, double pointy,
//		double pointz)
//	{
//		Point point = new Point(pointx, pointy, pointz);
//		double vectorx = 0.0;
//		double sqrt2div2 = Math.sqrt(2.0) / 2.0;
//		double vectory = -sqrt2div2;
//		double vectorz = sqrt2div2;
//		Vector vector = new Vector(vectorx, vectory, vectorz);
//
//		Ray ray = new Ray(point, vector);
//		data.put(rayName, ray);
//	}
//
//	@When("^" + wordPattern + " ← ray\\(point\\(0, 0, √2/2\\), vector\\(" + threeDoublesPattern + "\\)\\)")
//	public void rRayPointSqrtVector(String rayName, double vectorx, double vectory,
//		double vectorz)
//	{
//		double pointx = 0.0;
//		double sqrt2div2 = Math.sqrt(2.0) / 2.0;
//		double pointy = 0;
//		double pointz = sqrt2div2;
//		Point point = new Point(pointx, pointy, pointz);
//		Vector vector = new Vector(vectorx, vectory, vectorz);
//
//		Ray ray = new Ray(point, vector);
//		data.put(rayName, ray);
//	}

	@When("{identifier} ← transform\\({identifier}, {identifier})")
	public void rTransformRM(String newObjectName, String objToTransformName,
		String translationName)
	{
		Ray ray = data.getRay(objToTransformName);
		BoundingBox box = data.getBoundingBox(objToTransformName);

		Matrix mtx = data.getMatrix(translationName);

		if (ray != null)
		{
			Ray newRay = ray.transform(mtx);
			data.put(newObjectName, newRay);
		}
		else if (box != null)
		{
			BoundingBox newBox = box.transform(mtx);
			data.put(newObjectName, newBox);
		}
		else
			Assert.fail("Unknown object type for parameter " + objToTransformName);
	}



	@Then("position\\({identifier}, {dbl}) = {point}")
	public void positionRPoint(String rayName, double t, Point expected)
	{
		Ray ray = data.getRay(rayName);
		Point result = ray.position(t);

		Assert.assertEquals(expected, result);
	}
}
