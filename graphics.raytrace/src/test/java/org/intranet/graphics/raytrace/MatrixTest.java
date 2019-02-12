package org.intranet.graphics.raytrace;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest
{
	@Test
	public void testCreateMatrix4x4()
	{
		Matrix m = new Matrix(
			new double[] { 1, 2, 3, 4 },
			new double[] { 5.5, 6.5, 7.5, 8.5 },
			new double[] { 9, 10, 11, 12 },
			new double[] { 13.5, 14.5, 15.5, 16.5 }
		);

		Assert.assertEquals(1.0, m.get(0, 0), Tuple.EPSILON);
		Assert.assertEquals(4.0, m.get(0, 3), Tuple.EPSILON);
		Assert.assertEquals(5.5, m.get(1, 0), Tuple.EPSILON);
		Assert.assertEquals(7.5, m.get(1, 2), Tuple.EPSILON);
		Assert.assertEquals(11.0, m.get(2, 2), Tuple.EPSILON);
		Assert.assertEquals(13.5, m.get(3, 0), Tuple.EPSILON);
		Assert.assertEquals(15.5, m.get(3, 2), Tuple.EPSILON);
	}

	@Test
	public void testCreateMatrix3x3()
	{
		Matrix m = new Matrix(
			new double[] { -3, 5, 0 },
			new double[] { 1, -2, -7 },
			new double[] { 0, 1, 1 }
		);

		Assert.assertEquals(-3.0, m.get(0, 0), Tuple.EPSILON);
		Assert.assertEquals(-2.0, m.get(1, 1), Tuple.EPSILON);
		Assert.assertEquals(1.0, m.get(2, 2), Tuple.EPSILON);
	}

	@Test
	public void testCreateMatrix2x2()
	{
		Matrix m = new Matrix(
			new double[] { -3, 5 },
			new double[] { 1, -2 }
		);

		Assert.assertEquals(-3.0, m.get(0, 0), Tuple.EPSILON);
		Assert.assertEquals(5.0, m.get(0, 1), Tuple.EPSILON);
		Assert.assertEquals(1.0, m.get(1, 0), Tuple.EPSILON);
		Assert.assertEquals(-2.0, m.get(1, 1), Tuple.EPSILON);
	}

	@Test
	public void testMatricesEqual()
	{
		Matrix a = new Matrix(
			new double[] { 1, 2, 3, 4 },
			new double[] { 5, 6, 7, 8 },
			new double[] { 9, 8, 7, 6 },
			new double[] { 5, 4, 3, 2 }
		);
		Matrix b = new Matrix(
			new double[] { 1, 2, 3, 4 },
			new double[] { 5, 6, 7, 8 },
			new double[] { 9, 8, 7, 6 },
			new double[] { 5, 4, 3, 2 }
		);
		Assert.assertEquals(a, b);
	}

	@Test
	public void testMatricesNotEqual()
	{
		Matrix a = new Matrix(
			new double[] { 1, 2, 3, 4 },
			new double[] { 5, 6, 7, 8 },
			new double[] { 9, 8, 7, 6 },
			new double[] { 5, 4, 3, 2 }
		);
		Matrix b = new Matrix(
			new double[] { 2, 3, 4, 5 },
			new double[] { 6, 7, 8, 9 },
			new double[] { 8, 7, 6, 5 },
			new double[] { 4, 3, 2, 1 }
		);
		Assert.assertNotEquals(a, b);
	}

	@Test
	public void testMatrixMultiply()
	{
		Matrix a = new Matrix(
			new double[] { 1, 2, 3, 4 },
			new double[] { 5, 6, 7, 8 },
			new double[] { 9, 8, 7, 6 },
			new double[] { 5, 4, 3, 2 }
		);
		Matrix b = new Matrix(
			new double[] { -2, 1, 2, 3 },
			new double[] { 3, 2, 1, -1 },
			new double[] { 4, 3, 6, 5 },
			new double[] { 1, 2, 7, 8 }
		);
		Matrix expected = new Matrix(
			new double[] { 20, 22, 50, 48 },
			new double[] { 44, 54, 114, 108 },
			new double[] { 40, 58, 110, 102 },
			new double[] { 16, 26, 46, 42 }
		);
		Matrix multiplied = a.multiply(b);
		Assert.assertEquals(expected, multiplied);
	}

	@Test
	public void testMatrixMultiplyTuple()
	{
		Matrix a = new Matrix(
			new double[] { 1, 2, 3, 4 },
			new double[] { 2, 4, 4, 2 },
			new double[] { 8, 6, 4, 1 },
			new double[] { 0, 0, 0, 1 }
		);
		Point b = new Point(1, 2, 3);
		Point expected = new Point(18, 24, 33);
		Point multiplied = a.multiply(b);
		Assert.assertEquals(expected, multiplied);
	}
}
