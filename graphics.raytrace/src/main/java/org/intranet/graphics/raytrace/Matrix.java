package org.intranet.graphics.raytrace;

public class Matrix
{
	public static Matrix identity(int size)
	{
		double[][] mtx = new double[size][];
		for (int row = 0; row < size; row++)
		{
			mtx[row] = new double[size];
			for (int col = 0; col < size; col++)
				mtx[row][col] = row == col ? 1.0 : 0.0;
		}
		return new Matrix(mtx);
	}

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
		Matrix result = new Matrix(allocateArray(4, 4));

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
		Point result = new Point(values[0], values[1], values[2], values[3]);
		return result;
	}

	public Matrix transpose()
	{
		double[][] mtx = allocateArray(matrix[0].length, matrix.length);

		for (int origCol = 0; origCol < matrix[0].length; origCol++)
			for (int origRow = 0; origRow < matrix.length; origRow++)
				mtx[origCol][origRow] = matrix[origRow][origCol];

		return new Matrix(mtx);
	}

	public static double[][] allocateArray(int numRows, int numCols)
	{
		double[][] rows = new double[numRows][];
		for (int rowNum = 0; rowNum < numRows; rowNum++)
			rows[rowNum] = new double[numCols];
		return rows;
	}

	public double determinant()
	{
		if (matrix.length != 2)
			throw new UnsupportedOperationException(
				"Determinant only works for 2x2 matrices.");
		return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
	}

	public Matrix submatrix(int dropRow, int dropCol)
	{
		int oldNumRows = matrix.length;
		int oldNumCols = matrix[0].length;
		double[][] mtx = allocateArray(oldNumRows - 1, oldNumCols - 1);
		for (int oldRowNum = 0; oldRowNum < oldNumRows; oldRowNum++)
		{
			if (oldRowNum == dropRow)
				continue;
			int newRowNum = oldRowNum - (oldRowNum >= dropRow ? 1 : 0);
			for (int oldColNum = 0; oldColNum < oldNumRows; oldColNum++)
			{
				if (oldColNum == dropCol)
					continue;
				int newColNum = oldColNum - (oldColNum >= dropCol ? 1 : 0);
				mtx[newRowNum][newColNum] = matrix[oldRowNum][oldColNum];
			}
		}
		return new Matrix(mtx);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("[");
		for (int rowNum = 0; rowNum < matrix.length; rowNum++)
		{
			if (rowNum != 0)
				sb.append(",");
			sb.append("[");
			double[] row = matrix[rowNum];
			for (int colNum = 0; colNum < row.length; colNum++)
			{
				if (colNum != 0)
					sb.append(",");
				sb.append(row[colNum]);
			}
			sb.append("]");
		}
		sb.append("]");
		return sb.toString();
	}
}
