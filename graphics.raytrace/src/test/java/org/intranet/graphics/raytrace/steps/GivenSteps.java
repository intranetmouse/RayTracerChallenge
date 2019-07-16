package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.GradientPattern;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class GivenSteps
	extends StepsParent
{
	private final class TestPattern
		extends Pattern
	{
		@Override
		public Color colorAt(Point point)
		{
			return new Color(point.getX(), point.getY(), point.getZ());
		}
	}

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

	@Given(wordPattern + " ← (stripe|gradient)_pattern\\(" + twoWordPattern + "\\)")
	public void patternStripe_patternWhiteBlack(String patternName,
		String patternType, String color1Name, String color2Name)
	{
		Color color1 = data.getColor(color1Name);
		Color color2 = data.getColor(color2Name);

		Pattern pattern = "stripe".equals(patternType) ?
			new StripePattern(color1, color2) :
			new GradientPattern(color1, color2);
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

	@Given("^set_pattern_transform\\(" + wordPattern + ", (scaling|translation)\\(" + threeDoublesPattern + "\\)\\)")
	public void set_pattern_transformPatternScaling(String patternName,
		String operation, double xformX, double xformY, double xformZ)
	{
		Pattern pattern = data.getPattern(patternName);
		Matrix mtx = "scaling".equals(operation) ?
			Matrix.newScaling(xformX, xformY, xformZ) :
			Matrix.newTranslation(xformX, xformY, xformZ);
		pattern.setTransform(mtx);
	}

	@When(wordPattern + " ← (?:stripe_at_object|pattern_at_shape)\\(" + twoWordPattern + ", point\\(" + threeDoublesPattern + "\\)\\)")
	public void cStripe_at_objectPatternObjectPoint(String assignColorName,
		String patternName, String shapeName, Double pointX, Double pointY,
		Double pointZ)
	{
		Point pt = new Point(pointX, pointY, pointZ);
		Pattern pattern = data.getPattern(patternName);
		Shape shape = data.getShape(shapeName);

		Color c = shape.colorAt(pattern, pt);
		data.put(assignColorName, c);
	}

	@Given(wordPattern + " ← test_pattern\\(\\)")
	public void patternTest_pattern(String patternName)
	{
		Pattern pattern = new TestPattern();
		data.put(patternName, pattern);
	}
}
