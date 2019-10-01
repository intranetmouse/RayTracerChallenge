package org.intranet.graphics.raytrace.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

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

	private Map<String, Double> doubleMap = new HashMap<>();
	public void put(String doubleName, double value)
	{ doubleMap.put(doubleName, value); }
	public double getDouble(String doubleName)
	{ return doubleMap.get(doubleName); }

	private Map<String, Integer> intMap = new HashMap<>();
	public void put(String intName, int value)
	{ intMap.put(intName, value); }
	public int getInt(String intName)
	{ return intMap.get(intName); }

	private Map<String, Boolean> booleanMap = new HashMap<>();
	public void put(String intName, boolean value)
	{ booleanMap.put(intName, value); }
	public Boolean getBoolean(String intName)
	{ return booleanMap.get(intName); }

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

	private Map<String, Shape> shapeMap = new HashMap<>();
	public void put(String shapeName, Shape shape)
	{ shapeMap.put(shapeName, shape); }
	public Shape getShape(String shapeName)
	{ return shapeMap.get(shapeName); }

	private Map<String, Pattern> patternMap = new HashMap<>();
	public void put(String patternName, Pattern pattern)
	{ patternMap.put(patternName, pattern); }
	public Pattern getPattern(String patternName)
	{ return patternMap.get(patternName); }

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

	private Map<String, PointLight> pointLightMap = new HashMap<>();
	public void put(String pointLightName, PointLight l)
	{ pointLightMap.put(pointLightName, l); }
	public PointLight getPointLight(String pointLightName)
	{ return pointLightMap.get(pointLightName); }

	private Map<String, Material> materialsMap = new HashMap<>();
	public void put(String materialName, Material material)
	{ materialsMap.put(materialName, material); }
	public Material getMaterial(String materialName)
	{ return materialsMap.get(materialName); }

	private Map<String, Canvas> canvasMap = new HashMap<>();
	public void put(String canvasName, Canvas canvas)
	{ canvasMap.put(canvasName, canvas); }
	public Canvas getCanvas(String canvasName)
	{ return canvasMap.get(canvasName); }

	private Map<String, List<String>> ppmMap = new HashMap<>();
	public void put(String ppmName, List<String> ppm)
	{ ppmMap.put(ppmName, ppm); }
	public List<String> getPpm(String ppmName)
	{ return ppmMap.get(ppmName); }

	private Map<String, World> worldMap = new HashMap<>();
	public void put(String worldName, World world)
	{ worldMap.put(worldName, world); }
	public World getWorld(String worldName)
	{ return worldMap.get(worldName); }

	private Map<String, IntersectionComputations> compsMap = new HashMap<>();
	public void put(String compsName, IntersectionComputations comps)
	{ compsMap.put(compsName, comps); }
	public IntersectionComputations getComputations(String compsName)
	{ return compsMap.get(compsName); }

	private Map<String, Camera> cameraMap = new HashMap<>();
	public void put(String cameraName, Camera camera)
	{ cameraMap.put(cameraName, camera); }
	public Camera getCamera(String cameraName)
	{ return cameraMap.get(cameraName); }

	private Map<String, Spliterator<PixelCoordinate>> pixelCoordinateSpliteratorMap = new HashMap<>();
	public void put(String spliteratorName, Spliterator<PixelCoordinate> spliterator)
	{ pixelCoordinateSpliteratorMap.put(spliteratorName, spliterator); }
	public Spliterator<PixelCoordinate> getPixelCoordinateSpliterator(String spliteratorName)
	{ return pixelCoordinateSpliteratorMap.get(spliteratorName); }

	private Map<String, PixelCoordinate> pixelCoordinateMap = new HashMap<>();
	public void put(String pixelCoordinateName, PixelCoordinate pixelCoordinate)
	{ pixelCoordinateMap.put(pixelCoordinateName, pixelCoordinate); }
	public PixelCoordinate getPixelCoordinate(String pixelCoordinateName)
	{ return pixelCoordinateMap.get(pixelCoordinateName); }
}