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
	private Map<String, Tuple> tupleMap = new HashMap<>();
	private Map<String, Point> pointMap = new HashMap<>();
	private Map<String, Vector> vectorMap = new HashMap<>();
	private Map<String, Color> colorMap = new HashMap<>();

	@Given("^([a-zA-Z_][a-zA-Z0-9_]*) ← tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void aTuple(String tupleName, double x, double y, double z, double w)
	{
		Tuple a = Tuple.dblEqual(1.0, w) ? new Point(x, y, z) :
			Tuple.dblEqual(0.0, w) ? new Vector(x, y, z) :
			new Tuple(x, y, z, w);
		tupleMap.put(tupleName, a);
	}

	@Given("^([a-zA-Z_][a-zA-Z0-9_]*) ← (vector|point|color)\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void setTupleType3(String varName, String type, double x, double y, double z)
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



	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.x = (-?\\d+\\.?\\d+)$")
	public void aX(String tupleName, double x)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertEquals(x, a.getX(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.y = (-?\\d+\\.?\\d+)$")
	public void aY(String tupleName, double y)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertEquals(y, a.getY(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.z = (-?\\d+\\.\\d+)$")
	public void aZ(String tupleName, double z)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertEquals(z, a.getZ(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.w = (-?\\d+\\.?\\d+)$")
	public void aW(String tupleName, double w)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertEquals(w, a.getW(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is a point$")
	public void aIsAPoint(String tupleName)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertTrue(a instanceof Point);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is not a point$")
	public void aIsNotAPoint(String tupleName)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertFalse(a instanceof Point);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is not a vector$")
	public void aIsNotAVector(String tupleName)
	{
		Tuple a = tupleMap.get(tupleName);
		Assert.assertFalse(a instanceof Vector);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is a vector$")
	public void aIsAVector(String tupleName)
	{
		Tuple a = tupleMap.get(tupleName);
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

	@Then("^-([a-zA-Z_][a-zA-Z0-9_]*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void negativeTuple_Equals_Tuple(String tupleName, double x, double y, double z, double w)
	{
		Tuple expected = new Tuple(x, y, z, w);

		Tuple a = tupleMap.get(tupleName);
		Tuple actual = a.negate();
		Assert.assertEquals(expected, actual);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) (\\*|\\/) (-?\\d+\\.?\\d*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void aMultiply_Equals_Tuple(String tupleName, String operation, double multiply, double x, double y, double z, double w)
	{
		Tuple a = tupleMap.get(tupleName);
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
	public void vector_cross_Vector(String vector1Name, String vector2Name,
		double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Vector vector1 = vectorMap.get(vector1Name);
		Vector vector2 = vectorMap.get(vector2Name);
		Vector actualVector = vector1.cross(vector2);
		Assert.assertEquals(expectedVector, actualVector);
	}


	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.(red|green|blue) = (-?\\d+\\.?\\d*)$")
	public void colorAssert(String colorVarName, String colorName,
		double expectedColor)
	{
		Color color = colorMap.get(colorVarName);
		Double value = "red".equals(colorName) ? color.getRed() :
			"green".equals(colorName) ? color.getGreen() :
			"blue".equals(colorName) ? color.getBlue() :
			null;
		Assert.assertNotNull("Illegal color name " + colorName, value);
		Assert.assertEquals(expectedColor, value, Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) (\\+|-|\\*) ([a-zA-Z_][a-zA-Z0-9_]*) = color\\((\\d+\\.?\\d*), (\\d+\\.?\\d*), (\\d+\\.?\\d*)\\)$")
	public void colorOperationColor_equals_Color(String color1Name, String operation, String color2Name,
		double red, double green, double blue)
	{
		Color expected = new Color(red, green, blue);
		Color c1 = colorMap.get(color1Name);
		Color c2 = colorMap.get(color2Name);
		Color result = "+".equals(operation) ? c1.add(c2) :
			"-".equals(operation) ? c1.subtract(c2) :
			"*".equals(operation) ? c1.multiply(c2) :
//			"/".equals(operation) ? c1.divide(c2) :
			null;
		Assert.assertNotNull("Unknown operation " + operation, result);
		Assert.assertEquals(expected, result);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) \\* (\\d+\\.?\\d*) = color\\((\\d+\\.?\\d*), (\\d+\\.?\\d*), (\\d+\\.?\\d*)\\)$")
	public void colorOperationDouble_equals_Color(String color1Name,
		double multiplyValue,
		double red, double green, double blue)
	{
		Color expected = new Color(red, green, blue);
		Color c1 = colorMap.get(color1Name);
		Color result = c1.multiply(multiplyValue);
		Assert.assertEquals(expected, result);
	}

}
