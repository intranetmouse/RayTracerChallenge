package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LightsSteps
	extends StepsParent
{

	public LightsSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("^" + wordPattern + " ← point_light\\(point\\(" +
		threeDoublesPattern + "\\), color\\(" + threeDoublesPattern + "\\)\\)$")
	public void lightPoint_lightPositionPointColor(String pointLightName,
		double pointX, double pointY, double pointZ, double red, double green,
		double blue)
	{
		Point position = new Point(pointX, pointY, pointZ);
		Color color = new Color(red, green, blue);
		PointLight pointLight = new PointLight(position, color);
		data.put(pointLightName, pointLight);
	}

	@When("^" + wordPattern + " ← point_light\\(" + wordPattern + ", " + wordPattern + "\\)$")
	public void lightPoint_lightPositionIntensity(String pointLightName,
		String positionPointName, String colorName)
	{
		Point position = data.getPoint(positionPointName);
		Color color = data.getColor(colorName);
		PointLight pointLight = new PointLight(position, color);
		data.put(pointLightName, pointLight);
	}

	@Then(wordPattern + ".position = " + wordPattern)
	public void lightPositionPosition(String pointLightName,
		String expectedPositionName)
	{
		PointLight pointLight = data.getPointLight(pointLightName);
		Point expectedPosition = data.getPoint(expectedPositionName);
		Assert.assertEquals(expectedPosition, pointLight.getPosition());
	}

	@Then(wordPattern + ".intensity = " + wordPattern)
	public void lightIntensityIntensity(String pointLightName,
		String expectedIntensityName)
	{
		PointLight pointLight = data.getPointLight(pointLightName);
		Color expectedIntensity = data.getColor(expectedIntensityName);
		Assert.assertEquals(expectedIntensity, pointLight.getIntensity());
	}

}
