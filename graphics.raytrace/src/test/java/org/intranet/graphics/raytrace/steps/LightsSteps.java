package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.PointLight;
import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LightsSteps
	extends StepsParent
{

	public LightsSteps(RaytraceData data)
	{
		super(data);
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
