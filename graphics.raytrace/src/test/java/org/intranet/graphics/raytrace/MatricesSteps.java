package org.intranet.graphics.raytrace;

import java.util.List;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
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
	public void theTransposeOfIdentity(String matrix1Name, String matrix2Name,
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
	public void theTransposeOfIdentity(String destMatrixName,
		String matrix1Name, String matrix2Name)
	{
		Matrix matrix1 = data.getMatrix(matrix1Name);
		Matrix matrix2 = data.getMatrix(matrix2Name);

		data.put(destMatrixName, matrix1.multiply(matrix2));
	}

	@Given(wordPattern + " ← translation\\(" + threeDoublesPattern + "\\)")
	public void transformTranslation(String matrixName, double x, double y,
		double z)
	{
		Matrix matrix = Matrix.identity(4);
		matrix.matrix[0][3] = x;
		matrix.matrix[1][3] = y;
		matrix.matrix[2][3] = z;
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

	@Then("^" + wordPattern + " \\* identity_matrix = " + wordPattern + "$")
	public void matrixATimesIdentity(String mtx1Name, String mtx2Name)
	{
		Assert.assertEquals("Only the same matrix is supported", mtx1Name, mtx2Name);
		Matrix m1 = data.getMatrix(mtx1Name);
		Assert.assertNotNull(mtx1Name, m1);

		Matrix identityMtx = Matrix.identity(m1.matrix[0].length);

		Matrix result = m1.multiply(identityMtx);

		Assert.assertEquals(m1, result);
	}

	@Then("^identity_matrix \\* " + wordPattern + " = " + wordPattern + "$")
	public void identityTimesMatrixA(String mtx1Name, String mtx2Name)
	{
		Assert.assertEquals("Only the same matrix is supported", mtx1Name, mtx2Name);
		Tuple a = data.getTuple(mtx1Name);
		Assert.assertNotNull(mtx1Name, a);

		Matrix identityMtx = Matrix.identity(a.values.length);

		Tuple result = identityMtx.multiply(a);

		Assert.assertEquals(a, result);
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

}
