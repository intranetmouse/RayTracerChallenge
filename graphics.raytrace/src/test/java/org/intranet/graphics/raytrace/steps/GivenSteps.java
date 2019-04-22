package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Pattern;
import org.intranet.graphics.raytrace.StripePattern;
import org.junit.Assert;

import cucumber.api.java.en.Given;

public class GivenSteps
	extends StepsParent
{
	public GivenSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← " + intPattern)
	public void hsize(String varName, int value)
	{
		data.put(varName, value);
	}

	@Given(wordPattern + " ← π\\/" + doublePattern)
	public void fieldOfViewPi(String varName, double divisor)
	{
		double value = Math.PI / divisor;
		data.put(varName, value);
	}

	@Given(wordPattern + " ← (true|false)")
	public void in_shadowTrue(String booleanName, String booleanValueStr)
	{
		boolean booleanValue = "true".contentEquals(booleanValueStr);
		data.put(booleanName, booleanValue);
	}

	@Given(wordPattern + " ← stripe_pattern\\(" + twoWordPattern + "\\)")
	public void patternStripe_patternWhiteBlack(String patternName,
		String color1Name, String color2Name)
	{
		Color color1 = data.getColor(color1Name);
		Color color2 = data.getColor(color2Name);

		Pattern pattern = new StripePattern(color1, color2);
		data.put(patternName, pattern);
	}

	@Given(wordPattern + "\\." + wordPattern + " ← " + doublePattern)
	public void objPropAssignDouble(String objectName, String propertyName,
		double value)
	{
		Material material = data.getMaterial(objectName);

		if (material != null)
		{
			switch (propertyName)
			{
				case "ambient":
					material.setAmbient(value);
					return;
				case "diffuse":
					material.setDiffuse(value);
					return;
				case "specular":
					material.setSpecular(value);
					return;
				default:
					Assert.fail("Unknown material property " + propertyName);
			}
		}

		Assert.fail("Unknown object name " + objectName);
	}

	@Given(wordPattern + ".pattern ← stripe_pattern\\(color\\(" +
		threeDoublesPattern + "\\), color\\(" + threeDoublesPattern + "\\)\\)")
	public void mPatternStripe_patternColorColor(String materialName,
		double red1, double green1, double blue1, double red2, double green2,
		double blue2)
	{
		Material material = data.getMaterial(materialName);
		Color color1 = new Color(red1, green1, blue1);
		Color color2 = new Color(red2, green2, blue2);
		Pattern pattern = new StripePattern(color1, color2);
		material.setPattern(pattern);
	}

}
