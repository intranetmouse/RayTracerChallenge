package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.light.AreaLight;
import org.intranet.graphics.raytrace.light.PointLight;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Sequence;
import org.intranet.graphics.raytrace.surface.Color;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Given.Givens;
import io.cucumber.java.en.Then;
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

	@Given("{identifier} ← {identifier}.light")
	public void lightWLight(String lightVarName, String worldName)
	{
		World w = data.getWorld(worldName);
		Light light = w.getLightSources().get(0);
		data.put(lightVarName, light);
	}

	@When("{identifier} ← intensity_at\\({identifier}, {identifier}, {identifier})")
	public void intensityIntensity_atLightPtW(String resultVarStr,
		String lightName, String pointName, String worldName)
	{
		Light light = data.getLight(lightName);
		Point point = data.getPoint(pointName);
		World w = data.getWorld(worldName);

		double intensity = light.intensityAt(point, w);
		data.put(resultVarStr, intensity);
	}

	@When("{identifier} ← area_light\\({identifier}, {identifier}, {int}, {identifier}, {int}, {color})")
	public void lightArea_lightCornerV1V2Color(String lightName,
		String cornerPointName, String unscaledUvectorName,
		int unscaledUvectorSteps, String unscaledVvectorName,
		int unscaledVvectorSteps, Color color)
	{
		Point cornerPoint = data.getPoint(cornerPointName);
		Vector unscaledUvector = data.getVector(unscaledUvectorName);
		Vector unscaledVvector = data.getVector(unscaledVvectorName);

		Light light = new AreaLight(cornerPoint, unscaledUvector,
			unscaledUvectorSteps, unscaledVvector, unscaledVvectorSteps, color);
		data.put(lightName, light);
	}

	@Then("{identifier}.corner = {identifier}")
	public void lightCornerCorner(String areaLightName,
		String expectedCornerPointName)
	{
		Point expectedCornerPoint = data.getPoint(expectedCornerPointName);
		Assert.assertNotNull(expectedCornerPoint);

		AreaLight light = (AreaLight)data.getLight(areaLightName);
		Point actualCornerPoint = light.getCorner();

		Assert.assertEquals(expectedCornerPoint, actualCornerPoint);
	}

	@Then("{identifier}.uvec = {vector}")
	public void lightUvecVector(String areaLightName, Vector expectedVector)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);
		Vector actualUvec = light.getUvec();

		Assert.assertEquals(expectedVector, actualUvec);
	}

	@Then("{identifier}.usteps = {int}")
	public void lightUsteps(String areaLightName, int expectedUsteps)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);
		int actualUsteps = light.getUsteps();

		Assert.assertEquals(expectedUsteps, actualUsteps);
	}

	@Then("{identifier}.vvec = {vector}")
	public void lightVvecVector(String areaLightName, Vector expectedVvec)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);
		Vector actualVvec = light.getVvec();

		Assert.assertEquals(expectedVvec, actualVvec);
	}

	@Then("{identifier}.vsteps = {int}")
	public void lightVsteps(String areaLightName, int expectedNumVsteps)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);
		int actualNumVsteps = light.getVsteps();

		Assert.assertEquals(expectedNumVsteps, actualNumVsteps);
	}

	@Then("{identifier}.samples = {int}")
	public void lightSamples(String areaLightName, int int1)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);
		int actualNumSamples = light.getNumSamples();

		Assert.assertEquals(int1, actualNumSamples);
	}

	@Then("{identifier}.position = {point}")
	public void lightPositionPoint(String areaLightName, Point expectedPosition)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);
		Point actualPosition = light.getPosition();

		Assert.assertEquals(expectedPosition, actualPosition);
	}

	@When("{identifier} ← point_on_light\\({identifier}, {int}, {int})")
	public void ptPoint_on_lightLight(String pointName, String areaLightName,
		int u, int v)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);

		Point point = light.pointOnLight(u, v);
		data.put(pointName, point);
	}

	@Givens({
		@Given("{identifier}.jitter_by ← {sequence2}"),
		@Given("{identifier}.jitter_by ← {sequence5}")
	})
	public void lightJitter_bySequence(String areaLightName, Sequence s)
	{
		AreaLight light = (AreaLight)data.getLight(areaLightName);

		light.setJitterBy(s);
	}
}
