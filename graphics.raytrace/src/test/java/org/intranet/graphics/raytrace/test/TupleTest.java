package org.intranet.graphics.raytrace.test;

import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Tuple;
import org.intranet.graphics.raytrace.Vector;
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
	}

	@Test
	public void testTupleWithWzeroIsVector()
	{
		Tuple a = new Tuple(4.3, -4.2, 3.1, 0.0);
		Assert.assertEquals(4.3, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4.2, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3.1, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(0.0, a.getW(), Tuple.EPSILON);
	}

	@Test
	public void testPoint()
	{
		Point a = new Point(4, -4, 3);
		Assert.assertEquals(4, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(1.0, a.getW(), Tuple.EPSILON);
	}

	@Test
	public void testVector()
	{
		Vector a = new Vector(4, -4, 3);
		Assert.assertEquals(4, a.getX(), Tuple.EPSILON);
		Assert.assertEquals(-4, a.getY(), Tuple.EPSILON);
		Assert.assertEquals(3, a.getZ(), Tuple.EPSILON);
		Assert.assertEquals(0.0, a.getW(), Tuple.EPSILON);
	}

	@Test
	public void testAdd()
	{
		Point a1 = new Point(3, -2, 5);
		Vector a2 = new Vector(-2, 3, 1);
		Point sum = a1.add(a2);
		Point expectedSum = new Point(1, 1, 6);
		Assert.assertEquals(expectedSum, sum);
	}

	@Test
	public void testSubtractTwoPoints()
	{
		Point a1 = new Point(3, 2, 1);
		Point a2 = new Point(5, 6, 7);
		Vector diff = a1.subtract(a2);
		Vector expectedDiff = new Vector(-2, -4, -6);
		Assert.assertEquals(expectedDiff, diff);
	}

	@Test
	public void testSubtractVectorFromPoint()
	{
		Point a1 = new Point(3, 2, 1);
		Vector a2 = new Vector(5, 6, 7);
		Point diff = a1.subtract(a2);
		Point expectedDiff = new Point(-2, -4, -6);
		Assert.assertEquals(expectedDiff, diff);
	}

	@Test
	public void testSubtractVectorFromVector()
	{
		Vector a1 = new Vector(3, 2, 1);
		Vector a2 = new Vector(5, 6, 7);
		Vector diff = a1.subtract(a2);
		Vector expectedDiff = new Vector(-2, -4, -6);
		Assert.assertEquals(expectedDiff, diff);
	}

	@Test
	public void testSubtractVectorFromZero()
	{
		Vector v = new Vector(1, -2, 3);
		Vector negative = v.negate();
		Vector expectedNegative = new Vector(-1, 2, -3);
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
		Tuple v = new Vector(1, 0, 0);
		double magnitude = v.magnitude();
		Assert.assertEquals(1.0, magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitudeUnitY()
	{
		Tuple v = new Vector(0, 1, 0);
		double magnitude = v.magnitude();
		Assert.assertEquals(1.0, magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitudeUnitZ()
	{
		Tuple v = new Vector(0, 0, 1);
		double magnitude = v.magnitude();
		Assert.assertEquals(1.0, magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitude123()
	{
		Tuple v = new Vector(1, 2, 3);
		double magnitude = v.magnitude();
		Assert.assertEquals(Tuple.magnitude(1, 2, 3), magnitude, Tuple.EPSILON);
	}

	@Test
	public void testMagnitudeNegative123()
	{
		Tuple v = new Vector(-1, -2, -3);
		double magnitude = v.magnitude();
		Assert.assertEquals(Tuple.magnitude(-1, -2, -3), magnitude, Tuple.EPSILON);
	}

	@Test
	public void testNormalize4to1()
	{
		Tuple v = new Vector(4, 0, 0);
		Tuple normalized = v.normalize();
		Assert.assertEquals(new Vector(1, 0, 0), normalized);
	}

	@Test
	public void testNormalize123()
	{
		Tuple v = new Vector(1, 2, 3);
		Tuple normalized = v.normalize();
		Assert.assertEquals(new Vector(0.26726124, 0.53452248, 0.80178373), normalized);
	}

	@Test
	public void testNormalizedMagnitude()
	{
		Tuple v = new Vector(1, 2, 3);
		Tuple normalized = v.normalize();
		Assert.assertEquals(1.0, normalized.magnitude(), Tuple.EPSILON);
	}

	@Test
	public void testDotProduct()
	{
		Tuple a = new Vector(1, 2, 3);
		Tuple b = new Vector(2, 3, 4);
		double dot = a.dot(b);
		Assert.assertEquals(20.0, dot, Tuple.EPSILON);
	}

	@Test
	public void testCrossProduct()
	{
		Vector a = new Vector(1, 2, 3);
		Vector b = new Vector(2, 3, 4);
		Tuple aCrossB = a.cross(b);
		Assert.assertEquals(new Vector(-1, 2, -1), aCrossB);
		Tuple bCrossA = b.cross(a);
		Assert.assertEquals(new Vector(1, -2, 1), bCrossA);
	}
}
