package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Matrix;
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

public class MatricesSteps
	extends StepsParent
{

	public MatricesSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("the following {int}x{int} matrix {identifier}:")
	public void theFollowingRxCMatrixM(int numRows, int numCols, String matrixName,
		List<List<Double>> doubles)
	{
		Matrix m = createMatrixFrom2dimList(numRows, numCols, doubles);
		data.put(matrixName,  m);
	}

	@Given("the following matrix {identifier}:")
	public void theFollowingXMatrixM(String matrixName,
		List<List<Double>> doubles)
	{
		int size = doubles.size();
		Matrix m = createMatrixFrom2dimList(size, size, doubles);
		data.put(matrixName, m);
	}

	@Given("{identifier} ← transpose\\(identity_matrix)")
	public void theTransposeOfIdentity(String matrixName)
	{
		Matrix ident = Matrix.identity(4);
		data.put(matrixName, ident);
	}

	@Given("{identifier} ← submatrix\\({identifier}, {int}, {int})")
	public void theSubmatrixOf(String matrix1Name, String matrix2Name,
		int dropRow, int dropCol)
	{
		Matrix m2 = data.getMatrix(matrix2Name);
		Matrix result = m2.submatrix(dropRow, dropCol);
		data.put(matrix1Name, result);
	}

	@Given("{identifier} ← inverse\\({identifier})")
	public void theInverse(String matrix1Name, String matrix2Name)
	{
		Matrix matrix2 = data.getMatrix(matrix2Name);
		Matrix calc = matrix2.inverse();

		data.put(matrix1Name, calc);
	}

	@Given("{identifier} ← {identifier} * {identifier}")
	public void multiplyingMatrixBySomethingElse(String destObjectName,
		String transformMatrixName, String object2Name)
	{
		Matrix transformMatrix = data.getMatrix(transformMatrixName);

		Matrix matrix = data.getMatrix(object2Name);
		if (matrix != null)
		{
			Matrix resultMatrix = transformMatrix.multiply(matrix);
			data.put(destObjectName, resultMatrix);
			return;
		}

		Point point = data.getPoint(object2Name);
		if (point != null)
		{
			Point resultPoint = transformMatrix.multiply(point);
			data.put(destObjectName, resultPoint);
			return;
		}

		Vector v2 = data.getVector(object2Name);
		if (v2 != null)
		{
			Vector resultVector = transformMatrix.multiply(v2);
			data.put(destObjectName, resultVector);
			return;
		}
		throw new IllegalStateException();
	}

	@Given("{identifier} ← {identifier} * {identifier} * {identifier}")
	public void multiplyingMatrixByMatrixByMatrix(String destObjectName,
		String matrix1Name, String matrix2Name, String matrix3Name)
	{
		Matrix matrix1 = data.getMatrix(matrix1Name);
		Matrix matrix2 = data.getMatrix(matrix2Name);
		Matrix matrix3 = data.getMatrix(matrix3Name);

		Matrix result = matrix1.multiply(matrix2).multiply(matrix3);

		data.put(destObjectName, result);
	}

	@Givens({
		@Given("{identifier} ← {matrix}"),
		@Given("{identifier} ← {shearing}"),
		@Given("{identifier} ← {matrixRotationPiDiv}")
	})
	public void transformTranslation(String matrixName, Matrix matrix)
	{
		data.put(matrixName, matrix);
	}

	@Thens({
		@Then("{identifier} * {identifier} = {pointNSS}"),
		@Then("{identifier} * {identifier} = {pointSNS}"),
		@Then("{identifier} * {identifier} = {pointSSN}")
	})
	public void mtxTimesPointEqPoint(String matrixName, String pointName,
		Point expectedPoint)
	{
		Matrix mtx = data.getMatrix(matrixName);
		Point p = data.getPoint(pointName);
		Assert.assertEquals(expectedPoint, mtx.multiply(p));
	}

//	@Given(wordPattern + " ← scaling\\(" + threeDoublesPattern + "\\)")
//	public void transformScaling(String matrixName, double x, double y,
//		double z)
//	{
//		Matrix matrix = Matrix.newScaling(x, y, z);
//		data.put(matrixName, matrix);
//	}


//	@Given(wordPattern + " ← rotation_y\\(π \\/ " + doublePattern + "\\)")
//	public void half_quarterRotation_yPi(String matrixName, double yRotation)
//	{
//		Matrix matrix = Matrix.newRotationY(Math.PI / yRotation);
//		data.put(matrixName, matrix);
//	}
//
//	@Given(wordPattern + " ← rotation_z\\(π \\/ " + doublePattern + "\\)")
//	public void half_quarterRotation_zPi(String matrixName, double yRotation)
//	{
//		Matrix matrix = Matrix.newRotationZ(Math.PI / yRotation);
//		data.put(matrixName, matrix);
//	}

	private Matrix createMatrixFrom2dimList(int numRows, int numCols,
		List<List<Double>> doubles)
	{
		Assert.assertEquals(numRows, doubles.size());
		double[][] rows = new double[numRows][];
		for (int rowNum = 0; rowNum < numRows; rowNum++)
		{
			List<Double> rowDoubles = doubles.get(rowNum);
			Assert.assertEquals(numCols, rowDoubles.size());
			rows[rowNum] = new double[numCols];
			for (int colNum = 0; colNum < numCols; colNum++)
			{
				rows[rowNum][colNum] = rowDoubles.get(colNum);
			}
		}
		Matrix m = new Matrix(rows);
		return m;
	}

	@When("{identifier} ← view_transform\\({identifier}, {identifier}, {identifier})")
	public void tView_transformFromToUp(String matrixName, String fromPointName,
		String toPointName, String upVectorName)
	{
		Point fromPoint = data.getPoint(fromPointName);
		Point toPoint = data.getPoint(toPointName);
		Vector upVector = data.getVector(upVectorName);

		data.put(matrixName, Matrix.newView(fromPoint, toPoint, upVector));
	}


	@Then("{identifier}[{int},{int}] = {dbl}")
	public void matrixAssertEquals(String varName, int rowNum, int colNum,
		double expected)
	{
		Matrix m = data.getMatrix(varName);
		double actual = m.get(rowNum, colNum);
		Assert.assertEquals(expected, actual, Tuple.EPSILON);
	}

	@Then("{identifier} = {identifier}")
	public void mtxEq(String mtx1Name, String mtx2Name)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Matrix m2 = "identity_matrix".equals(mtx2Name) ?
			Matrix.identity(m1.getNumCols()) : data.getMatrix(mtx2Name);
		if (m2 != null)
		{
			Assert.assertEquals(m1, m2);
			return;
		}

		Color c1 = data.getColor(mtx1Name);
		Color c2 = "black".equals(mtx2Name) ? Color.BLACK :
			"white".equals(mtx2Name) ? Color.WHITE :
			data.getColor(mtx2Name);
		if (c1 != null || c2 != null)
		{
			Assert.assertEquals(c1, c2);
			return;
		}

		Assert.fail("Unknown data for types " + mtx1Name + " and " + mtx2Name);
	}

	@Then("{identifier} != {identifier}")
	public void mtxNeq(String mtx1Name, String mtx2Name)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Matrix m2 = "identity_matrix".equals(mtx2Name) ?
			Matrix.identity(m1.getNumCols()) : data.getMatrix(mtx2Name);

		Assert.assertNotEquals(m1, m2);
	}

	@Then("{identifier} * {identifier} is the following {int}x{int} matrix:")
	public void matrixATimesMatrixB(String mtx1Name, String mtx2Name,
		int numRows, int numCols, List<List<Double>> doubles)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Matrix m2 = data.getMatrix(mtx2Name);
		Matrix expected = createMatrixFrom2dimList(numRows, numCols, doubles);

		Matrix result = m1.multiply(m2);

		Assert.assertEquals(expected, result);
	}

	@Then("{identifier} * {identifier} = {tuple}")
	public void matrixATimesTupleB(String mtx1Name, String tupleBName,
		Tuple expected)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Point tupleB = data.getPoint(tupleBName);

		Point result = m1.multiply(tupleB);

		Assert.assertEquals(expected, result);
	}

	@Then("{identifier} * {identifier} = {identifier}")
	public void matrixATimesVectorB(String arg1Name, String arg2Name,
		String expectedName)
	{
		Matrix m1 = "identity_matrix".equals(arg1Name) ? Matrix.identity(4) : data.getMatrix(arg1Name);
		Vector vector = data.getVector(arg2Name);
		if (vector != null)
		{
			Vector expected = data.getVector(expectedName);
			Vector result = m1.multiply(vector);
			Assert.assertEquals(expected, result);
			return;
		}
		Tuple t = data.getTuple(arg2Name);
		if (t != null)
		{
			Tuple expected = data.getTuple(expectedName);
			Tuple result = m1.multiply(t);
			Assert.assertEquals(expected, result);
			return;
		}
		Matrix m2 = "identity_matrix".equals(arg2Name) ? Matrix.identity(4) : data.getMatrix(arg2Name);
		Matrix expected = data.getMatrix(expectedName);
		Matrix result = m1.multiply(m2);
		Assert.assertEquals(expected, result);
	}

	@Then("transpose\\({identifier}) is the following matrix:")
	public void transposeAIsTheFollowingMatrix(String mtxName, List<List<Double>> doubles)
	{
		Matrix a = data.getMatrix(mtxName);

		Matrix result = a.transpose();

		Matrix expected = createMatrixFrom2dimList(doubles.size(),
			doubles.get(0).size(), doubles);

		Assert.assertEquals(expected, result);
	}

	@Then("{identifier} is the following {int}x{int} matrix:")
	public void aIsTheFollowingMatrix(String mtxName, int rows, int cols,
		List<List<Double>> doubles)
	{
		Matrix result = data.getMatrix(mtxName);

		Matrix expected = createMatrixFrom2dimList(doubles.size(),
			doubles.get(0).size(), doubles);

		Assert.assertEquals(rows, result.getNumRows());
		Assert.assertEquals(cols, result.getNumCols());
		Assert.assertEquals(expected, result);
	}

	@Then("inverse\\({identifier}) is the following {int}x{int} matrix:")
	public void inverseAIsTheFollowingMatrix(String mtxName, int rows, int cols,
		List<List<Double>> doubles)
	{
		Matrix matrix = data.getMatrix(mtxName);
		Matrix result = matrix.inverse();

		Matrix expected = createMatrixFrom2dimList(doubles.size(),
			doubles.get(0).size(), doubles);

		Assert.assertEquals(rows, result.getNumRows());
		Assert.assertEquals(cols, result.getNumCols());
		Assert.assertEquals(expected, result);
	}

	@Then("determinant\\({identifier}) = {dbl}")
	public void determinantA(String matrixName, double expectedValue)
	{
		Matrix a = data.getMatrix(matrixName);

		double result = a.determinant();

		Assert.assertEquals(expectedValue, result, Tuple.EPSILON);
	}

	@Then("submatrix\\({identifier}, {int}, {int}) is the following {int}x{int} matrix:")
	public void submatrixAIsTheFollowingXMatrix(String matrixName, int dropRow,
		int dropCol, int subRows, int subCols,
		List<List<Double>> doubles)
	{
		Matrix m = data.getMatrix(matrixName);

		Matrix result = m.submatrix(dropRow, dropCol);

		Matrix expected = createMatrixFrom2dimList(doubles.size(),
			doubles.get(0).size(), doubles);

		Assert.assertEquals(expected, result);
	}

	@Then("minor\\({identifier}, {int}, {int}) = {dbl}")
	public void minorA(String matrixName, int dropRow, int dropCol,
		double expected)
	{
		Matrix m = data.getMatrix(matrixName);
		double result = m.minor(dropRow, dropCol);
		Assert.assertEquals(expected, result, Tuple.EPSILON);
	}

	@Then("cofactor\\({identifier}, {int}, {int}) = {dbl}")
	public void cofactorA(String matrixName, int dropRow, int dropCol,
		double expected)
	{
		Matrix m = data.getMatrix(matrixName);
		double result = m.cofactor(dropRow, dropCol);
		Assert.assertEquals(expected, result, Tuple.EPSILON);
	}

	@Then("{identifier} is invertible")
	public void aIsInvertible(String matrixName)
	{
		Matrix m = data.getMatrix(matrixName);
		Assert.assertTrue(m.isInvertible());
	}

	@Then("{identifier} is not invertible")
	public void aIsNotInvertible(String matrixName)
	{
		Matrix m = data.getMatrix(matrixName);
		Assert.assertFalse(m.isInvertible());
	}

	@Then("{identifier}[{int},{int}] = {dbl}\\/{dbl}")
	public void testMatrixCellToFraction(String matrixName, int row, int col,
		double numerator, double denominator)
	{
		double expected = numerator / denominator;

		Matrix m = data.getMatrix(matrixName);
		double value = m.get(row, col);

		Assert.assertEquals(expected, value, Tuple.EPSILON);
	}


	@Then("{identifier} * inverse\\({identifier}) = {identifier}")
	public void cInverseBA(String cName, String bName, String expectedMatrixName)
	{
		Matrix c = data.getMatrix(cName);
		Matrix b = data.getMatrix(bName);
		Matrix result = data.getMatrix(expectedMatrixName);
		Matrix value = c.multiply(b.inverse());
		Assert.assertEquals(result, value);
	}

	@Then("{identifier} * {identifier} = {point}")
	public void transformPPoint(String matrixName, String pointName,
		Point expectedPoint)
	{
		Matrix m = data.getMatrix(matrixName);
		Point p = data.getPoint(pointName);
		Point result = m.multiply(p);
		Assert.assertEquals(expectedPoint, result);
	}

