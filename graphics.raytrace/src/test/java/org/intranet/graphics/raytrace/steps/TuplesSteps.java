package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class TuplesSteps
	extends StepsParent
{
	public TuplesSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("^" + wordPattern + " ← tuple\\(" + fourDoublesPattern + "\\)$")
	public void aTuple(String tupleName, double x, double y, double z, double w)
	{
		if (Tuple.dblEqual(1.0, w)) data.put(tupleName, new Point(x, y, z));
		else if (Tuple.dblEqual(0.0, w)) data.put(tupleName, new Vector(x, y, z));
		else data.put(tupleName, new Tuple(x, y, z, w));
	}

	@Given("^" + wordPattern + " ← (vector|point|color)\\(" + threeDoublesPattern + "\\)$")
	public void setTupleType3(String varName, String type, double x, double y, double z)
	{
		if ("vector".equals(type))
			data.put(varName, new Vector(x, y, z));
		if ("point".equals(type))
			data.put(varName, new Point(x, y, z));
		if ("color".equals(type))
			data.put(varName, new Color(x, y, z));
	}

	@Given(wordPattern + " ← vector\\(√" + doublePattern + "\\/" + doublePattern
		+ ", √" + doublePattern + "\\/" + doublePattern + ", " + doublePattern
		+ "\\)")
	public void nVector(String vectorName, double xNum, double xDenom,
		double yNum, double yDenom, double z)
	{
		data.put(vectorName,
			new Vector(Math.sqrt(xNum) / xDenom, Math.sqrt(yNum) / yDenom, z));
	}

	@Given(wordPattern + " ← vector\\(" + doublePattern + ", "
		+ "(-?)√" + doublePattern + "\\/" + doublePattern
		+ ", (-?)√" + doublePattern + "\\/" + doublePattern
		+ "\\)")
	public void nVectorNumSqrSqr(String vectorName, double x,
		String ySignStr, double yNum, double yDenom,
		String zSignStr, double zNum, double zDenom)
	{
		int ySign = "-".contentEquals(ySignStr) ? -1 : 1;
		int zSign = "-".contentEquals(zSignStr) ? -1 : 1;
		Vector vector = new Vector(x, ySign * Math.sqrt(yNum) / yDenom,
			zSign * Math.sqrt(zNum) / zDenom);
		data.put(vectorName, vector);
	}


	@When("^" + wordPattern + " ← normalize\\(" + wordPattern + "\\)$")
	public void v2Vector(String varName, String vectorName)
	{
		Vector vector = data.getVector(vectorName);
		data.put(varName, vector.normalize());
	}

	@When(wordPattern + " ← reflect\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void rReflectVN(String reflectedVectorName, String bounceVectorName,
		String normalVectorName)
	{
		Vector bounceVector = data.getVector(bounceVectorName);
		Vector normalVector = data.getVector(normalVectorName);

		Vector reflectedVector = bounceVector.reflect(normalVector);
		data.put(reflectedVectorName, reflectedVector);
	}


	@Then("^" + wordPattern + " is a point$")
	public void aIsAPoint(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertTrue(a instanceof Point);
	}

	@Then("^" + wordPattern + " is not a point$")
	public void aIsNotAPoint(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertFalse(a instanceof Point);
	}

	@Then("^" + wordPattern + " is not a vector$")
	public void aIsNotAVector(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertFalse(a instanceof Vector);
	}

	@Then("^" + wordPattern + " is a vector$")
	public void aIsAVector(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertTrue(a instanceof Vector);
	}

	@Then("^(p[a-zA-Z0-9_]*) = tuple\\(" + threeDoublesPattern + ", 1\\)$")
	public void pEqualsTuple(String pointName, double x, double y, double z)
	{
		Point p2 = new Point(x, y, z);
		Assert.assertEquals(p2, data.getPoint(pointName));
	}

	@Then("^" + wordPattern + " = point\\(" + threeDoublesPattern + "\\)$")
	public void pEqualsPoint(String pointName, double x, double y, double z)
	{
		Point p2 = new Point(x, y, z);
		Assert.assertEquals(p2, data.getPoint(pointName));
	}

	@Then("^(v[a-zA-Z0-9_]*) = tuple\\(" + threeDoublesPattern + ", 0\\)$")
	public void vTuple(String vectorName, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, data.getVector(vectorName));
	}

	@Then("^" + wordPattern + " = vector\\(" + threeDoublesPattern + "\\)$")
	public void vEqualsVector(String vectorName, double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, data.getVector(vectorName));
	}

	@Then("^(p[a-zA-Z0-9_]*) \\+ v2 = tuple\\(" + threeDoublesPattern + ", 1\\)$")
	public void pAddV_point(String pointName, double x, double y, double z)
	{
		Point expected = new Point(x, y, z);
		Point point = data.getPoint(pointName);
		Vector vector = data.getVector("v2");
		Point actual = point.add(vector);
		Assert.assertEquals(expected, actual);
	}

	@Then("^(p[a-zA-Z0-9_]*) \\- " + wordPattern + " = vector\\(" + threeDoublesPattern + "\\)$")
	public void pSubtractV_vector(String point1Name, String point2Name, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Point point1 = data.getPoint(point1Name);
		Point point2 = data.getPoint(point2Name);
		Vector actual = point1.subtract(point2);
		Assert.assertEquals(expected, actual);
	}

	@Then("^(p[a-zA-Z0-9_]*) \\- v = point\\(" + threeDoublesPattern + "\\)$")
	public void pSubtractV_point(String point1Name, double x, double y, double z)
	{
		Point expected = new Point(x, y, z);
		Point point = data.getPoint(point1Name);
		Vector vector = data.getVector("v");
		Point actual = point.subtract(vector);
		Assert.assertEquals(expected, actual);
	}

	@Then("^([vz][a-zA-Z0-9_]*) \\- ([vz][a-zA-Z0-9_]*) = vector\\(" + threeDoublesPattern + "\\)$")
	public void vSubtractV_vector(String v1Name, String v2Name, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector vector1 = data.getVector(v1Name);
		Vector vector2 = data.getVector(v2Name);
		Vector actual = vector1.subtract(vector2);
		Assert.assertEquals(expected, actual);
	}

	@Then("^-" + wordPattern + " = tuple\\(" + fourDoublesPattern + "\\)$")
	public void negativeTuple_Equals_Tuple(String tupleName, double x, double y, double z, double w)
	{
		Tuple expected = new Tuple(x, y, z, w);

		Tuple a = data.getTuple(tupleName);
		Tuple actual = a.negate();
		Assert.assertEquals(expected, actual);
	}

	@Then("^" + wordPattern + " (\\*|\\/) " + doublePattern + " = tuple\\(" + fourDoublesPattern + "\\)$")
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

	@Then("^magnitude\\(" + wordPattern + "\\) = " + doublePattern + "$")
	public void magnitude_v(String vectorVariable, double expectedMagnitude)
	{
		Vector v = data.getVector(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("^magnitude\\(" + wordPattern + "\\) = √" + doublePattern + "$")
	public void sqrtMagnitude_v(String vectorVariable, double expectedMagnitudeSquared)
	{
		double expectedMagnitude = Math.sqrt(expectedMagnitudeSquared);
		Vector v = data.getVector(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("^normalize\\(" + wordPattern + "\\) = (?:approximately )?vector\\(" + threeDoublesPattern + "\\)$")
	public void normalize_v_vector(String vectorVariable, double x, double y, double z)
	{
		Vector expected = new Vector(x, y, z);
		Vector vector = data.getVector(vectorVariable);
		Vector actualNormalizedVector = vector.normalize();
		Assert.assertEquals(expected, actualNormalizedVector);
	}


	@Then("^dot\\(" + wordPattern + ", " + wordPattern + "\\) = " + doublePattern + "$")
	public void vector_dot_vector(String vectorVariable, String otherVectorVariable, double expectedDot)
	{
		Vector v1 = data.getVector(vectorVariable);
		Vector v2 = data.getVector(otherVectorVariable);
		double actualDot = v1.dot(v2);
		Assert.assertEquals(expectedDot, actualDot, Tuple.EPSILON);
	}

	@Then("^cross\\(" + wordPattern + ", " + wordPattern + "\\) = vector\\(" + threeDoublesPattern + "\\)$")
	public void vector_cross_Vector(String vector1Name, String vector2Name,
		double x, double y, double z)
	{
		Vector expectedVector = new Vector(x, y, z);
		Vector vector1 = data.getVector(vector1Name);
		Vector vector2 = data.getVector(vector2Name);
		Vector actualVector = vector1.cross(vector2);
		Assert.assertEquals(expectedVector, actualVector);
	}


//	@Then("^" + wordPattern + "\\.(red|green|blue) = " + doublePattern + "$")
//	public void colorAssert(String colorVarName, String colorName,
//		double expectedColor)
//	{
//		Color color = data.getColor(colorVarName);
//		Double value = "red".equals(colorName) ? color.getRed() :
//			"green".equals(colorName) ? color.getGreen() :
//			"blue".equals(colorName) ? color.getBlue() :
//			null;
//		Assert.assertNotNull("Illegal color name " + colorName, value);
//		Assert.assertEquals(expectedColor, value, Tuple.EPSILON);
//	}

	@Then("^" + wordPattern + " (\\+|-|\\*) " + wordPattern + " = color\\(" + threeDoublesPattern + "\\)$")
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

	@Then("^" + wordPattern + " \\* " + doublePattern + " = color\\(" + threeDoublesPattern + "\\)$")
	public void colorOperationDouble_equals_Color(String color1Name,
		double multiplyValue,
		double red, double green, double blue)
	{
		Color expected = new Color(red, green, blue);
		Color c1 = data.getColor(color1Name);
		Color result = c1.multiply(multiplyValue);
		Assert.assertEquals(expected, result);
	}

	@Then("^" + wordPattern + " = color\\(" + threeDoublesPattern + "\\)$")
	public void color_equals_Color(String color1Name,
		double red, double green, double blue)
	{
		Color expected = new Color(red, green, blue);
		Color c1 = data.getColor(color1Name);
		Assert.assertEquals(expected, c1);
	}

}
