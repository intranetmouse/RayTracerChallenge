package org.intranet.graphics.raytrace;

public class Matrix
{
	double[][] matrix;
	public Matrix(double[] ... rows)
	{
		int numRows = rows.length;
		matrix = new double[numRows][];
		for (int rowNum = 0; rowNum < numRows; rowNum++)
		{
			int numCols = rows[rowNum].length;
			matrix[rowNum] = new double[numCols];
			for (int colNum = 0; colNum < numCols; colNum++)
				matrix[rowNum][colNum] = rows[rowNum][colNum];
		}
	}

	public double get(int row, int col)
	{
		return matrix[row][col];
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Matrix))
			return false;
		Matrix otherMatrix = (Matrix)other;

		int numRows = matrix.length;
		if (otherMatrix.matrix.length != numRows)
			return false;

		for (int rowNum = 0; rowNum < numRows; rowNum++)
		{
			double[] row = matrix[rowNum];
			double[] otherRow = otherMatrix.matrix[rowNum];
			int numCols = row.length;
			if (otherRow.length != numCols)
				return false;
			for (int colNum = 0; colNum < numCols; colNum++)
			{
				if (!Tuple.dblEqual(row[colNum], otherRow[colNum]))
					return false;
			}
		}
		return true;
	}

	public Matrix multiply(Matrix other)
	{
		Matrix result = new Matrix(new double[4], new double[4], new double[4], new double[4]);

		int numRows = matrix.length;
		for (int rowNum = 0; rowNum < numRows; rowNum++)
		{
			double[] row = matrix[rowNum];
			int numCols = row.length;
			if (numRows != numCols)
				throw new IllegalStateException("Both matrices should have same number of rows and cols");
			for (int colNum = 0; colNum < numCols; colNum++)
			{
				double sum = 0.0;
				for (int i = 0; i < numCols; i++)
					sum += matrix[rowNum][i] * other.matrix[i][colNum];
				result.matrix[rowNum][colNum] = sum;
			}
		}
		return result;
	}

	public Point multiply(Point b)
	{
		double[] values = new double[4];
		for (int rowNum = 0; rowNum < 4; rowNum++)
		{
			double[] row = matrix[rowNum];
			for (int colNum = 0; colNum < row.length; colNum++)
				values[rowNum] += matrix[rowNum][colNum] * b.values[colNum];
		}
		Point result = new Point(values[0], values[1], values[2]);
		return result;
	}
}
