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
import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.persistence.ObjFileParser;
import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Sequence;
import org.intranet.graphics.raytrace.shape.TubeLike;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.UvPattern;
import org.junit.Assert;

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

	private Map<String, BoundingBox> boundingBoxMap = new HashMap<>();
	public void put(String boundingBoxName, BoundingBox box)
	{ boundingBoxMap.put(boundingBoxName, box); }
	public BoundingBox getBoundingBox(String boundingBoxName)
	{ return boundingBoxMap.get(boundingBoxName); }

	private Map<String, Color> colorMap = new HashMap<>();
	public void put(String colorName, Color color)
	{ colorMap.put(colorName, color); }
	public Color getColor(String colorName)
	{ return colorMap.get(colorName); }

	private Map<String, Sequence> sequenceMap = new HashMap<>();
	public void putSequence(String sequenceName, Sequence color)
	{ sequenceMap.put(sequenceName, color); }
	public Sequence getSequence(String sequenceName)
	{ return sequenceMap.get(sequenceName); }

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
	public TubeLike getTubelike(String shapeName)
	{
		Shape shape = shapeMap.get(shapeName);
		if (!(shape instanceof TubeLike))
			Assert.fail("Shape " + shapeName + " is not TubeLike");
		return (TubeLike)shape;
	}

	private Map<String, List<Shape>> shapeListMap = new HashMap<>();
	public void putShapeList(String shapeListName, List<Shape> shapes)
	{ shapeListMap.put(shapeListName, shapes); }
	public List<Shape> getShapeList(String shapeListName)
	{ return shapeListMap.get(shapeListName); }

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
	{ Assert.assertNotNull(ilist); intersectionListsMap.put(intersectionListName, ilist); }
	public IntersectionList getIntersectionList(String intersectionListName)
	{ return intersectionListsMap.get(intersectionListName); }

	private Map<String, Light> lightMap = new HashMap<>();
	public void put(String lightName, Light l)
	{ lightMap.put(lightName, l); }
	public Light getLight(String lightName)
	{ return lightMap.get(lightName); }

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

	private Map<String, List<String>> stringListMap = new HashMap<>();
	public void put(String stringListName, List<String> stringList)
	{ stringListMap.put(stringListName, stringList); }
	public List<String> getStringList(String stringListName)
	{ return stringListMap.get(stringListName); }

	private Map<String, String> stringMap = new HashMap<>();
	public void put(String stringName, String string)
	{ stringMap.put(stringName, string); }
	public String getString(String stringName)
	{ return stringMap.get(stringName); }

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

	private Map<String, ObjFileParser> objFileParserMap = new HashMap<>();
	public void put(String objFileParseName, ObjFileParser parser)
	{ objFileParserMap.put(objFileParseName, parser); }
	public ObjFileParser getObjParser(String objFileParserName)
	{ return objFileParserMap.get(objFileParserName); }

	private Map<String, UvPattern> uvPattern = new HashMap<>();
	public void put(String uvMapName, UvPattern value)
	{ uvPattern.put(uvMapName, value); }
	public UvPattern getUvPattern(String uvMapName)
	{ return uvPattern.get(uvMapName); }
}