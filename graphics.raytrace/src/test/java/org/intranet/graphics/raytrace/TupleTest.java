package org.intranet.graphics.raytrace;

import org.junit.Assert;
import org.junit.Test;

public class TupleTest
{
	@Test
	public void testTupleWithWoneIsPoint()
	{
		Tuple a = new Tuple(4.3, -4.2, 3.1, 1.0);
		Assert.assertEquals(4.3, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4.2, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3.1, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(1.0, a.getW(), Tuple.EPSILON);
		Assert.assertTrue("isPoint should be true", a.isPoint());
		Assert.assertFalse("isVector should be false", a.isVector());
	}

	@Test
	public void testTupleWithWzeroIsVector()
	{
		Tuple a = new Tuple(4.3, -4.2, 3.1, 0.0);
		Assert.assertEquals(4.3, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4.2, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3.1, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(0.0, a.getW(), Tuple.EPSILON);
		Assert.assertFalse("isPoint should be false", a.isPoint());
		Assert.assertTrue("isVector should be true", a.isVector());
	}

	@Test
	public void testPoint()
	{
		Tuple a = Tuple.point(4, -4, 3);
		Assert.assertEquals(4, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(1.0, a.getW(), Tuple.EPSILON);
		Assert.assertTrue("isPoint should be true", a.isPoint());
		Assert.assertFalse("isVector should be false", a.isVector());
	}

	@Test
	public void testVector()
	{
		Tuple a = Tuple.vector(4, -4, 3);
		Assert.assertEquals(4, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(0.0, a.getW(), Tuple.EPSILON);
		Assert.assertFalse("isPoint should be false", a.isPoint());
		Assert.assertTrue("isVector should be true", a.isVector());
	}

	@Test
	public void testAdd()
	{
		Tuple a1 = Tuple.point(3, -2, 5);
		Tuple a2 = Tuple.vector(-2, 3, 1);
		Tuple sum = a1.add(a2);
		Tuple expectedSum = Tuple.point(1, 1, 6);
		Assert.assertEquals(expectedSum, sum);
	}

	@Test
	public void testSubtractTwoPoints()
	{
		Tuple a1 = Tuple.point(3, 2, 1);
		Tuple a2 = Tuple.point(5, 6, 7);
		Tuple diff = a1.subtract(a2);
		Tuple expectedDiff = Tuple.vector(-2, -4, -6);
		Assert.assertEquals(expectedDiff, diff);
	}

	@Test
	public void testSubtractVectorFromPoint()
	{
		Tuple a1 = Tuple.point(3, 2, 1);
		Tuple a2 = Tuple.vector(5, 6, 7);
		Tuple diff = a1.subtract(a2);
		Tuple expectedDiff = Tuple.point(-2, -4, -6);
		Assert.assertEquals(expectedDiff, diff);
	}

	@Test
	public void testSubtractVectorFromVector()
	{
		Tuple a1 = Tuple.vector(3, 2, 1);
		Tuple a2 = Tuple.vector(5, 6, 7);
		Tuple diff = a1.subtract(a2);
		Tuple expectedDiff = Tuple.vector(-2, -4, -6);
		Assert.assertEquals(expectedDiff, diff);
	}

	@Test
	public void testSubtractVectorFromZero()
	{
		Tuple v = Tuple.vector(1, -2, 3);
		Tuple negative = v.negate();
		Tuple expectedNegative = Tuple.vector(-1, 2, -3);
		Assert.assertEquals(expectedNegative, negative);
	}

	@Test
	public void testNegate()
	{
		Tuple v = new Tuple(1, -2, 3, -4);
		Tuple negative = v.negate();
		Tuple expectedNegative = new Tuple(-1, 2, -3, 4);
		Assert.assertEquals(expectedNegative, negative);
	}

	@Test
	public void testMultiplyScalar()
	{
		Tuple v = new Tuple(1, -2, 3, -4);
		Tuple negative = v.multiply(3.5);
		Tuple expectedNegative = new Tuple(3.5, -7, 10.5, -14);
		Assert.assertEquals(expectedNegative, negative);
	}

	@Test
	public void testMultiplyFraction()
	{
		Tuple v = new Tuple(1, -2, 3, -4);
		Tuple negative = v.multiply(0.5);
		Tuple expectedNegative = new Tuple(0.5, -1, 1.5, -2);
		Assert.assertEquals(expectedNegative, negative);
	}

	@Test
	public void testDivide()
	{
		Tuple v = new Tuple(1, -2, 3, -4);
		Tuple negative = v.divide(2);
		Tuple expectedNegative = new Tuple(0.5, -1, 1.5, -2);
		Assert.assertEquals(expectedNegative, negative);
	}

	@Test
	public void testMagnitudeUnitX()
	{
		Tuple v = Tuple.vector(1, 0, 0);
		double magnitude = v.magnitude();
		Assert.assertEquals(1.0, magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitudeUnitY()
	{
		Tuple v = Tuple.vector(0, 1, 0);
		double magnitude = v.magnitude();
		Assert.assertEquals(1.0, magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitudeUnitZ()
	{
		Tuple v = Tuple.vector(0, 0, 1);
		double magnitude = v.magnitude();
		Assert.assertEquals(1.0, magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitude123()
	{
		Tuple v = Tuple.vector(1, 2, 3);
		double magnitude = v.magnitude();
		Assert.assertEquals(Tuple.magnitude(1, 2, 3), magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitudeNegative123()
	{
		Tuple v = Tuple.vector(-1, -2, -3);
		double magnitude = v.magnitude();
		Assert.assertEquals(Tuple.magnitude(-1, -2, -3), magnitude, Tuple.EPSILON);
	}

	@Test
	public void testNormalize4to1()
	{
		Tuple v = Tuple.vector(4, 0, 0);
		Tuple normalized = v.normalize();
		Assert.assertEquals(Tuple.vector(1, 0, 0), normalized);
	}

	@Test
	public void testNormalize123()
	{
		Tuple v = Tuple.vector(1, 2, 3);
		Tuple normalized = v.normalize();
		Assert.assertEquals(Tuple.vector(0.26726124, 0.53452248, 0.80178373), normalized);
	}

	@Test
	public void testNormalizedMagnitude()
	{
		Tuple v = Tuple.vector(1, 2, 3);
		Tuple normalized = v.normalize();
		Assert.assertEquals(1.0, normalized.magnitude(), Tuple.EPSILON);
	}

	@Test
	public void testDotProduct()
	{
		Tuple a = Tuple.vector(1, 2, 3);
		Tuple b = Tuple.vector(2, 3, 4);
		double dot = a.dot(b);
		Assert.assertEquals(20.0, dot, Tuple.EPSILON);
	}

	@Test
	public void testCrossProduct()
	{
		Tuple a = Tuple.vector(1, 2, 3);
		Tuple b = Tuple.vector(2, 3, 4);
		Tuple aCrossB = a.cross(b);
		Assert.assertEquals(Tuple.vector(-1, 2, -1), aCrossB);
		Tuple bCrossA = b.cross(a);
		Assert.assertEquals(Tuple.vector(1, -2, 1), bCrossA);
	}
}
