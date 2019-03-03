package org.intranet.graphics.raytrace;

import org.junit.Assert;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TuplesSteps
{
	private final TupleData data;

	public TuplesSteps(TupleData data)
	{
		this.data = data;
	}

	@Given("^([a-zA-Z_][a-zA-Z0-9_]*) ← tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void aTuple(String tupleName, double x, double y, double z, double w)
	{
		Tuple a = Tuple.dblEqual(1.0, w) ? new Point(x, y, z) :
			Tuple.dblEqual(0.0, w) ? new Vector(x, y, z) :
			new Tuple(x, y, z, w);
		data.put(tupleName, a);
	}

	@Given("^([a-zA-Z_][a-zA-Z0-9_]*) ← (vector|point|color)\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void setTupleType3(String varName, String type, double x, double y, double z)
	{
		if ("vector".equals(type))
			data.put(varName, new Vector(x, y, z));
		if ("point".equals(type))
			data.put(varName, new Point(x, y, z));
		if ("color".equals(type))
			data.put(varName, new Color(x, y, z));
	}


	@When("^([a-zA-Z_][a-zA-Z0-9_]*) ← normalize\\(([a-zA-Z_][a-zA-Z0-9_]*)\\)$")
	public void v2Vector(String varName, String vectorName)
	{
		data.put(varName, data.getVector(vectorName).normalize());
	}



	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.x = (-?\\d+\\.?\\d+)$")
	public void aX(String tupleName, double x)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertEquals(x, a.getX(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.y = (-?\\d+\\.?\\d+)$")
	public void aY(String tupleName, double y)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertEquals(y, a.getY(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.z = (-?\\d+\\.\\d+)$")
	public void aZ(String tupleName, double z)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertEquals(z, a.getZ(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.w = (-?\\d+\\.?\\d+)$")
	public void aW(String tupleName, double w)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertEquals(w, a.getW(), Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is a point$")
	public void aIsAPoint(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertTrue(a instanceof Point);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is not a point$")
	public void aIsNotAPoint(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertFalse(a instanceof Point);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is not a vector$")
	public void aIsNotAVector(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertFalse(a instanceof Vector);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) is a vector$")
	public void aIsAVector(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertTrue(a instanceof Vector);
	}

	@Then("^(p[a-zA-Z0-9_]*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), 1\\)$")
	public void pEqualsTuple(String pointName, double x, double y, double z)
	{
		Point p2 = new Point(x, y, z);
		Assert.assertEquals(p2, data.getPoint(pointName));
	}

	@Then("^(v[a-zA-Z0-9_]*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), 0\\)$")
	public void vTuple(String vectorName, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, data.getVector(vectorName));
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void vEqualsVector(String vectorName, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, data.getVector(vectorName));
	}

	@Then("^(p[a-zA-Z0-9_]*) \\+ v2 = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), 1\\)$")
	public void pAddV_point(String pointName, double x, double y, double z)
	{
		Point expected = new Point(x, y, z);
		Point point = data.getPoint(pointName);
		Vector vector = data.getVector("v2");
		Point actual = point.add(vector);
		Assert.assertEquals(expected, actual);
	}

	@Then("^(p[a-zA-Z0-9_]*) \\- ([a-zA-Z_][a-zA-Z0-9_]*) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void pSubtractV_vector(String point1Name, String point2Name, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Point point1 = data.getPoint(point1Name);
		Point point2 = data.getPoint(point2Name);
		Vector actual = point1.subtract(point2);
		Assert.assertEquals(expected, actual);
	}

	@Then("^(p[a-zA-Z0-9_]*) \\- v = point\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void pSubtractV_point(String point1Name, double x, double y, double z)
	{
		Point expected = new Point(x, y, z);
		Point point = data.getPoint(point1Name);
		Vector vector = data.getVector("v");
		Point actual = point.subtract(vector);
		Assert.assertEquals(expected, actual);
	}

	@Then("^([vz][a-zA-Z0-9_]*) \\- ([vz][a-zA-Z0-9_]*) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void vSubtractV_vector(String v1Name, String v2Name, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector vector1 = data.getVector(v1Name);
		Vector vector2 = data.getVector(v2Name);
		Vector actual = vector1.subtract(vector2);
		Assert.assertEquals(expected, actual);
	}

	@Then("^-([a-zA-Z_][a-zA-Z0-9_]*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void negativeTuple_Equals_Tuple(String tupleName, double x, double y, double z, double w)
	{
		Tuple expected = new Tuple(x, y, z, w);

		Tuple a = data.getTuple(tupleName);
		Tuple actual = a.negate();
		Assert.assertEquals(expected, actual);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9_]*) (\\*|\\/) (-?\\d+\\.?\\d*) = tuple\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void aMultiply_Equals_Tuple(String tupleName, String operation, double multiply, double x, double y, double z, double w)
	{
		Tuple a = data.getTuple(tupleName);
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
		Vector v = data.getVector(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("^magnitude\\(([a-zA-Z_][a-zA-Z0-9_]*)\\) = √(-?\\d+\\.?\\d*)$")
	public void sqrtMagnitude_v(String vectorVariable, double expectedMagnitudeSquared)
	{
		double expectedMagnitude = Math.sqrt(expectedMagnitudeSquared);
		Vector v = data.getVector(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("^normalize\\(([a-zA-Z_][a-zA-Z0-9_]*)\\) = (?:approximately )?vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void normalize_v_vector(String vectorVariable, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector vector = data.getVector(vectorVariable);
		Vector actualNormalizedVector = vector.normalize();
		Assert.assertEquals(expected, actualNormalizedVector);
	}


	@Then("^dot\\(([a-zA-Z_][a-zA-Z0-9_]*), ([a-zA-Z_][a-zA-Z0-9_]*)\\) = (-?\\d+\\.?\\d*)$")
	public void vector_dot_vector(String vectorVariable, String otherVectorVariable, double expectedDot)
	{
		Vector v1 = data.getVector(vectorVariable);
		Vector v2 = data.getVector(otherVectorVariable);
		double actualDot = v1.dot(v2);
		Assert.assertEquals(expectedDot, actualDot, Tuple.EPSILON);
	}

	@Then("^cross\\(([a-zA-Z_][a-zA-Z0-9_]*), ([a-zA-Z_][a-zA-Z0-9_]*)\\) = vector\\((-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*), (-?\\d+\\.?\\d*)\\)$")
	public void vector_cross_Vector(String vector1Name, String vector2Name,
		double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Vector vector1 = data.getVector(vector1Name);
		Vector vector2 = data.getVector(vector2Name);
		Vector actualVector = vector1.cross(vector2);
		Assert.assertEquals(expectedVector, actualVector);
	}


	@Then("^([a-zA-Z_][a-zA-Z0-9_]*)\\.(red|green|blue) = (-?\\d+\\.?\\d*)$")
	public void colorAssert(String colorVarName, String colorName,
		double expectedColor)
	{
		Color color = data.getColor(colorVarName);
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
		Color c1 = data.getColor(color1Name);
		Color c2 = data.getColor(color2Name);
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
		Color c1 = data.getColor(color1Name);
		Color result = c1.multiply(multiplyValue);
		Assert.assertEquals(expected, result);
	}

}
