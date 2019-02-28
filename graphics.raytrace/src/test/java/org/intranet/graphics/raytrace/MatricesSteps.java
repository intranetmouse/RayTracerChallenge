package org.intranet.graphics.raytrace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class MatricesSteps
{
	private Map<String, Matrix> matricesMap = new HashMap<>();

	@Given("^the following (\\d+)x(\\d+) matrix ([a-zA-Z_][a-zA-Z0-9]*):$")
	public void theFollowingRxCMatrixM(int numRows, int numCols, String matrixName,
		DataTable dataTable)
	{
		List<Double> doubles = dataTable.asList(Double.class);
		Assert.assertEquals(numRows * numCols, doubles.size());
		createMatrix(numRows, numCols, matrixName, doubles);
	}

	@Given("^the following matrix ([a-zA-Z_][a-zA-Z0-9]*):$")
	public void theFollowingXMatrixM(String matrixName,
		DataTable dataTable)
	{
		List<Double> doubles = dataTable.asList(Double.class);
		int size = (int)Math.sqrt(doubles.size());
		createMatrix(size, size, matrixName, doubles);
	}


	private void createMatrix(int numRows, int numCols, String matrixName,
		List<Double> doubles)
	{
		Matrix m = createMatrix(numRows, numCols, doubles);
		matricesMap.put(matrixName,  m);
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

	@Then("^([a-zA-Z_][a-zA-Z0-9]*)\\[(\\d+),(\\d+)\\] = (-?\\d+\\.?\\d*)$")
	public void matrixAssertEquals(String varName, int rowNum, int colNum,
		double expected)
	{
		Matrix m = matricesMap.get(varName);
		double actual = m.get(rowNum, colNum);
		Assert.assertEquals(expected, actual, Tuple.EPSILON);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9]*) (=|!=) ([a-zA-Z_][a-zA-Z0-9]*)$")
	public void aB(String mtx1Name, String operation, String mtx2Name)
	{
		Matrix m1 = matricesMap.get(mtx1Name);
		Matrix m2 = matricesMap.get(mtx2Name);

		if ("=".equals(operation))
			Assert.assertEquals(m1, m2);
		else if ("!=".equals(operation))
			Assert.assertNotEquals(m1, m2);
		else
			throw new IllegalArgumentException("Unknown operation " + operation);
	}

	@Then("^([a-zA-Z_][a-zA-Z0-9]*) \\* ([a-zA-Z_][a-zA-Z0-9]*) is the following (\\d+)x(\\d+) matrix:$")
	public void aTimesB(String mtx1Name, String mtx2Name, int numRows, int numCols, List<Double> doubles)
	{
		Matrix m1 = matricesMap.get(mtx1Name);
		Matrix m2 = matricesMap.get(mtx2Name);
		Matrix expected = createMatrix(numRows, numCols, doubles);

		Matrix result = m1.multiply(m2);

		Assert.assertEquals(expected, result);
	}

}
