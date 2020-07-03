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

	@Given("{identifier} ← point_light\\({point}, {color})")
	public void lightPoint_lightPositionPointColor(String pointLightName,
		Point position, Color color)
	{
		PointLight pointLight = new PointLight(position, color);
		data.put(pointLightName, pointLight);
	}

	@When("{identifier} ← point_light\\({identifier}, {identifier})")
	public void lightPoint_lightPositionIntensity(String pointLightName,
		String positionPointName, String colorName)
	{
		Point position = data.getPoint(positionPointName);
		Color color = data.getColor(colorName);
		PointLight pointLight = new PointLight(position, color);
		data.put(pointLightName, pointLight);
	}
}
