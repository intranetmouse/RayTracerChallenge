package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
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
		data.putRay(rayName, ray);
	}

	@Whens({@When("{identifier} ← ray\\({point}, {vector})"),
		@When("{identifier} ← ray\\({point}, {vectorSSN})"),
		@When("{identifier} ← ray\\({point}, {vectorNSS})"),
		@When("{identifier} ← ray\\({pointNNS}, {vector})")})
	public void rRayPointVector(String rayName, Point point, Vector vector)
	{
		Ray ray = new Ray(point, vector);
		data.putRay(rayName, ray);
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
		data.putRay(rayName, ray);
	}

	@When("{identifier} ← transform\\({identifier}, {identifier})")
	public void rTransformRM(String newObjectName, String objToTransformName,
		String mtxTranslationName)
	{
		Ray ray = data.getRay(objToTransformName);
		BoundingBox box = data.getBoundingBox(objToTransformName);

		Matrix mtx = data.getMatrix(mtxTranslationName);

		if (ray != null)
		{
			Ray newRay = ray.transform(mtx);
			data.putRay(newObjectName, newRay);
		}
		else if (box != null)
		{
			BoundingBox newBox = box.transform(mtx);
			data.putBoundingBox(newObjectName, newBox);
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
