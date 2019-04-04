package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.Matrix;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Tuple;
import org.intranet.graphics.raytrace.Vector;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class MatricesSteps
	extends StepsParent
{

	public MatricesSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("^the following " + intPattern + "x" + intPattern + " matrix " + wordPattern + ":$")
	public void theFollowingRxCMatrixM(int numRows, int numCols, String matrixName,
		DataTable dataTable)
	{
		List<Double> doubles = dataTable.asList(Double.class);
		Assert.assertEquals(numRows * numCols, doubles.size());
		createMatrix(numRows, numCols, matrixName, doubles);
	}

	@Given("^the following matrix " + wordPattern + ":$")
	public void theFollowingXMatrixM(String matrixName,
		DataTable dataTable)
	{
		List<Double> doubles = dataTable.asList(Double.class);
		int size = (int)Math.sqrt(doubles.size());
		createMatrix(size, size, matrixName, doubles);
	}

	@Given("^" + wordPattern + " ← transpose\\(identity_matrix\\)$")
	public void theTransposeOfIdentity(String matrixName)
	{
		Matrix ident = Matrix.identity(4);
		data.put(matrixName, ident);
	}

	@Given("^" + wordPattern + " ← submatrix\\(" + wordPattern + ", " + intPattern + ", " + intPattern + "\\)$")
	public void theSubmatrixOf(String matrix1Name, String matrix2Name,
		int dropRow, int dropCol)
	{
		Matrix m2 = data.getMatrix(matrix2Name);
		Matrix result = m2.submatrix(dropRow, dropCol);
		data.put(matrix1Name, result);
	}

	@Given("^" + wordPattern + " ← inverse\\(" + wordPattern + "\\)$")
	public void theInverse(String matrix1Name, String matrix2Name)
	{
		Matrix matrix2 = data.getMatrix(matrix2Name);
		Matrix calc = matrix2.inverse();

		data.put(matrix1Name, calc);
	}

	@Given("^" + wordPattern + " ← " + wordPattern + " \\* " + wordPattern + "$")
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

	@Given("^" + wordPattern + " ← " + wordPattern + " \\* " + wordPattern + " \\* " + wordPattern + "$")
	public void multiplyingMatrixByMatrixByMatrix(String destObjectName,
		String matrix1Name, String matrix2Name, String matrix3Name)
	{
		Matrix matrix1 = data.getMatrix(matrix1Name);
		Matrix matrix2 = data.getMatrix(matrix2Name);
		Matrix matrix3 = data.getMatrix(matrix3Name);

		Matrix result = matrix1.multiply(matrix2).multiply(matrix3);

		data.put(destObjectName, result);
	}

	@Given(wordPattern + " ← translation\\(" + threeDoublesPattern + "\\)")
	public void transformTranslation(String matrixName, double x, double y,
		double z)
	{
		Matrix matrix = Matrix.newTranslation(x, y, z);
		data.put(matrixName, matrix);
	}

	@Given(wordPattern + " ← scaling\\(" + threeDoublesPattern + "\\)")
	public void transformScaling(String matrixName, double x, double y,
		double z)
	{
		Matrix matrix = Matrix.newScaling(x, y, z);
		data.put(matrixName, matrix);
	}

	@Given(wordPattern + " ← rotation_x\\(π \\/ " + doublePattern + "\\)")
	public void half_quarterRotation_xPi(String matrixName, double xRotation)
	{
		Matrix matrix = Matrix.newRotationX(Math.PI / xRotation);
		data.put(matrixName, matrix);
	}

	@Given(wordPattern + " ← rotation_y\\(π \\/ " + doublePattern + "\\)")
	public void half_quarterRotation_yPi(String matrixName, double yRotation)
	{
		Matrix matrix = Matrix.newRotationY(Math.PI / yRotation);
		data.put(matrixName, matrix);
	}

	@Given(wordPattern + " ← rotation_z\\(π \\/ " + doublePattern + "\\)")
	public void half_quarterRotation_zPi(String matrixName, double yRotation)
	{
		Matrix matrix = Matrix.newRotationZ(Math.PI / yRotation);
		data.put(matrixName, matrix);
	}

	@Given(wordPattern + " ← shearing\\(" + sixDoublesPattern + "\\)")
	public void shearing(String matrixName, double xy, double xz, double yx, double yz, double zx, double zy)
	{
		Matrix matrix = Matrix.shearing(xy, xz, yx, yz, zx, zy);
		data.put(matrixName, matrix);
	}

	private void createMatrix(int numRows, int numCols, String matrixName,
		List<Double> doubles)
	{
		Matrix m = createMatrix(numRows, numCols, doubles);
		data.put(matrixName,  m);
	}

	private Matrix createMatrix(int numRows, int numCols, List<Double> doubles)
	{
		double[][] rows = new double[numRows][];
		for (int rowNum = 0; rowNum < numRows; rowNum++)
		{
			rows[rowNum] = new double[numCols];
			for (int colNum = 0; colNum < numCols; colNum++)
			{
				rows[rowNum][colNum] = doubles.get(rowNum * numCols + colNum);
			}
		}
		Matrix m = new Matrix(rows);
		return m;
	}


	@When(wordPattern + " ← view_transform\\(" + wordPattern + ", "
		+ wordPattern + ", " + wordPattern + "\\)")
	public void tView_transformFromToUp(String matrixName, String fromPointName,
		String toPointName, String upVectorName)
	{
		Point fromPoint = data.getPoint(fromPointName);
		Point toPoint = data.getPoint(toPointName);
		Vector upVector = data.getVector(upVectorName);

		data.put(matrixName, Matrix.newView(fromPoint, toPoint, upVector));
	}


	@Then("^" + wordPattern + "\\[" + intPattern + "," + intPattern + "\\] = " + doublePattern + "$")
	public void matrixAssertEquals(String varName, int rowNum, int colNum,
		double expected)
	{
		Matrix m = data.getMatrix(varName);
		double actual = m.get(rowNum, colNum);
		Assert.assertEquals(expected, actual, Tuple.EPSILON);
	}

	@Then("^" + wordPattern + " (=|!=) " + wordPattern + "$")
	public void aB(String mtx1Name, String operation, String mtx2Name)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Matrix m2 = "identity_matrix".equals(mtx2Name) ?
			Matrix.identity(m1.getNumCols()) : data.getMatrix(mtx2Name);

		if ("=".equals(operation))
			Assert.assertEquals(m1, m2);
		else if ("!=".equals(operation))
			Assert.assertNotEquals(m1, m2);
		else
			throw new IllegalArgumentException("Unknown operation " + operation);
	}

	@Then("^" + wordPattern + " \\* " + wordPattern + " is the following " + intPattern + "x" + intPattern + " matrix:$")
	public void matrixATimesMatrixB(String mtx1Name, String mtx2Name,
		int numRows, int numCols, List<Double> doubles)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Matrix m2 = data.getMatrix(mtx2Name);
		Matrix expected = createMatrix(numRows, numCols, doubles);

		Matrix result = m1.multiply(m2);

		Assert.assertEquals(expected, result);
	}

	@Then("^" + wordPattern + " \\* " + wordPattern + " = tuple\\(" + threeDoublesPattern + ", 1\\)$")
	public void matrixATimesTupleB(String mtx1Name, String tupleBName,
		double x, double y, double z)
	{
		Matrix m1 = data.getMatrix(mtx1Name);
		Point tupleB = data.getPoint(tupleBName);
		Point expected = new Point(x, y, z);

		Point result = m1.multiply(tupleB);

		Assert.assertEquals(expected, result);
	}

	@Then("^" + wordPattern + " \\* " + wordPattern + " = " + wordPattern + "$")
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

	@Then("^transpose\\(" + wordPattern + "\\) is the following matrix:$")
	public void transposeAIsTheFollowingMatrix(String mtxName, DataTable dataTable)
	{
		Matrix a = data.getMatrix(mtxName);

		Matrix result = a.transpose();

		List<Double> doubles = dataTable.asList(Double.class);
		Matrix expected = createMatrix(dataTable.height(), dataTable.width(), doubles);

		Assert.assertEquals(expected, result);
	}

	@Then("^" + wordPattern + " is the following " + intPattern + "x" + intPattern + " matrix:$")
	public void aIsTheFollowingMatrix(String mtxName, int rows, int cols, DataTable dataTable)
	{
		Matrix result = data.getMatrix(mtxName);

		List<Double> doubles = dataTable.asList(Double.class);
		Matrix expected = createMatrix(dataTable.height(), dataTable.width(), doubles);

		Assert.assertEquals(rows, result.getNumRows());
		Assert.assertEquals(cols, result.getNumCols());
		Assert.assertEquals(expected, result);
	}

	@Then("^inverse\\(" + wordPattern + "\\) is the following " + intPattern + "x" + intPattern + " matrix:$")
	public void inverseAIsTheFollowingMatrix(String mtxName, int rows, int cols,
		DataTable dataTable)
	{
		Matrix matrix = data.getMatrix(mtxName);
		Matrix result = matrix.inverse();

		List<Double> doubles = dataTable.asList(Double.class);
		Matrix expected = createMatrix(dataTable.height(), dataTable.width(), doubles);

		Assert.assertEquals(rows, result.getNumRows());
		Assert.assertEquals(cols, result.getNumCols());
		Assert.assertEquals(expected, result);
	}

	@Then("^determinant\\(" + wordPattern + "\\) = " + doublePattern + "$")
	public void determinantA(String matrixName, double expectedValue)
	{
		Matrix a = data.getMatrix(matrixName);

		double result = a.determinant();

		Assert.assertEquals(expectedValue, result, Tuple.EPSILON);
	}

	@Then("^submatrix\\(" + wordPattern + ", " + intPattern + ", " + intPattern + "\\) is the following " + intPattern + "x" + intPattern + " matrix:$")
	public void submatrixAIsTheFollowingXMatrix(String matrixName, int dropRow,
		int dropCol, int subRows, int subCols,
		DataTable dataTable)
	{
		Matrix m = data.getMatrix(matrixName);

		Matrix result = m.submatrix(dropRow, dropCol);

		List<Double> doubles = dataTable.asList(Double.class);
		Matrix expected = createMatrix(dataTable.height(), dataTable.width(), doubles);

		Assert.assertEquals(expected, result);
	}

	@Then("^minor\\(" + wordPattern + ", " + intPattern + ", " + intPattern + "\\) = " + doublePattern + "$")
	public void minorA(String matrixName, int dropRow, int dropCol, double expected)
	{
		Matrix m = data.getMatrix(matrixName);
		double result = m.minor(dropRow, dropCol);
		Assert.assertEquals(expected, result, Tuple.EPSILON);
	}

	@Then("^cofactor\\(" + wordPattern + ", " + intPattern + ", " + intPattern + "\\) = " + doublePattern + "$")
	public void cofactorA(String matrixName, int dropRow, int dropCol, double expected)
	{
		Matrix m = data.getMatrix(matrixName);
		double result = m.cofactor(dropRow, dropCol);
		Assert.assertEquals(expected, result, Tuple.EPSILON);
	}

	@Then(wordPattern + " (is|is not) invertible")
	public void aIsInvertible(String matrixName, String operation)
	{
		Matrix m = data.getMatrix(matrixName);
		if ("is".equals(operation))
			Assert.assertTrue(m.isInvertible());
		else if ("is not".equals(operation))
			Assert.assertFalse(m.isInvertible());
	}

	@Then("^" + wordPattern + "\\[" + intPattern + "," + intPattern + "\\] = " + doublePattern + "\\/" + doublePattern + "$")
	public void testMatrixCellToFraction(String matrixName, int row, int col,
		double numerator, double denominator)
	{
		double expected = numerator / denominator;

		Matrix m = data.getMatrix(matrixName);
		double value = m.get(row, col);

		Assert.assertEquals(expected, value, Tuple.EPSILON);
	}


	@Then("^" + wordPattern + " \\* inverse\\(" + wordPattern + "\\) = " + wordPattern + "$")
	public void cInverseBA(String cName, String bName, String expectedMatrixName)
	{
		Matrix c = data.getMatrix(cName);
		Matrix b = data.getMatrix(bName);
		Matrix result = data.getMatrix(expectedMatrixName);
		Matrix value = c.multiply(b.inverse());
		Assert.assertEquals(result, value);
	}

	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" + threeDoublesPattern + "\\)")
	public void transformPPoint(String matrixName, String pointName, double x,
		double y, double z)
	{
		Matrix m = data.getMatrix(matrixName);
		Point p = data.getPoint(pointName);
		Point result = m.multiply(p);
		Point expectedPoint = new Point(x, y, z);
		Assert.assertEquals(expectedPoint, result);
	}

	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
		doublePattern +
		", √" + doublePattern + "\\/" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern + "\\)")
	public void quarterPPointX(String matrixName, String pointName, double x,
		double y1, double y2, double z1, double z2)
	{
		transformPPoint(matrixName, pointName, x, Math.sqrt(y1) / y2, Math.sqrt(z1) / z2);
	}

	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
		doublePattern +
		", √" + doublePattern + "\\/" + doublePattern +
		", -√" + doublePattern + "\\/" + doublePattern + "\\)")
	public void quarterPPointXNegZ(String matrixName, String pointName, double x,
		double y1, double y2, double z1, double z2)
	{
		transformPPoint(matrixName, pointName, x, Math.sqrt(y1) / y2, -Math.sqrt(z1) / z2);
	}

	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
		"√" + doublePattern + "\\/" + doublePattern +
		", " + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern + "\\)")
	public void quarterPPointZ(String matrixName, String pointName, double x1,
		double x2, double y, double z1, double z2)
	{
		transformPPoint(matrixName, pointName, Math.sqrt(x1) / x2, y, Math.sqrt(z1) / z2);
	}

	@Then(wordPattern + " \\* " + wordPattern + " = point\\(" +
		"-√" + doublePattern + "\\/" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern +
		", " + doublePattern + "\\)")
	public void quarterPPointNegZ(String matrixName, String pointName, double x1,
		double x2, double y1, double y2, double z)
	{
		transformPPoint(matrixName, pointName, -Math.sqrt(x1) / x2, Math.sqrt(y1) / y2, z);
	}

	@Then(wordPattern + " \\* " + wordPattern + " = vector\\(" + threeDoublesPattern + "\\)")
	public void transformPVector(String matrixName, String vectorName, double x,
		double y, double z)
	{
		Matrix m = data.getMatrix(matrixName);
		Vector p = data.getVector(vectorName);
		Vector result = m.multiply(p);
		Vector expectedVector = new Vector(x, y, z);
		Assert.assertEquals(expectedVector, result);
	}

	@Then(wordPattern + " = scaling\\(" + threeDoublesPattern + "\\)")
	public void compareToScalingMatrix(String matrixName, double x,
		double y, double z)
	{
		Matrix m = data.getMatrix(matrixName);
		Matrix expected = Matrix.newScaling(x, y, z);
		Assert.assertEquals(expected, m);
	}

	@Then(wordPattern + " = translation\\(" + threeDoublesPattern + "\\)")
	public void compareToTranslationMatrix(String matrixName, double x,
		double y, double z)
	{
		Matrix m = data.getMatrix(matrixName);
		Matrix expected = Matrix.newTranslation(x, y, z);
		Assert.assertEquals(expected, m);
	}

}
