package org.intranet.graphics.raytrace;

import java.util.HashMap;
import java.util.Map;

public final class RaytraceData
{
	private Map<String, Tuple> tupleMap = new HashMap<>();
	public void put(String tupleName, Tuple tuple)
	{ tupleMap.put(tupleName, tuple); }

	public Tuple getTuple(String tupleName)
	{
		Tuple a = tupleMap.get(tupleName);
		if (a == null) a = getPoint(tupleName);
		if (a == null) a = getVector(tupleName);
		return a;
	}

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

	private Map<String, Ray> raysMap = new HashMap<>();
	public void put(String rayName, Ray r)
	{ raysMap.put(rayName, r); }
	public Ray getRay(String rayName)
	{ return raysMap.get(rayName); }

	private Map<String, Sphere> spheresMap = new HashMap<>();
	public void put(String sphereName, Sphere r)
	{ spheresMap.put(sphereName, r); }
	public Sphere getSphere(String sphereName)
	{ return spheresMap.get(sphereName); }

	private Map<String, double[]> intersectionDistancesMap = new HashMap<>();
	public void put(String intersectionDistanceName, double[] i)
	{ intersectionDistancesMap.put(intersectionDistanceName, i); }
	public double[] getIntersectionDistances(String intersectionDistanceName)
	{ return intersectionDistancesMap.get(intersectionDistanceName); }

	private Map<String, Intersection> intersectionsMap = new HashMap<>();
	public void put(String intersectionName, Intersection i)
	{ intersectionsMap.put(intersectionName, i); }
	public Intersection getIntersection(String intersectionName)
	{ return intersectionsMap.get(intersectionName); }

	private Map<String, IntersectionList> intersectionListsMap = new HashMap<>();
	public void put(String intersectionListName, IntersectionList ilist)
	{ intersectionListsMap.put(intersectionListName, ilist); }
	public IntersectionList getIntersectionList(String intersectionListName)
	{ return intersectionListsMap.get(intersectionListName); }
}