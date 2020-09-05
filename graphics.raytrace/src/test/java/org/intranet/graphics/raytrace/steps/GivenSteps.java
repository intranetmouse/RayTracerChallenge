package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.CheckerPattern;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.GradientPattern;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.RingPattern;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.When.Whens;

public class GivenSteps
	extends StepsParent
{
	public GivenSteps(RaytraceData data)
	{
		super(data);
	}


	@Given("{identifier} ← {int}")
	public void hsize(String varName, int value)
	{
		data.putInt(varName, value);
	}

	@Given("{identifier} ← π\\/{dbl}")
	public void fieldOfViewPi(String varName, double divisor)
	{
		double value = Math.PI / divisor;
		data.putDouble(varName, value);
	}

	@Given("{identifier} ← {boolean}")
	public void in_shadowTrue(String booleanName, Boolean booleanValue)
	{
		data.putBoolean(booleanName, booleanValue);
	}

	@Given("{identifier} ← {identifier}_pattern\\({identifier}, {identifier})")
	public void patternStripe_patternWhiteBlack(String patternName,
		String patternType, String color1Name, String color2Name)
	{
		Color color1 = data.getColor(color1Name);
		Color color2 = data.getColor(color2Name);

		Pattern pattern =
			"stripe".equals(patternType) ? new StripePattern(color1, color2) :
			"ring".equals(patternType) ? new RingPattern(color1, color2) :
			"checkers".equals(patternType) ? new CheckerPattern(color1, color2) :
			"gradient".equals(patternType) ? new GradientPattern(color1, color2) :
			null;
		Assert.assertNotNull("unrecognized pattern " + patternName, pattern);
		data.putPattern(patternName, pattern);
	}

	@Given("{identifier}.pattern ← stripe_pattern\\({color}, {color})")
	public void mPatternStripe_patternColorColor(String materialName,
		Color color1, Color color2)
	{
		Material material = data.getMaterial(materialName);
		Pattern pattern = new StripePattern(color1, color2);
		material.setPattern(pattern);
	}

	@Given("{identifier} ← stripe_pattern\\({color}, {color})")
	public void pattern_setStripePatternColorColor(String patternName,
		Color color1, Color color2)
	{
		Pattern pattern = new StripePattern(color1, color2);
		data.putPattern(patternName, pattern);
	}

	@Given("set_pattern_transform\\({identifier}, {matrix})")
	public void set_pattern_transformPatternScaling(String patternName,
		Matrix mtx)
	{
		Pattern pattern = data.getPattern(patternName);
		pattern.setTransform(mtx);
	}

	@Whens({
		@When("{identifier} ← pattern_at_shape\\({identifier}, {identifier}, {point})"),
		@When("{identifier} ← stripe_at_object\\({identifier}, {identifier}, {point})")
	})
	public void cStripe_at_objectPatternObjectPoint(String assignColorName,
		String patternName, String shapeName, Point pt)
	{
		Pattern pattern = data.getPattern(patternName);
		Shape shape = data.getShape(shapeName);

		Color c = shape.colorAt(pattern, pt);
		data.putColor(assignColorName, c);
	}

	@Given("{identifier} ← {test_pattern}")
	public void patternTest_pattern(String patternName, Pattern pattern)
	{
		data.putPattern(patternName, pattern);
	}

	@Given("{identifier} has:")
	public void shapeHas(String shapeName, DataTable dataTable)
	{
		Shape shape = data.getShape(shapeName);

		WorldSteps.setShapePropertiesFromDataTable(dataTable, shape);
	}

	@When("{identifier} ← world_to_object\\({identifier}, {point})")
	public void pWorld_to_objectSPoint(String pointName, String shapeName, Point p)
	{
		Shape shape = data.getShape(shapeName);
		Point newPoint = shape.worldToObject(p);
		data.putPoint(pointName, newPoint);
	}

	@When("{identifier} ← normal_to_world\\({identifier}, {vectorSSS})")
	public void nNormal_to_worldSVector(String normalName, String shapeName,
		Vector v)
	{
		Vector normal = v.normalize();
		Shape shape = data.getShape(shapeName);

		Vector worldNormal = shape.normalToWorld(normal);
		data.putVector(normalName, worldNormal);
	}
}
