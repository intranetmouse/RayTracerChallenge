package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.BoundingBox;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RaysSteps
	extends StepsParent
{
	public RaysSteps(RaytraceData data)
	{
		super(data);
	}

	@When("^" + wordPattern + " ← ray\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void rRayOriginDirection(String rayName, String pointOriginName,
		String rayDirectionName)
	{
		Point point = data.getPoint(pointOriginName);
		Vector vector = data.getVector(rayDirectionName);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@When("^" + wordPattern + " ← ray\\(point\\(" + threeDoublesPattern + "\\), vector\\(" + threeDoublesPattern + "\\)\\)")
	public void rRayPointVector(String rayName, double pointx, double pointy,
		double pointz, double vectorx, double vectory, double vectorz)
	{
		Point point = new Point(pointx, pointy, pointz);
		Vector vector = new Vector(vectorx, vectory, vectorz);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@When("^" + wordPattern + " ← ray\\(point\\(" + threeDoublesPattern + "\\), " + wordPattern + "\\)")
	public void rRayPointVectorName(String rayName, double pointx, double pointy,
		double pointz, String vectorName)
	{
		Point point = new Point(pointx, pointy, pointz);
		Vector vector = data.getVector(vectorName);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@When("^" + wordPattern + " ← ray\\(point\\(" + threeDoublesPattern + "\\), vector\\(0, -√2/2, √2/2\\)\\)")
	public void rRayPointVector(String rayName, double pointx, double pointy,
		double pointz)
	{
		Point point = new Point(pointx, pointy, pointz);
		double vectorx = 0.0;
		double sqrt2div2 = Math.sqrt(2.0) / 2.0;
		double vectory = -sqrt2div2;
		double vectorz = sqrt2div2;
		Vector vector = new Vector(vectorx, vectory, vectorz);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@When("^" + wordPattern + " ← ray\\(point\\(0, 0, √2/2\\), vector\\(" + threeDoublesPattern + "\\)\\)")
	public void rRayPointSqrtVector(String rayName, double vectorx, double vectory,
		double vectorz)
	{
		double pointx = 0.0;
		double sqrt2div2 = Math.sqrt(2.0) / 2.0;
		double pointy = 0;
		double pointz = sqrt2div2;
		Point point = new Point(pointx, pointy, pointz);
		Vector vector = new Vector(vectorx, vectory, vectorz);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}

	@When(wordPattern + " ← transform\\(" + twoWordPattern + "\\)")
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



	@Then("^position\\(" + wordPattern + ", " + doublePattern + "\\) = point\\(" + threeDoublesPattern + "\\)$")
	public void positionRPoint(String rayName, double t, double x, double y, double z)
	{
		Ray ray = data.getRay(rayName);
		Point result = ray.position(t);

		Point expected = new Point(x, y, z);

		Assert.assertEquals(expected, result);
	}

}
