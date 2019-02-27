package org.intranet.graphics.raytrace;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TuplesSteps
{
	private Tuple a;
	private Map<String, Point> pointMap = new HashMap<>();
	private Map<String, Vector> vectorMap = new HashMap<>();
	private Map<String, Color> colorMap = new HashMap<>();

	@Given("^a ← tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void aTuple(double x, double y, double z, double w)
	{
		a = Tuple.dblEqual(1.0, w) ? new Point(x, y, z) :
			Tuple.dblEqual(0.0, w) ? new Vector(x, y, z) :
			new Tuple(x, y, z, w);
	}

	@Given("^([a-zA-Z_][a-zA-Z0-9_]*) ← (vector|point|color)\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void v2Vector(String varName, String type, double x, double y, double z)
	{
		if ("vector".equals(type))
			vectorMap.put(varName, new Vector(x, y, z));
		if ("point".equals(type))
			pointMap.put(varName, new Point(x, y, z));
		if ("color".equals(type))
			colorMap.put(varName, new Color(x, y, z));
	}


	@When("^([a-zA-Z_][a-zA-Z0-9_]*) ← normalize\\(([a-zA-Z_][a-zA-Z0-9_]*)\\)$")
	public void v2Vector(String varName, String vectorName)
	{
		vectorMap.put(varName, vectorMap.get(vectorName).normalize());
	}



	@Then("^a\\.x = (-?\\d+\\.?\\d+)$")
	public void aX(double x)
	{
		Assert.assertEquals(x, a.getX(), Tuple.EPSILON);
	}

	@Then("^a\\.y = (-?\\d+\\.?\\d+)$")
	public void aY(double y)
	{
		Assert.assertEquals(y, a.getY(), Tuple.EPSILON);
	}

	@Then("^a\\.z = (-?\\d+\\.\\d+)$")
	public void aZ(double z)
	{
		Assert.assertEquals(z, a.getZ(), Tuple.EPSILON);
	}

	@Then("^a\\.w = (-?\\d+\\.?\\d+)$")
	public void aW(double w)
	{
		Assert.assertEquals(w, a.getW(), Tuple.EPSILON);
	}

	@Then("^a is a point$")
	public void aIsAPoint()
	{
		Assert.assertTrue(a instanceof Point);
	}

	@Then("^a is not a point$")
	public void aIsNotAPoint() throws Throwable
	{
		Assert.assertFalse(a instanceof Point);
	}

	@Then("^a is not a vector$")
	public void aIsNotAVector()
	{
		Assert.assertFalse(a instanceof Vector);
	}

	@Then("^a is a vector$")
	public void aIsAVector() throws Throwable
	{
		Assert.assertTrue(a instanceof Vector);
	}

	@Then("^(p[a-zA-Z0-9_]*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), 1\\)$")
	public void pEqualsTuple(String pointName, double x, double y, double z)
	{
		Point p2 = new Point(x, y, z);
		Assert.assertEquals(p2, pointMap.get(pointName));
	}

	@Then("^(v[a-zA-Z0-9_]*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), 0\\)$")
	public void vTuple(String vectorName, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, vectorMap.get(vectorName));
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void vEqualsVector(String vectorName, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, vectorMap.get(vectorName));
	}

	@Then("^(p[a-zA-Z0-9_]*) \\+ v2 = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), 1\\)$")
	public void pAddV_point(String pointName, double x, double y, double z)
	{
		Point expected = new Point(x, y, z);
		Point actual = pointMap.get(pointName).add(vectorMap.get("v2"));
		Assert.assertEquals(expected, actual);
	}

	@Then("^(p[a-zA-Z0-9_]*) \\- ([a-zA-Z_][a-zA-Z0-9_]*) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void pSubtractV_vector(String point1Name, String point2Name, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector actual = pointMap.get(point1Name).subtract(pointMap.get(point2Name));
		Assert.assertEquals(expected, actual);
	}

	@Then("^(p[a-zA-Z0-9_]*) \\- v = point\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void pSubtractV_point(String point1Name, double x, double y, double z)
	{
		Point expected = new Point(x, y, z);
		Point actual = pointMap.get(point1Name).subtract(vectorMap.get("v"));
		Assert.assertEquals(expected, actual);
	}

	@Then("^([vz][a-zA-Z0-9_]*) \\- ([vz][a-zA-Z0-9_]*) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void vSubtractV_vector(String v1Name, String v2Name, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector vector1 = vectorMap.get(v1Name);
		Vector vector2 = vectorMap.get(v2Name);
		Vector actual = vector1.subtract(vector2);
		Assert.assertEquals(expected, actual);
	}

	@Then("^-a = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void negativeTuple_Equals_Tuple(double x, double y, double z, double w)
	{
		Tuple expected = new Tuple(x, y, z, w);

		Tuple actual = a.negate();
		Assert.assertEquals(expected, actual);
	}

	@Then("^a (\\*|\\/) (-?\\d+\\.?\\d*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void aMultiply_Equals_Tuple(String operation, double multiply, double x, double y, double z, double w)
	{
		Tuple actual = operation.equals("*") ? a.multiply(multiply) :
			operation.equals("/") ? a.divide(multiply) :
			null;
		Assert.assertNotNull("Unexpected Operation " + operation, actual);
		Tuple expected = new Tuple(x, y, z, w);
		Assert.assertEquals(expected, actual);
	}

	@Then("^magnitude\\(([a-zA-Z_][a-zA-Z0-9_]*)\\) = (-?\\d+\\.?\\d*)$")
	public void magnitude_v(String vectorVariable, double expectedMagnitude)
	{
		Vector v = vectorMap.get(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("^magnitude\\(([a-zA-Z_][a-zA-Z0-9_]*)\\) = √(-?\\d+\\.?\\d*)$")
	public void sqrtMagnitude_v(String vectorVariable, double expectedMagnitudeSquared)
	{
		double expectedMagnitude = Math.sqrt(expectedMagnitudeSquared);
		Vector v = vectorMap.get(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("^normalize\\(([a-zA-Z_][a-zA-Z0-9_]*)\\) = (?:approximately )?vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void normalize_v_vector(String vectorVariable, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector actualNormalizedVector = vectorMap.get(vectorVariable).normalize();
		Assert.assertEquals(expected, actualNormalizedVector);
	}


	@Then("^dot\\(([a-zA-Z_][a-zA-Z0-9_]*), ([a-zA-Z_][a-zA-Z0-9_]*)\\) = (-?\\d+\\.?\\d*)$")
	public void vector_dot_vector(String vectorVariable, String otherVectorVariable, double expectedDot)
	{
		Vector v1 = vectorMap.get(vectorVariable);
		Vector v2 = vectorMap.get(otherVectorVariable);
		double actualDot = v1.dot(v2);
		Assert.assertEquals(expectedDot, actualDot, Tuple.EPSILON);
	}

	@Then("^cross\\(([a-zA-Z_][a-zA-Z0-9_]*), ([a-zA-Z_][a-zA-Z0-9_]*)\\) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void vector_cross_Vector(String vector1Name, String vector2Name, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Vector vector1 = vectorMap.get(vector1Name);
		Vector vector2 = vectorMap.get(vector2Name);
		Vector actualVector = vector1.cross(vector2);
		Assert.assertEquals(expectedVector, actualVector);
	}


	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.(red|green|blue) = (-?\\d+\\.?\\d*)$")
	public void colorAssert(String colorVarName, String colorName, double expectedColor)
	{
		Color color = colorMap.get(colorVarName);
		Double value = "red".equals(colorName) ? color.getRed() :
			"green".equals(colorName) ? color.getGreen() :
			"blue".equals(colorName) ? color.getBlue() :
			null;
		Assert.assertNotNull("Illegal color name " + colorName, value);
		Assert.assertEquals(expectedColor, value, Tuple.EPSILON);
	}

}
