package org.intranet.graphics.raytrace;

public final class Matrix
{
	public static Matrix identity(int size)
	{
		double[][] mtx = allocateArray(size, size);
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				mtx[row][col] = row == col ? 1.0 : 0.0;
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

	public void set(int row, int col, double value)
	{
		matrix[row][col] = value;
		inverse = null;
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
				throw new IllegalStateException("Both matrices should have same number of rows " + numRows + " and cols " + numCols);
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
		double[] values = multiplyTuple(b);
		return new Point(values[0], values[1], values[2], values[3]);
	}

	public Tuple multiply(Tuple b)
	{
		double[] values = multiplyTuple(b);
		return new Tuple(values[0], values[1], values[2], values[3]);
	}

	private double[] multiplyTuple(Tuple tuple)
	{
		double[] values = new double[4];
		for (int rowNum = 0; rowNum < 4; rowNum++)
		{
			double[] row = matrix[rowNum];
			for (int colNum = 0; colNum < row.length; colNum++)
				values[rowNum] += matrix[rowNum][colNum] * tuple.values[colNum];
		}
		return values;
	}

	public Vector multiply(Vector v)
	{
		double[] values = multiplyTuple(v);
		return new Vector(values[0], values[1], values[2], values[3]);
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
		double[] row0 = matrix[0];

		if (matrix.length == 2 && row0.length == 2)
			return row0[0] * matrix[1][1] - matrix[1][0] * row0[1];

		double det = 0.0;
		for (int colNum = 0; colNum < row0.length; colNum++)
			det += matrix[0][colNum] * cofactor(0, colNum);
		return det;
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

	public double minor(int dropRow, int dropCol)
	{
		return submatrix(dropRow, dropCol).determinant();
	}

	public double cofactor(int dropRow, int dropCol)
	{
		double minor = minor(dropRow, dropCol);
		if ((dropRow + dropCol) % 2 == 1)
			minor = -minor;
		return minor;
	}

	public boolean isInvertible()
	{
		return !Tuple.dblEqual(0.0, determinant());
	}

	private Matrix inverse;
	public Matrix inverse()
	{
		if (!isInvertible())
			return null;

		if (inverse != null)
			return inverse;

		double determinant = determinant();
		double[][] other = allocateArray(matrix.length, matrix[0].length);
		for (int row = 0; row < matrix.length; row++)
			for (int col = 0; col < matrix[0].length; col++)
			{
				double c = cofactor(row, col);
				// note that "col, row" here, instead of "row, col",
				// accomplishes the transpose operation!
				other[col][row] = c / determinant;
			}
		inverse = new Matrix(other);
		return inverse;
	}

	public static Matrix newTranslation(double x, double y, double z)
	{
		Matrix translation = identity(4);
		translation.matrix[0][3] = x;
		translation.matrix[1][3] = y;
		translation.matrix[2][3] = z;
		return translation;
	}

	public static Matrix newScaling(double x, double y, double z)
	{
		Matrix translation = identity(4);
		translation.matrix[0][0] = x;
		translation.matrix[1][1] = y;
		translation.matrix[2][2] = z;
		return translation;
	}

	/**
	* @param d And angle in radians
	* @return
	*/
	public static Matrix newRotationX(double d)
	{
		Matrix translation = identity(4);
		translation.matrix[1][1] = Math.cos(d);
		translation.matrix[1][2] = -Math.sin(d);
		translation.matrix[2][1] = Math.sin(d);
		translation.matrix[2][2] = Math.cos(d);
		return translation;
	}

	/**
	* @param d And angle in radians
	* @return
	*/
	public static Matrix newRotationY(double d)
	{
		Matrix translation = identity(4);
		translation.matrix[0][0] = Math.cos(d);
		translation.matrix[0][2] = Math.sin(d);
		translation.matrix[2][0] = -Math.sin(d);
		translation.matrix[2][2] = Math.cos(d);
		return translation;
	}

	/**
	* @param d And angle in radians
	* @return
	*/
	public static Matrix newRotationZ(double d)
	{
		Matrix translation = identity(4);
		translation.matrix[0][0] = Math.cos(d);
		translation.matrix[0][1] = -Math.sin(d);
		translation.matrix[1][0] = Math.sin(d);
		translation.matrix[1][1] = Math.cos(d);
		return translation;
	}

	public static Matrix shearing(double xy, double xz, double yx, double yz,
		double zx, double zy)
	{
		Matrix translation = identity(4);
		translation.matrix[0][1] = xy;
		translation.matrix[0][2] = xz;
		translation.matrix[1][0] = yx;
		translation.matrix[1][2] = yz;
		translation.matrix[2][0] = zx;
		translation.matrix[2][1] = zy;
		return translation;
	}

	public static Matrix newView(Point fromPoint, Point toPoint,
		Vector upVector)
	{
		Vector forwardVector = toPoint.subtract(fromPoint).normalize();
		Vector left = forwardVector.cross(upVector.normalize());
		Vector trueUp = left.cross(forwardVector);

		double[][] orientationArray = {
			{ left.getX(), left.getY(), left.getZ(), 0},
			{ trueUp.getX(), trueUp.getY(), trueUp.getZ(), 0},
			{ -forwardVector.getX(), -forwardVector.getY(), -forwardVector.getZ(), 0 },
			{0, 0, 0, 1}
		};

		Matrix orientationMtx = new Matrix(orientationArray);

		return orientationMtx.multiply(Matrix.newTranslation(-fromPoint.getX(),
			-fromPoint.getY(), -fromPoint.getZ()));
	}

	public int getNumRows()
	{
		return matrix.length;
	}

	public int getNumCols()
	{
		return matrix[0].length;
	}
}
