package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Transformable;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.CheckerPattern;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.GradientPattern;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.RingPattern;
import org.intranet.graphics.raytrace.surface.StripePattern;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class YamlWorldParser
{
	public YamlWorldParser()
	{ }

	public World parse(InputStream ymlStream)
	{
		World world = new World();

		InputStreamReader ymlReader = new InputStreamReader(ymlStream);
		YamlReader reader = new YamlReader(ymlReader);

		try
		{
			Map<String, Material> materialDefines = new HashMap<>();
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
						addObject(world, paramMap, materialDefines);
					}
					else if (paramMap.containsKey("define"))
					{
						define(world, paramMap, materialDefines);
					}
					else
					{
						System.err.println("unknown object map: " + paramMap);
					}
				}
			}
		}
		catch (YamlException e)
		{
			e.printStackTrace();
		}
		return world;
	}

	private void define(World world, Map<String, Object> defineMap,
		Map<String, Material> materialDefines)
	{
		defineMap = new DestructiveHashMap<String, Object>(defineMap);
		String name = (String)defineMap.get("define");

		if (!name.endsWith("-material"))
		{
			System.err.println("Unknown define name: " + name);
			return;
		}

		String extendKey = (String)defineMap.get("extend");
		Material mat = extendKey == null ? new Material() :
				materialDefines.get(extendKey).duplicate();

		@SuppressWarnings("unchecked")
		Map<String, Object> valueMap = (Map<String, Object>)defineMap.get("value");
		if (valueMap != null)
		{
			parseRawMaterial(mat, valueMap);

			materialDefines.put(name, mat);
		}
		else
		{
		}

		if (defineMap.size() > 0)
			System.err.printf("Leftovers for define %s = %s\n", name, defineMap);
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

		String reflective = (String)materialMap.get("reflective");
		if (reflective != null)
			mat.setReflective(stringToDbl(reflective));

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
			System.err.printf("Leftovers for material: %s", materialMap);
	}

	private static void addObject(World world, Map<String, Object> objMap,
		Map<String, Material> materialDefines)
	{
		objMap = new DestructiveHashMap<String, Object>(objMap);
		String type = (String)objMap.get("add");
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
			case "sphere":
				Sphere sphere = new Sphere();
				@SuppressWarnings("unchecked")
				List<List<String>> sphereTransform =
					(List<List<String>>)objMap.get("transform");
				if (sphereTransform != null)
					parseTransform(sphere, sphereTransform);

				Object sphereMaterial = objMap.get("material");
				setMaterialForShape(sphere, sphereMaterial, materialDefines);

				world.addSceneObjects(sphere);
				break;
			case "plane":
				Plane plane = new Plane();
				@SuppressWarnings("unchecked")
				List<List<String>> planeTransform =
					(List<List<String>>)objMap.get("transform");
				if (planeTransform != null)
					parseTransform(plane, planeTransform);

				Object planeMaterial = objMap.get("material");
				setMaterialForShape(plane, planeMaterial, materialDefines);

				world.addSceneObjects(plane);
				break;
			default:
				System.err.println("Unknown object type to add: " + type +
					": data=" + objMap);
		}
		if (objMap.size() > 0)
			System.err.printf("Leftovers for type %s = %s\n", type, objMap);
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