//	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
//		doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern + "\\)")
//	public void quarterPPointX(String matrixName, String pointName, double x,
//		double y1, double y2, double z1, double z2)
//	{
//		transformPPoint(matrixName, pointName, x, Math.sqrt(y1) / y2, Math.sqrt(z1) / z2);
//	}
//
//	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
//		doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern +
//		", -√" + doublePattern + "\\/" + doublePattern + "\\)")
//	public void quarterPPointXNegZ(String matrixName, String pointName, double x,
//		double y1, double y2, double z1, double z2)
//	{
//		transformPPoint(matrixName, pointName, x, Math.sqrt(y1) / y2, -Math.sqrt(z1) / z2);
//	}
//
//	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
//		"√" + doublePattern + "\\/" + doublePattern +
//		", " + doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern + "\\)")
//	public void quarterPPointZ(String matrixName, String pointName, double x1,
//		double x2, double y, double z1, double z2)
//	{
//		transformPPoint(matrixName, pointName, Math.sqrt(x1) / x2, y, Math.sqrt(z1) / z2);
//	}
//
//	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
//		"-√" + doublePattern + "\\/" + doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern +
//		", " + doublePattern + "\\)")
//	public void quarterPPointNegZ(String matrixName, String pointName, double x1,
//		double x2, double y1, double y2, double z)
//	{
//		transformPPoint(matrixName, pointName, -Math.sqrt(x1) / x2, Math.sqrt(y1) / y2, z);
//	}

	@Then("{identifier} * {identifier} = {vector}")
	public void transformPVector(String matrixName, String vectorName, Vector expectedVector)
	{
		Matrix m = data.getMatrix(matrixName);
		Vector p = data.getVector(vectorName);
		Vector result = m.multiply(p);
		Assert.assertEquals(expectedVector, result);
	}

	@Thens({
		@Then("{identifier} = {matrix}"),
		@Then("{identifier} = {matrixRotationPiDiv}")
	})
	public void compareToScalingMatrix(String matrixName, Matrix expected)
	{
		Matrix m = data.getMatrix(matrixName);
		Assert.assertEquals(expected, m);
	}

//	@Then(wordPattern + " = translation\\(" + threeDoublesPattern + "\\)")
//	public void compareToTranslationMatrix(String matrixName, double x,
//		double y, double z)
//	{
//		Matrix m = data.getMatrix(matrixName);
//		Matrix expected = Matrix.newTranslation(x, y, z);
//		Assert.assertEquals(expected, m);
//	}
//
//	@Given("{word} ← rotation_x\\(π \\/ {int}) * rotation_y\\(π \\/ {int})")
//	public void matrixRotation_xΠRotation_yΠ(String matrixName, Integer int1, Integer int2)
//	{
//		Matrix m = Matrix.newRotationX(Math.PI / int1).multiply(Matrix.newRotationY(Math.PI / int2));
//		data.put(matrixName, m);
//	}
}
