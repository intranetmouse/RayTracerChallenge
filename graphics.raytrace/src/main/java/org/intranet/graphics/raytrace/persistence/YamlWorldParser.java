package org.intranet.graphics.raytrace.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Transformable;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Cone;
import org.intranet.graphics.raytrace.shape.Cube;
import org.intranet.graphics.raytrace.shape.Cylinder;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.shape.TubeLike;
import org.intranet.graphics.raytrace.surface.CheckerPattern;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.GradientPattern;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.RingPattern;
import org.intranet.graphics.raytrace.surface.StripePattern;

import com.esotericsoftware.yamlbeans.YamlReader;

public class YamlWorldParser
{
	public YamlWorldParser()
	{ }

	public World parse(InputStream ymlStream)
	{
		World world = new World();

		try (InputStreamReader ymlReader = new InputStreamReader(ymlStream))
		{
			YamlReader reader = new YamlReader(ymlReader);

			Map<String, Material> materialDefines = new HashMap<>();
			Map<String, Shape> shapeDefines = new HashMap<>();
			while (true)
			{
				@SuppressWarnings({ "unchecked" })
				ArrayList<Map<String, Object>> commands = (ArrayList<Map<String, Object>>)reader.read();
				if (commands == null)
					break;

				for (Map<String, Object> paramMap : commands)
				{
					if (paramMap.containsKey("add"))
					{
						addObject(world, paramMap, materialDefines, shapeDefines);
					}
					else if (paramMap.containsKey("define"))
					{
						define(world, paramMap, materialDefines, shapeDefines);
					}
					else
					{
						System.err.println("unknown object map: " + paramMap);
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return world;
	}

	private void define(World world, Map<String, Object> defineMap,
		Map<String, Material> materialDefines, Map<String, Shape> shapeDefines)
	{
		defineMap = new DestructiveHashMap<String, Object>(defineMap);
		String defineName = (String)defineMap.get("define");
		String extendName = (String)defineMap.get("extend");
		@SuppressWarnings("unchecked")
		Map<String, Object> valueMap = (Map<String, Object>)defineMap.get("value");
		if (valueMap == null)
		{
			System.err.println("Missing value for define name=" + defineName);
			return;
		}
		valueMap = new DestructiveHashMap<String, Object>(valueMap);

		String type = (String)valueMap.get("add");

		if (type != null)
		{
			Shape s = createShape(type, extendName, world, valueMap,
				materialDefines, shapeDefines);
			shapeDefines.put(defineName, s);

			if (valueMap.size() > 0)
				System.err.printf("Leftovers for define %s, values = %s\n", defineName, valueMap);
		}
		else
		{
			Material mat = extendName == null ? new Material() :
				materialDefines.get(extendName).duplicate();

			if (valueMap != null)
			{
				parseRawMaterial(mat, valueMap);
				// Note: parseRawMaterial does its own values.
				materialDefines.put(defineName, mat);
			}
		}

		if (defineMap.size() > 0)
			System.err.printf("Leftovers for define %s = %s\n", defineName, defineMap);
	}

	private static void parseRawMaterial(Material mat,
		Map<String, Object> materialMap)
	{
		materialMap = new DestructiveHashMap<>(materialMap);

		String ambient = (String)materialMap.get("ambient");
		if (ambient != null)
			mat.setAmbient(stringToDbl(ambient));

		String diffuse = (String)materialMap.get("diffuse");
		if (diffuse != null)
			mat.setDiffuse(stringToDbl(diffuse));

		String specular = (String)materialMap.get("specular");
		if (specular != null)
			mat.setSpecular(stringToDbl(specular));

		String shininess = (String)materialMap.get("shininess");
		if (shininess != null)
			mat.setShininess(stringToDbl(shininess));

		String transparency = (String)materialMap.get("transparency");
		if (transparency != null)
			mat.setTransparency(stringToDbl(transparency));

		String reflective = (String)materialMap.get("reflective");
		if (reflective != null)
			mat.setReflective(stringToDbl(reflective));

		String refractive = (String)materialMap.get("refractive");
		if (refractive != null)
			mat.setRefractive(stringToDbl(refractive));
		refractive = (String)materialMap.get("refractive-index");
		if (refractive != null)
			mat.setRefractive(stringToDbl(refractive));

		@SuppressWarnings("unchecked")
		List<String> colorsLst = (List<String>)materialMap.get("color");
		if (colorsLst != null)
			mat.setColor(listToColor(colorsLst));

		@SuppressWarnings("unchecked")
		Map<Object, Object> patternMap = (Map<Object, Object>)materialMap.get("pattern");
		if (patternMap != null)
		{
			patternMap = new DestructiveHashMap<>(patternMap);
//System.out.println("YamlWorldParser.parseRawMaterial: Got pattern="+patternMap);
			String patternType = (String)patternMap.get("type");
			Pattern pattern = null;
			switch (patternType)
			{
				case "stripes":
				case "checker":
				case "checkers":
				case "gradient":
				case "ring":
					@SuppressWarnings("unchecked")
					List<List<String>> colors = (List<List<String>>)patternMap.get("colors");
//System.out.println("YamlWorldParser.parseRawMaterial: Got colors="+colors);

					Color color1 = listToColor(colors.get(0));
					Color color2 = listToColor(colors.get(1));
					pattern = "stripes".equals(patternType) ?
						new StripePattern(color1, color2) :
						"gradient".equals(patternType) ?
						new GradientPattern(color1, color2) :
						"ring".equals(patternType) ?
						new RingPattern(color1, color2) :
						new CheckerPattern(color1, color2);
					mat.setPattern(pattern);
					break;
				default:
					System.err.println("Unknown pattern type " + patternType);
			}
			if (pattern != null)
			{
				@SuppressWarnings("unchecked")
				List<List<String>> patternTransform =
					(List<List<String>>)patternMap.get("transform");
				if (patternTransform != null)
					parseTransform(pattern, patternTransform);
			}

			if (patternMap.size() > 0)
				System.err.println("YamlWorldParser.parseRawMaterial: pattern leftovers="+patternMap);
		}

		if (materialMap.size() > 0)
			System.err.printf("Leftovers for material: %s\n", materialMap);
	}

	private static void addObject(World world, Map<String, Object> objMap,
		Map<String, Material> materialDefines, Map<String, Shape> shapeDefines)
	{
		objMap = new DestructiveHashMap<String, Object>(objMap);
		String type = (String)objMap.get("add");
		if (!addShape(type, world, objMap, materialDefines, shapeDefines))
		{
			switch (type)
			{
				case "camera":
					String width = (String)objMap.get("width");
					String height = (String)objMap.get("height");
					String fieldOfView = (String)objMap.get("field-of-view");
					Camera camera = new Camera(stringToInt(width),
						stringToInt(height), stringToDbl(fieldOfView));

					@SuppressWarnings("unchecked")
					ArrayList<String> from = (ArrayList<String>)objMap.get("from");
					@SuppressWarnings("unchecked")
					ArrayList<String> to = (ArrayList<String>)objMap.get("to");
					@SuppressWarnings("unchecked")
					ArrayList<String> up = (ArrayList<String>)objMap.get("up");
					Matrix mtx = Matrix.newView(listToPoint(from), listToPoint(to),
						listToVector(up));
					camera.setTransform(mtx);
					world.setCamera(camera);
					break;
				case "light":
					@SuppressWarnings("unchecked")
					ArrayList<String> at = (ArrayList<String>)objMap.get("at");
					@SuppressWarnings("unchecked")
					ArrayList<String> intensity = (ArrayList<String>)objMap.get("intensity");

					PointLight pointLight = new PointLight(listToPoint(at),
						listToColor(intensity));
					world.addLight(pointLight);
					break;
				default:
					System.err.println("Unknown shape type to add: " + type +
						": data=" + objMap);
			}
		}
		if (objMap.size() > 0)
			System.err.printf("Leftovers for type %s = %s\n", type, objMap);
	}

	public static boolean addShape(String shapeName, World world,
		Map<String, Object> objMap, Map<String, Material> materialDefines,
		Map<String, Shape> shapeDefines)
	{
		Shape shape = createShape(shapeName, null, world, objMap,
			materialDefines, shapeDefines);
		if (shape == null)
		{
			shape = shapeDefines.get(shapeName);
//System.out.println("addShape: shapeName="+shapeName);
			if (shape != null)
			{
				shape = shape.deepCopy();
				@SuppressWarnings("unchecked")
				Map<String, Object> objectValues =
					(Map<String, Object>)objMap.get("value");
//System.out.println("  objMap map="+objMap);
				processShapeProperties(world,
					objectValues, materialDefines,
					shapeDefines, shape);
			}
		}

		if (shape != null)
			world.addSceneObjects(shape);
		return shape != null;
	}

	private static Shape createShape(String shapeName, String extendName,
		World world, Map<String, Object> objMap,
		Map<String, Material> materialDefines, Map<String, Shape> shapeDefines)
	{
		Shape shape =
			extendName != null ? shapeDefines.get(extendName).deepCopy() :
			shapeName.equals("plane") ? new Plane() :
			shapeName.equals("sphere") ? new Sphere() :
			shapeName.equals("cube") ? new Cube() :
			shapeName.equals("cylinder") ? new Cylinder() :
			shapeName.equals("cone") ? new Cone() :
			shapeName.equals("group") ? new Group() :
			null;
		if (shape == null)
		{
			return null;
		}

		processShapeProperties(world, objMap, materialDefines, shapeDefines, shape);
		return shape;
	}

	private static Shape processShapeProperties(World world,
		Map<String, Object> objMap, Map<String, Material> materialDefines,
		Map<String, Shape> shapeDefines, Shape shape)
	{
		if (objMap == null)
			return shape;
		@SuppressWarnings("unchecked")
		List<List<String>> shapeTransform =
			(List<List<String>>)objMap.get("transform");
		if (shapeTransform != null)
			parseTransform(shape, shapeTransform);

		Object shapeMaterial = objMap.get("material");
		if (shapeMaterial != null)
		setMaterialForShape(shape, shapeMaterial, materialDefines);

		if (shape instanceof TubeLike)
		{
			String min = (String)objMap.get("min");
			TubeLike tubelike = (TubeLike)shape;
			if (min != null)
				tubelike.setMinimum(Double.parseDouble(min));
			String max = (String)objMap.get("max");
			if (max != null)
				tubelike.setMaximum(Double.parseDouble(max));
			String closedString = (String)objMap.get("closed");
			if (closedString != null)
				tubelike.setClosed("true".equals(closedString));
		}
		if (shape instanceof Group)
		{
			Group group = (Group)shape;
			@SuppressWarnings("unchecked")
			List<Map<Object, Object>> childrenPropMapList =
				(List<Map<Object, Object>>)objMap.get("children");
			if (childrenPropMapList != null)
			{
				for (Map<Object, Object> childPropMap : childrenPropMapList)
				{
					String shapeName = (String)childPropMap.get("add");
					Shape childShape = createShape(shapeName, null, world,
						objMap, materialDefines, shapeDefines);
					if (childShape == null)
					{
						childShape = shapeDefines.get(shapeName);
						if (childShape != null)
						{
							childShape = shape.deepCopy();
							processShapeProperties(world, objMap,
								materialDefines, shapeDefines, childShape);
						}
					}
					if (childShape != null)
						group.addChild(childShape.deepCopy());
				}
			}
		}

		return shape;
	}

	private static void setMaterialForShape(Shape s, Object materialData,
		Map<String, Material> materialDefines)
	{
		if (materialData instanceof String)
		{
			Material mat = materialDefines.get(materialData);
			if (mat == null)
				System.err.printf("Material %s not found\n", materialData);
			else
				s.setMaterial(mat);
		}
		else if (materialData instanceof Map)
		{
			@SuppressWarnings("unchecked")
			Map<String, Object> materialMap =
				(Map<String, Object>)materialData;
			Material mat = s.getMaterial().duplicate();
			parseRawMaterial(mat, materialMap);
			s.setMaterial(mat);
		}
	}

	private static void parseTransform(Transformable xform,
		List<List<String>> object)
	{
		for (List<String> obj : object)
		{
			String transformType = obj.get(0);
			Matrix originalTransform = xform.getTransform();
			switch (transformType)
			{
				case "scale":
					xform.setTransform(
						Matrix.newScaling(
							stringToDbl(obj.get(1)),
							stringToDbl(obj.get(2)),
							stringToDbl(obj.get(3))
						).multiply(originalTransform)
					);
					break;
				case "translate":
					xform.setTransform(
						Matrix.newTranslation(
							stringToDbl(obj.get(1)),
							stringToDbl(obj.get(2)),
							stringToDbl(obj.get(3))
						).multiply(originalTransform)
					);
					break;
				case "rotate-z":
					xform.setTransform(
						Matrix.newRotationZ(
							stringToDbl(obj.get(1))
						).multiply(originalTransform)
					);
					break;
				case "rotate-x":
					xform.setTransform(
						Matrix.newRotationX(
							stringToDbl(obj.get(1))
						).multiply(originalTransform)
					);
					break;
				case "rotate-y":
					xform.setTransform(
						Matrix.newRotationY(
							stringToDbl(obj.get(1))
						).multiply(originalTransform)
					);
					break;
				default:
					System.err.println("Unknown transform property " + transformType);
			}
		}
	}

	static int stringToInt(String s)
	{
		return Integer.valueOf(s);
	}

	static double stringToDbl(String s)
	{
		return Double.valueOf(s);
	}

	static Point listToPoint(ArrayList<String> s)
	{
		return new Point(stringToDbl(s.get(0)), stringToDbl(s.get(1)),
			stringToDbl(s.get(2)));
	}

	static Color listToColor(List<String> s)
	{
		return new Color(stringToDbl(s.get(0)), stringToDbl(s.get(1)),
			stringToDbl(s.get(2)));
	}

	static Vector listToVector(List<String> s)
	{
		return new Vector(stringToDbl(s.get(0)), stringToDbl(s.get(1)),
			stringToDbl(s.get(2)));
	}

	static Point stringToPoint(String s)
	{
		s = s.substring(1, s.length() - 2);
		String[] values = s.split(",\\s*");
		return new Point(stringToDbl(values[0]), stringToDbl(values[1]),
			stringToDbl(values[2]));
	}

	static Vector stringToVector(String s)
	{
		s = s.substring(1, s.length() - 2);
		String[] values = s.split(",\\s*");
		return new Vector(stringToDbl(values[0]), stringToDbl(values[1]),
			stringToDbl(values[2]));
	}
}