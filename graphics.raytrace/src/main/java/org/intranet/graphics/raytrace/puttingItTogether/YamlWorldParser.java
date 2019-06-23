package org.intranet.graphics.raytrace.puttingItTogether;

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
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;

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

	private static void parseRawMaterial(Material mat, Map<String, Object> valueMap)
	{
		String ambient = (String)valueMap.get("ambient");
		if (ambient != null)
			mat.setAmbient(stringToDbl(ambient));

		String diffuse = (String)valueMap.get("diffuse");
		if (diffuse != null)
			mat.setDiffuse(stringToDbl(diffuse));

		String specular = (String)valueMap.get("specular");
		if (specular != null)
			mat.setSpecular(stringToDbl(specular));

		String shininess = (String)valueMap.get("shininess");
		if (shininess != null)
			mat.setShininess(stringToDbl(shininess));

		@SuppressWarnings("unchecked")
		List<String> colorsLst = (List<String>)valueMap.get("color");
		if (colorsLst != null)
			mat.setColor(listToColor(colorsLst));
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
				Camera camera = new Camera(stringToInt(width), stringToInt(height),
					stringToDbl(fieldOfView));

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
				Sphere s = new Sphere();
				@SuppressWarnings("unchecked")
				ArrayList<ArrayList<String>> transformData =
					(ArrayList<ArrayList<String>>)objMap.get("transform");
				if (transformData != null)
					parseTransform(s, transformData);
				Object materialObj = objMap.get("material");
				if (materialObj instanceof String)
				{
					Material mat = materialDefines.get(materialObj);
					if (mat == null)
					{
						System.err.printf("Material %s not found\n", materialObj);
						break;
					}
					s.setMaterial(mat);
				}
				else if (materialObj instanceof Map)
				{
					@SuppressWarnings("unchecked")
					Map<String, Object> materialData =
						(Map<String, Object>)materialObj;
					Material mat = s.getMaterial().duplicate();
					parseRawMaterial(mat, materialData);
					s.setMaterial(mat);
				}

				world.addSceneObjects(s);
				break;
			default:
				System.err.println("Unknown object type to add: " + type +
					": data=" + objMap);
		}
		if (objMap.size() > 0)
			System.err.printf("Leftovers for type %s = %s\n", type, objMap);
	}

	private static void parseTransform(Shape s, ArrayList<ArrayList<String>> object)
	{
		for (ArrayList<String> obj : object)
		{
			String transformType = obj.get(0);
			Matrix originalTransform = s.getTransform();
			switch (transformType)
			{
				case "scale":
					s.setTransform(
						Matrix.newScaling(
							stringToDbl(obj.get(1)),
							stringToDbl(obj.get(2)),
							stringToDbl(obj.get(3))
						).multiply(originalTransform)
					);
					break;
				case "translate":
					s.setTransform(
						Matrix.newTranslation(
							stringToDbl(obj.get(1)),
							stringToDbl(obj.get(2)),
							stringToDbl(obj.get(3))
						).multiply(originalTransform)
					);
					break;
				case "rotate-z":
					s.setTransform(
						Matrix.newRotationZ(
							stringToDbl(obj.get(1))
						).multiply(originalTransform)
					);
					break;
				case "rotate-x":
					s.setTransform(
						Matrix.newRotationX(
							stringToDbl(obj.get(1))
						).multiply(originalTransform)
					);
					break;
				case "rotate-y":
					s.setTransform(
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