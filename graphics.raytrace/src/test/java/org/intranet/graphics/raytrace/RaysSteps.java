package org.intranet.graphics.raytrace;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
	public void rRayPointVector(String rayName, double pointx, double pointy, double pointz,
		double vectorx, double vectory, double vectorz)
	{
		Point point = new Point(pointx, pointy, pointz);
		Vector vector = new Vector(vectorx, vectory, vectorz);

		Ray ray = new Ray(point, vector);
		data.put(rayName, ray);
	}




	@Then("^" + wordPattern + ".origin = " + wordPattern)
	public void rOriginOrigin(String originRayName, String originPointName)
	{
		Ray originRay = data.getRay(originRayName);
		Point rayOriginPoint = originRay.getOrigin();
		Point originPoint = data.getPoint(originPointName);
		Assert.assertEquals(rayOriginPoint, originPoint);
	}

	@Then("^" + wordPattern + ".direction = " + wordPattern)
	public void rDirectionDirection(String directionRayName, String directionVectorName)
	{
		Ray directionRay = data.getRay(directionRayName);
		Vector rayDirectionVector = directionRay.getDirection();
		Vector directionVector = data.getVector(directionVectorName);
		Assert.assertEquals(rayDirectionVector, directionVector);
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
