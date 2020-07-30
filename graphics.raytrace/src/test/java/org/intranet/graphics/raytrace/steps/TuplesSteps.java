package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Given.Givens;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Then.Thens;
import io.cucumber.java.en.When;


public class TuplesSteps
	extends StepsParent
{
	public TuplesSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← {tuple}")
	public void aTuple(String tupleName, Tuple t)
	{
		if (t instanceof Point) data.put(tupleName, (Point)t);
		else if (t instanceof Vector) data.put(tupleName, (Vector)t);
		else data.put(tupleName, t);
	}

	@Then("{identifier} = {tuple}")
	public void pEqualsTuple(String pointName, Tuple expected)
	{
		Tuple actual = expected instanceof Point ? data.getPoint(pointName) :
			expected instanceof Vector ? data.getVector(pointName) :
			data.getTuple(pointName);
		Assert.assertEquals(expected, actual);
	}

	@Then("-{identifier} = {tuple}")
	public void negativeTuple_Equals_Tuple(String tupleName, Tuple expected)
	{
		Tuple a = data.getTuple(tupleName);
		Tuple actual = a.negate();
		Assert.assertEquals(expected, actual);
	}

	@Then("{identifier} {multiplyDivide} {dbl} = {tuple}")
	public void aMultiply_Equals_Tuple(String tupleName, String operation,
		double multiply, Tuple expected)
	{
		Tuple a = data.getTuple(tupleName);
		Tuple actual = operation.equals("*") ? a.multiply(multiply) :
			a.divide(multiply);
		Assert.assertNotNull("Unexpected Operation " + operation, actual);
		Assert.assertEquals(expected, actual);
	}



	@Given("{identifier} ← {point}")
	public void setIdentifierToPoint(String varName, Point p)
	{
		data.put(varName, p);
	}

	@Then("{identifier} = {point}")
	public void pEqualsPoint(String pointName, Point p)
	{
		Assert.assertEquals(p, data.getPoint(pointName));
	}

	@Then("{identifier} is a point")
	public void aIsAPoint(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertTrue(a instanceof Point);
	}

	@Then("{identifier} is not a point")
	public void aIsNotAPoint(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertFalse(a instanceof Point);
	}



	@Givens({@Given("{identifier} ← {vector}"),
		@Given("{identifier} ← {vectorNSS}"),
		@Given("{identifier} ← {vectorSSS}"),
		@Given("{identifier} ← {vectorSSN}")})
	public void setIdentifierToVector(String varName, Vector v)
	{
		data.put(varName, v);
	}

	@Thens({@Then("{identifier} = {vector}"),
		@Then("{identifier} = {vectorSSS}"),
		@Then("{identifier} = {vectorNsN}")})
	public void vEqualsVector(String vectorName, Vector expectedVector)
	{
		Vector actualVector = data.getVector(vectorName);
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier} is not a vector")
	public void aIsNotAVector(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertFalse(a instanceof Vector);
	}

	@Then("{identifier} is a vector")
	public void aIsAVector(String tupleName)
	{
		Tuple a = data.getTuple(tupleName);
		Assert.assertTrue(a instanceof Vector);
	}

	@When("{identifier} ← normalize\\({identifier})")
	public void v2Vector(String varName, String vectorName)
	{
		Vector vector = data.getVector(vectorName);
		data.put(varName, vector.normalize());
	}

	@When("{identifier} ← normalize\\({vector})")
	public void v2Vector(String varName, Vector vector)
	{
		data.put(varName, vector.normalize());
	}

	@When("{identifier} ← reflect\\({identifier}, {identifier})")
	public void rReflectVN(String reflectedVectorName, String bounceVectorName,
		String normalVectorName)
	{
		Vector bounceVector = data.getVector(bounceVectorName);
		Vector normalVector = data.getVector(normalVectorName);

		Vector reflectedVector = bounceVector.reflect(normalVector);
		data.put(reflectedVectorName, reflectedVector);
	}



	@Given("{identifier} ← {color}")
	public void setTupleType3(String varName, Color color)
	{
		data.put(varName, color);
	}

	@Then("{identifier} {plusMinusTimes} {identifier} = {color}")
	public void colorOperationColor_equals_Color(String color1Name,
		String operation, String color2Name, Color expected)
	{
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

	@Given("{identifier}.red = {dbl}")
	public void testRedValue(String varName, double expectedValue)
	{
		Color c = data.getColor(varName);
		double actualValue = c.getRed();
		Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
	}

	@Given("{identifier}.green = {dbl}")
	public void testGreenValue(String varName, double expectedValue)
	{
		Color c = data.getColor(varName);
		double actualValue = c.getGreen();
		Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
	}

	@Given("{identifier}.blue = {dbl}")
	public void testBlueValue(String varName, double expectedValue)
	{
		Color c = data.getColor(varName);
		double actualValue = c.getBlue();
		Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
	}


//	@Given("{identifier} ← vector\\({dbl}, (-?)√{dbl}\\/{dbl}, (-?)√{dbl}\\/{dbl})")
//	public void nVectorNumSqrSqr(String vectorName, double x,
//		String ySignStr, double yNum, double yDenom,
//		String zSignStr, double zNum, double zDenom)
//	{
//		int ySign = "-".contentEquals(ySignStr) ? -1 : 1;
//		int zSign = "-".contentEquals(zSignStr) ? -1 : 1;
//		Vector vector = new Vector(x, ySign * Math.sqrt(yNum) / yDenom,
//			zSign * Math.sqrt(zNum) / zDenom);
//		data.put(vectorName, vector);
//	}
//


//	@Then("^(v[a-zA-Z0-9_]*) = tuple\\(" + threeDoublesPattern + ", 0\\)$")
//	public void vTuple(String vectorName, double x, double y, double z)
//	{
//		Vector expectedVector = new Vector(x, y, z);
//		Assert.assertEquals(expectedVector, data.getVector(vectorName));
//	}

	@Then("{identifier} + {identifier} = {tuple}")
	public void pAddV_point(String pointName, String vectorName, Tuple expected)
	{
		Point point = data.getPoint(pointName);
		Vector vector = data.getVector(vectorName);
		Point actual = point.add(vector);
		Assert.assertEquals(expected, actual);
	}

	@Then("{identifier} - {identifier} = {vector}")
	public void pSubtractV_vector(String point1Name, String point2Name, Vector expected)
	{
		Point point1 = data.getPoint(point1Name);
		Point point2 = data.getPoint(point2Name);
		if (point1 != null && point2 != null)
		{
			Vector actual = point1.subtract(point2);
			Assert.assertEquals(expected, actual);
			return;
		}
		Vector v1 = data.getVector(point1Name);
		Vector v2 = data.getVector(point2Name);
		if (v1 != null && v2 != null)
		{
			Vector actual = v1.subtract(v2);
			Assert.assertEquals(expected, actual);
			return;
		}
		Assert.fail("Unknown types for " + point1Name + " and " + point2Name);
	}

	@Then("{identifier} - {identifier} = {point}")
	public void pSubtractV_point(String pointName, String vectorName, Point expected)
	{
		Point point = data.getPoint(pointName);
		Vector vector = data.getVector(vectorName);
		Point actual = point.subtract(vector);
		Assert.assertEquals(expected, actual);
	}

//	@Then("{identifier} - {identifier} = {vector}")
//	public void vSubtractV_vector(String v1Name, String v2Name, double x, double y, double z)
//	{
//		Vector expected = new Vector(x, y, z);
//		Vector vector1 = data.getVector(v1Name);
//		Vector vector2 = data.getVector(v2Name);
//		Vector actual = vector1.subtract(vector2);
//		Assert.assertEquals(expected, actual);
//	}

	@Then("magnitude\\({identifier}) = {dbl}")
	public void magnitude_v(String vectorVariable, double expectedMagnitude)
	{
		Vector v = data.getVector(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("magnitude\\({identifier}) = √{dbl}")
	public void sqrtMagnitude_v(String vectorVariable, double expectedMagnitudeSquared)
	{
		double expectedMagnitude = Math.sqrt(expectedMagnitudeSquared);
		Vector v = data.getVector(vectorVariable);
		double actualMagnitude = v.magnitude();
		Assert.assertEquals(expectedMagnitude, actualMagnitude, Tuple.EPSILON);
	}

	@Then("normalize\\({identifier}) = (approximately ){vector}")
	public void normalize_v_vector(String vectorVariable, Vector expected)
	{
		Vector vector = data.getVector(vectorVariable);
		Vector actualNormalizedVector = vector.normalize();
		Assert.assertEquals(expected, actualNormalizedVector);
	}


	@Then("dot\\({identifier}, {identifier}) = {dbl}")
	public void vector_dot_vector(String vectorVariable, String otherVectorVariable, double expectedDot)
	{
		Vector v1 = data.getVector(vectorVariable);
		Vector v2 = data.getVector(otherVectorVariable);
		double actualDot = v1.dot(v2);
		Assert.assertEquals(expectedDot, actualDot, Tuple.EPSILON);
	}

	@Then("cross\\({identifier}, {identifier}) = {vector}")
	public void vector_cross_Vector(String vector1Name, String vector2Name,
		Vector expectedVector)
	{
		Vector vector1 = data.getVector(vector1Name);
		Vector vector2 = data.getVector(vector2Name);
		Vector actualVector = vector1.cross(vector2);
		Assert.assertEquals(expectedVector, actualVector);
	}

	@Then("{identifier} * {dbl} = {color}")
	public void colorOperationDouble_equals_Color(String color1Name,
		double multiplyValue, Color expected)
	{
		Color c1 = data.getColor(color1Name);
		Color result = c1.multiply(multiplyValue);
		Assert.assertEquals(expected, result);
	}

	@Given("{identifier} ← normalize\\({identifier} - {identifier})")
	public void eyevNormalizeEyePt(String vectorName, String point1Name, String point2Name)
	{
		Point p1 = data.getPoint(point1Name);
		Point p2 = data.getPoint(point2Name);
		Vector v = p1.subtract(p2);
		data.put(vectorName, v);
	}

	@Given("{identifier} ← vector\\({identifier}.x, {identifier}.y, {identifier}.z)")
	public void normalvVectorPtXPtYPtZ(String vectorName, String pt1Name, String pt2Name, String pt3Name)
	{
		Point p1 = data.getPoint(pt1Name);
		Point p2 = data.getPoint(pt2Name);
		Point p3 = data.getPoint(pt3Name);
		Vector v = new Vector(p1.getX(), p2.getY(), p3.getZ());
		data.put(vectorName, v);
	}
}
