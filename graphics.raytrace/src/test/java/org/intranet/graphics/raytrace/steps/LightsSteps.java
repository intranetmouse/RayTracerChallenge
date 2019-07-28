package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.surface.Color;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

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

}
