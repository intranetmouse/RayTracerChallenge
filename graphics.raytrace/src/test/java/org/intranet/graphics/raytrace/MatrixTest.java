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

	@Test
	public void testMatrixMultiplyIdentiy()
	{
		Matrix a = new Matrix(
			new double[] { 0, 1, 2, 4 },
			new double[] { 1, 2, 4, 8 },
			new double[] { 2, 4, 8, 16 },
			new double[] { 4, 8, 16, 32 }
		);
		Matrix ident = Matrix.identity(4);
		Matrix result = a.multiply(ident);

		Assert.assertEquals(a, result);
	}

	@Test
	public void testIdentiyMatrixMultiplyPoint()
	{
		Point t = new Point(1, 2, 3, 4);
		Matrix ident = Matrix.identity(4);
		Point result = ident.multiply(t);

		Assert.assertEquals(t, result);
	}


	@Test
	public void testMatrixTranspose()
	{
		Matrix a = new Matrix(
			new double[] { 0, 9, 3, 0 },
			new double[] { 9, 8, 0, 8 },
			new double[] { 1, 8, 5, 3 },
			new double[] { 0, 0, 5, 8 }
		);
		Matrix expected = new Matrix(
			new double[] { 0, 9, 1, 0 },
			new double[] { 9, 8, 8, 0 },
			new double[] { 3, 0, 5, 5 },
			new double[] { 0, 8, 3, 8 }
		);
		Matrix transposed = a.transpose();
		Assert.assertEquals(expected, transposed);
	}

	@Test
	public void testMatrixTransposeIdentity()
	{
		Matrix ident = Matrix.identity(4);
		Matrix transposed = ident.transpose();
		Assert.assertEquals(ident, transposed);
	}

	@Test
	public void testMatrixDeterminant2x2()
	{
		Matrix a = new Matrix(
			new double[] { 1, 5 },
			new double[] { -3, 2 }
		);
		double determinant = a.determinant();
		Assert.assertEquals(17.0, determinant, Tuple.EPSILON);
	}

	@Test
	public void testMatrixSubmatrix3x3To2x2()
	{
		Matrix a = new Matrix(
			new double[] { 1, 5, 0 },
			new double[] { -3, 2, 7 },
			new double[] { 0, 6, -3 }
		);
		Matrix expected = new Matrix(
			new double[] { -3, 2 },
			new double[] { 0, 6 }
		);
		Matrix sub = a.submatrix(0, 2);
		Assert.assertEquals(expected, sub);
	}

	@Test
	public void testMatrixSubmatrix4x4To3x3()
	{
		Matrix a = new Matrix(
			new double[] { -6, 1, 1, 6 },
			new double[] { -8, 5, 8, 6 },
			new double[] { -1, 0, 8, 2 },
			new double[] { -7, 1, -1, 1 }
		);
		Matrix expected = new Matrix(
			new double[] { -6, 1, 6 },
			new double[] { -8, 8, 6 },
			new double[] { -7, -1, 1 }
		);
		Matrix sub = a.submatrix(2, 1);
		Assert.assertEquals(expected, sub);
	}

	@Test
	public void testMatrixMinor3x3()
	{
		Matrix a = new Matrix(
			new double[] { 3, 5, 0 },
			new double[] { 2, -1, -7 },
			new double[] { 6, -1, 5 }
		);
		Matrix b = a.submatrix(1, 0);
		double bdeterminant = b.determinant();

		Assert.assertEquals(25, bdeterminant, Tuple.EPSILON);
		Assert.assertEquals(25, a.minor(1, 0), Tuple.EPSILON);
	}

	@Test
	public void testMatrixCofactor3x3()
	{
		Matrix a = new Matrix(
			new double[] { 3, 5, 0 },
			new double[] { 2, -1, -7 },
			new double[] { 6, -1, 5 }
		);
		double a00minor = a.minor(0, 0);
		double a00cofactor = a.cofactor(0, 0);
		double a10minor = a.minor(1, 0);
		double a10cofactor = a.cofactor(1, 0);

		Assert.assertEquals(-12, a00minor, Tuple.EPSILON);
		Assert.assertEquals(-12, a00cofactor, Tuple.EPSILON);
		Assert.assertEquals(25, a10minor, Tuple.EPSILON);
		Assert.assertEquals(-25, a10cofactor, Tuple.EPSILON);
	}

	@Test
	public void testMatrixDeterminant3x3()
	{
		Matrix a = new Matrix(
			new double[] { 1, 2, 6 },
			new double[] { -5, 8, -4 },
			new double[] { 2, 6, 4 }
		);

		Assert.assertEquals(56, a.cofactor(0, 0), Tuple.EPSILON);
		Assert.assertEquals(12, a.cofactor(0, 1), Tuple.EPSILON);
		Assert.assertEquals(-46, a.cofactor(0, 2), Tuple.EPSILON);
	}

	@Test
	public void testMatrixDeterminant4x4()
	{
		Matrix a = new Matrix(
			new double[] { -2, -8, 3, 5 },
			new double[] { -3, 1, 7, 3 },
			new double[] { 1, 2, -9, 6 },
			new double[] { -6, 7, 7, -9 }
		);

		Assert.assertEquals(690, a.cofactor(0, 0), Tuple.EPSILON);
		Assert.assertEquals(447, a.cofactor(0, 1), Tuple.EPSILON);
		Assert.assertEquals(210, a.cofactor(0, 2), Tuple.EPSILON);
		Assert.assertEquals(51, a.cofactor(0, 3), Tuple.EPSILON);
		Assert.assertEquals(-4071, a.determinant(), Tuple.EPSILON);
	}

	@Test
	public void testMatrixInvertible()
	{
		Matrix a = new Matrix(
			new double[] { 6, 4, 4, 4 },
			new double[] { 5, 5, 7, 6 },
			new double[] { 4, -9, 3, -7 },
			new double[] { 9, 1, 7, -6 }
		);

		Assert.assertEquals(-2120, a.determinant(), Tuple.EPSILON);
		Assert.assertTrue(a.isInvertible());
	}

	@Test
	public void testMatrixNoninvertible()
	{
		Matrix a = new Matrix(
			new double[] { -4, 2, -2, 3 },
			new double[] { 9, 6, 2, 6 },
			new double[] { 0, -5, 1, -5 },
			new double[] { 0, 0, 0, 0 }
		);

		Assert.assertEquals(0, a.determinant(), Tuple.EPSILON);
		Assert.assertFalse(a.isInvertible());
	}

	@Test
	public void testMatrixInvert()
	{
		Matrix a = new Matrix(
			new double[] { -5, 2, 6, -8 },
			new double[] { 1, -5, 1, 8 },
			new double[] { 7, 7, -6, -7 },
			new double[] { 1, -3, 7, 4 }
		);
		Matrix b = a.inverse();
		Matrix expectedB = new Matrix(
			new double[] { 0.21805, 0.45113, 0.24060, -0.04511 },
			new double[] { -0.80827, -1.45677, -0.44361, 0.52068 },
			new double[] { -0.07895, -0.22368, -0.05263, 0.19737 },
			new double[] { -0.52256, -0.81391, -0.30075, 0.30639 }
		);
System.out.println("        b="+b);
System.out.println("expectedB="+expectedB);
		Assert.assertEquals(532.0, a.determinant(), Tuple.EPSILON);
		Assert.assertEquals(-160, a.cofactor(2, 3), Tuple.EPSILON);
		Assert.assertEquals(-160/532.0, b.get(3, 2), Tuple.EPSILON);
		Assert.assertEquals(105, a.cofactor(3, 2), Tuple.EPSILON);
		Assert.assertEquals(105/532.0, b.get(2, 3), Tuple.EPSILON);
		Assert.assertEquals(expectedB, b);
	}

	@Test
	public void testMatrixInvert2()
	{
		Matrix a = new Matrix(
			new double[] { 8, -5, 9, 2 },
			new double[] { 7, 5, 6, 1 },
			new double[] { -6, 0, 9, 6 },
			new double[] { -3, 0, -9, -4 }
		);
		Matrix b = a.inverse();
		Matrix expectedB = new Matrix(
			new double[] { -0.15385, -0.15385, -0.28205, -0.53846 },
			new double[] { -0.07692, 0.12308, 0.02564, 0.03077 },
			new double[] { 0.35897, 0.35897, 0.43590, 0.92308 },
			new double[] { -0.69231, -0.69231, -0.76923, -1.92308 }
		);

		Assert.assertEquals(expectedB, b);
	}

	@Test
	public void testMatrixInvert3()
	{
		Matrix a = new Matrix(
			new double[] { 9, 3, 0, 9 },
			new double[] { -5, -2, -6, -3 },
			new double[] { -4, 9, 6, 4 },
			new double[] { -7, 6, 6, 2 }
		);
		Matrix b = a.inverse();
		Matrix expectedB = new Matrix(
			new double[] { -0.04074, -0.07778, 0.14444, -0.22222 },
			new double[] { -0.07778, 0.03333, 0.36667, -0.33333 },
			new double[] { -0.02901, -0.14630, -0.10926, 0.12963 },
			new double[] { 0.17778, 0.06667, -0.26667, 0.33333 }
		);

		Assert.assertEquals(expectedB, b);
	}

	@Test
	public void testMatrixTranslationPoint()
	{
		Matrix a = Matrix.newTranslation(5, -3, 2);
		Point p = new Point(-3, 4, 5);
		Point expectedPoint = new Point(2, 1, 7);

		Point result = a.multiply(p);
		Assert.assertEquals(expectedPoint, result);
	}

	@Test
	public void testMatrixTranslationInversePoint()
	{
		Matrix a = Matrix.newTranslation(5, -3, 2);
		Matrix inv = a.inverse();
		Point p = new Point(-3, 4, 5);
		Point expectedPoint = new Point(-8, 7, 3);

		Point result = inv.multiply(p);
		Assert.assertEquals(expectedPoint, result);
	}

	@Test
	public void testMatrixTranslationVector()
	{
		Matrix a = Matrix.newTranslation(5, -3, 2);
		Vector v = new Vector(-3, 4, 5);

		Vector result = a.multiply(v);
		Assert.assertEquals(v, result);
	}

	@Test
	public void testMatrixScalePoint()
	{
		Matrix a = Matrix.newScaling(2, 3, 4);
		Point pt = new Point(-4, 6, 8);
		Point expectedScaledPt = new Point(-8, 18, 32);

		Point scaledPt = a.multiply(pt);
		Assert.assertEquals(expectedScaledPt, scaledPt);
	}

	@Test
	public void testMatrixScaleVector()
	{
		Matrix a = Matrix.newScaling(2, 3, 4);
		Vector v = new Vector(-4, 6, 8);
		Vector expectedScaledVector = new Vector(-8, 18, 32);

		Vector scaledVector = a.multiply(v);
		Assert.assertEquals(expectedScaledVector, scaledVector);
	}

	@Test
	public void testMatrixInverseScaleVector()
	{
		Matrix a = Matrix.newScaling(2, 3, 4);
		Matrix inv = a.inverse();
		Vector v = new Vector(-4, 6, 8);
		Vector expectedScaledVector = new Vector(-2, 2, 2);

		Vector scaledVector = inv.multiply(v);
		Assert.assertEquals(expectedScaledVector, scaledVector);
	}

	@Test
	public void testMatrixReflectPoint()
	{
		Matrix a = Matrix.newScaling(-1, 1, 1);
		Point pt = new Point(2, 3, 4);
		Point expectedScaledPt = new Point(-2, 3, 4);

		Point scaledPt = a.multiply(pt);
		Assert.assertEquals(expectedScaledPt, scaledPt);
	}

	@Test
	public void testMatrixRotationXPoint()
	{
		Point p = new Point(0, 1, 0);

		Matrix halfQuarterRot = Matrix.newRotationX(Math.PI / 4);
		double sqrt2Div2 = Math.sqrt(2)/2;
		Point expectedHalfQuarterPt = new Point(0, sqrt2Div2, sqrt2Div2);
		Point halfQuarterRotPt = halfQuarterRot.multiply(p);
		Assert.assertEquals(expectedHalfQuarterPt, halfQuarterRotPt);

		Matrix fullQuarterRot = Matrix.newRotationX(Math.PI / 2);
		Point expectedFullQuarterPt = new Point(0, 0, 1);
		Point fullQuarterRotPt = fullQuarterRot.multiply(p);
		Assert.assertEquals(expectedFullQuarterPt, fullQuarterRotPt);
	}

	@Test
	public void testMatrixInverseRotationXPoint()
	{
		Point p = new Point(0, 1, 0);

		Matrix halfQuarterRot = Matrix.newRotationX(Math.PI / 4);
		Matrix halfQuarterRotInv = halfQuarterRot.inverse();

		double sqrt2Div2 = Math.sqrt(2)/2;
		Point expectedHalfQuarterPt = new Point(0, sqrt2Div2, -sqrt2Div2);
		Point halfQuarterRotPt = halfQuarterRotInv.multiply(p);
		Assert.assertEquals(expectedHalfQuarterPt, halfQuarterRotPt);
	}

	@Test
	public void testMatrixRotationYPoint()
	{
		Point p = new Point(0, 0, 1);

		double sqrt2Div2 = Math.sqrt(2)/2;

		Matrix halfQuarterRot = Matrix.newRotationY(Math.PI / 4);
		Point expectedHalfQuarterPt = new Point(sqrt2Div2, 0, sqrt2Div2);
		Point halfQuarterRotPt = halfQuarterRot.multiply(p);
		Assert.assertEquals(expectedHalfQuarterPt, halfQuarterRotPt);

		Matrix fullQuarterRot = Matrix.newRotationY(Math.PI / 2);
		Point expectedFullQuarterPt = new Point(1, 0, 0);
		Point fullQuarterRotPt = fullQuarterRot.multiply(p);
		Assert.assertEquals(expectedFullQuarterPt, fullQuarterRotPt);
	}

	@Test
	public void testMatrixRotationZPoint()
	{
		Point p = new Point(0, 1, 0);

		double sqrt2Div2 = Math.sqrt(2)/2;

		Matrix halfQuarterRot = Matrix.newRotationZ(Math.PI / 4);
		Point expectedHalfQuarterPt = new Point(-sqrt2Div2, sqrt2Div2, 0);
		Point halfQuarterRotPt = halfQuarterRot.multiply(p);
		Assert.assertEquals(expectedHalfQuarterPt, halfQuarterRotPt);

		Matrix fullQuarterRot = Matrix.newRotationZ(Math.PI / 2);
		Point expectedFullQuarterPt = new Point(-1, 0, 0);
		Point fullQuarterRotPt = fullQuarterRot.multiply(p);
		Assert.assertEquals(expectedFullQuarterPt, fullQuarterRotPt);
	}

	@Test
	public void testMatrixShearingXtoY()
	{
		Matrix shearMtx = Matrix.shearing(1, 0, 0, 0, 0, 0);
		Point p = new Point(2, 3, 4);

		Point expectedPt = new Point(5, 3, 4);
		Point shearedPt = shearMtx.multiply(p);
		Assert.assertEquals(expectedPt, shearedPt);
	}

	@Test
	public void testMatrixShearingXtoZ()
	{
		Matrix shearMtx = Matrix.shearing(0, 1, 0, 0, 0, 0);
		Point p = new Point(2, 3, 4);

		Point expectedPt = new Point(6, 3, 4);
		Point shearedPt = shearMtx.multiply(p);
		Assert.assertEquals(expectedPt, shearedPt);
	}

	@Test
	public void testMatrixShearingYtoX()
	{
		Matrix shearMtx = Matrix.shearing(0, 0, 1, 0, 0, 0);
		Point p = new Point(2, 3, 4);

		Point expectedPt = new Point(2, 5, 4);
		Point shearedPt = shearMtx.multiply(p);
		Assert.assertEquals(expectedPt, shearedPt);
	}

	@Test
	public void testMatrixShearingYtoZ()
	{
		Matrix shearMtx = Matrix.shearing(0, 0, 0, 1, 0, 0);
		Point p = new Point(2, 3, 4);

		Point expectedPt = new Point(2, 7, 4);
		Point shearedPt = shearMtx.multiply(p);
		Assert.assertEquals(expectedPt, shearedPt);
	}

	@Test
	public void testMatrixShearingZtoX()
	{
		Matrix shearMtx = Matrix.shearing(0, 0, 0, 0, 1, 0);
		Point p = new Point(2, 3, 4);

		Point expectedPt = new Point(2, 3, 6);
		Point shearedPt = shearMtx.multiply(p);
		Assert.assertEquals(expectedPt, shearedPt);
	}

	@Test
	public void testMatrixShearingZtoY()
	{
		Matrix shearMtx = Matrix.shearing(0, 0, 0, 0, 0, 1);
		Point p = new Point(2, 3, 4);

		Point expectedPt = new Point(2, 3, 7);
		Point shearedPt = shearMtx.multiply(p);
		Assert.assertEquals(expectedPt, shearedPt);
	}

	@Test
	public void testIndividualTransformationsInSequence()
	{
		Point p = new Point(1, 0, 1);
		Matrix a = Matrix.newRotationX(Math.PI / 2);
		Matrix b = Matrix.newScaling(5, 5, 5);
		Matrix c = Matrix.newTranslation(10, 5, 7);

		Point p2 = a.multiply(p);
		Assert.assertEquals(new Point(1, -1, 0), p2);

		Point p3 = b.multiply(p2);
		Assert.assertEquals(new Point(5, -5, 0), p3);

		Point p4 = c.multiply(p3);
		Assert.assertEquals(new Point(15, 0, 7), p4);
	}

	@Test
	public void testChainedTransformationsInReverseOrder()
	{
		Point p = new Point(1, 0, 1);
		Matrix a = Matrix.newRotationX(Math.PI / 2);
		Matrix b = Matrix.newScaling(5, 5, 5);
		Matrix c = Matrix.newTranslation(10, 5, 7);

		Matrix t = c.multiply(b).multiply(a);

		Point p2 = t.multiply(p);
		Assert.assertEquals(new Point(15, 0, 7), p2);
	}
}
