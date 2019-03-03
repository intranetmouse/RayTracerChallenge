package org.intranet.graphics.raytrace;

import java.util.HashMap;
import java.util.Map;

public final class TupleData
{
	private Map<String, Tuple> tupleMap = new HashMap<>();
	public void put(String tupleName, Tuple tuple)
	{ tupleMap.put(tupleName, tuple); }
	public Tuple getTuple(String tupleName)
	{ return tupleMap.get(tupleName); }

	private Map<String, Point> pointMap = new HashMap<>();
	public void put(String pointName, Point point)
	{ pointMap.put(pointName, point); }
	public Point getPoint(String pointName)
	{ return pointMap.get(pointName); }

	private Map<String, Vector> vectorMap = new HashMap<>();
	public void put(String vectorName, Vector vector)
	{ vectorMap.put(vectorName, vector); }
	public Vector getVector(String vectorName)
	{ return vectorMap.get(vectorName); }

	private Map<String, Color> colorMap = new HashMap<>();
	public void put(String colorName, Color color)
	{ colorMap.put(colorName, color); }
	public Color getColor(String colorName)
	{ return colorMap.get(colorName); }

	private Map<String, Matrix> matricesMap = new HashMap<>();
	public void put(String matrixName, Matrix m)
	{ matricesMap.put(matrixName, m); }
	public Matrix getMatrix(String matrixName)
	{ return matricesMap.get(matrixName); }
}