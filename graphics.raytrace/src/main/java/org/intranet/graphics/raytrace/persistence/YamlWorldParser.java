package org.intranet.graphics.raytrace.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.light.AreaLight;
import org.intranet.graphics.raytrace.light.PointLight;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Transformable;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Cone;
import org.intranet.graphics.raytrace.shape.Cube;
import org.intranet.graphics.raytrace.shape.Cylinder;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.RandomSequence;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.shape.Triangle;
import org.intranet.graphics.raytrace.shape.TubeLike;
import org.intranet.graphics.raytrace.surface.AlignCheckUvPattern;
import org.intranet.graphics.raytrace.surface.CheckerPattern;
import org.intranet.graphics.raytrace.surface.CheckersUvPattern;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.CylindricalUvMap;
import org.intranet.graphics.raytrace.surface.GradientPattern;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.PlanarUvMap;
import org.intranet.graphics.raytrace.surface.RingPattern;
import org.intranet.graphics.raytrace.surface.SphericalUvMap;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.intranet.graphics.raytrace.surface.TextureMapPattern;
import org.intranet.graphics.raytrace.surface.UvMap;
import org.intranet.graphics.raytrace.surface.UvPattern;

import com.esotericsoftware.yamlbeans.YamlReader;

public class YamlWorldParser
{
	Map<String, Material> materialDefines = new HashMap<>();
	Map<String, Shape> shapeDefines = new HashMap<>();
	Map<String, Matrix> xformDefines = new HashMap<>();

	private World world = new World();
	public World getWorld() { return world; }

	public YamlWorldParser(InputStream ymlStream, File relativeFolder)
	{
		defineFir();

		try (InputStreamReader ymlReader = new InputStreamReader(ymlStream))
		{
			YamlReader reader = new YamlReader(ymlReader);

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
						addObject(world, paramMap);
					}
					else if (paramMap.containsKey("define"))
					{
						define(world, paramMap, relativeFolder);
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
	}

	private void defineFir()
	{
		// the length of the branch
		double length = 2.0;

		// the radius of the branch
		double radius = 0.025;

		// how many groups of needles cover the branch
		int segments = 20;

		// how needles per group, or segment
		int per_segment = 24;

		// the branch itself, just a cylinder
		Cylinder branch = new Cylinder();
		branch.setMinimum(0);
		branch.setMaximum(length);
		branch.setClosed(true);
		branch.setTransform(Matrix.newScaling(radius, 1, radius));
		Material branchMaterial = new Material();
		branchMaterial.setColor(new Color(0.5, 0.35, 0.26));
		branchMaterial.setAmbient(0.2);
		branchMaterial.setSpecular(0.0);
		branchMaterial.setDiffuse(0.6);
		branch.setMaterial(branchMaterial);

		// how much branch each segment gets
		double seg_size = 1.0 * length / (segments - 1);

		// the radial distance, in radians, between adjacent needles
		// in a group
		double theta = 2.1 * Math.PI / per_segment;

		// the maximum length of each needle
		double max_length = 20.0 * radius;

		// the group that will contain the branch and all needles
		Group object = new Group();
		object.addChild(branch);

		for (int y = 0; y < segments; y++)
		{
			// create a subgroup for each segment of needles
			Group subgroup = new Group();

			for (int i = 0; i < per_segment; i++)
			{
				// each needle is a triangle.
				// y_base y coordinate of the base of the triangle
				double y_base = seg_size * y + rand() * seg_size;

				// y_tip is the y coordinate of the tip of the triangle
				double y_tip = y_base - rand() * seg_size;

				// y_angle is angle (in radians) that the needle should be
				// rotated around the branch.
				double y_angle = i * theta + rand() * theta;

				// how long is the needle?
				double needle_length = max_length / 2 * (1 + rand());

				// how much is the needle offset from the center of the branch?
				double ofs = radius / 2;

				// the three points of the triangle that form the needle
				Point p1 = new Point(ofs, y_base, ofs);
				Point p2 = new Point(-ofs, y_base, ofs);
				Point p3 = new Point(0.0, y_tip, needle_length);

				// create, transform, and texture the needle
				Triangle tri = new Triangle(p1, p2, p3);
				tri.setTransform(Matrix.newRotationY(y_angle));
				Material triMaterial = new Material();
				triMaterial.setColor(new Color(0.26, 0.36, 0.16));
				triMaterial.setSpecular(0.1);

				subgroup.addChild(tri);
			}

			object.addChild(subgroup);
		}

		shapeDefines.put("fir_branch", object);
	}

	private Random r = new Random(System.currentTimeMillis());
	private double rand()
	{
		return r.nextDouble();
	}

	private void define(World world, Map<String, Object> defineMap,
		File relativeFolder)
			throws FileNotFoundException, IOException
	{
		defineMap = new DestructiveHashMap<String, Object>(defineMap);
		String defineName = (String)defineMap.get("define");
		String extendName = (String)defineMap.get("extend");
		Object valueObj = defineMap.get("value");
		if (valueObj instanceof List)
		{
			defineTransform(defineName, extendName, valueObj);
		}
		else
		{
			@SuppressWarnings("unchecked")
			Map<String, Object> valueMap = (Map<String, Object>)valueObj;
			if (valueMap == null)
			{
				System.err.println("Missing value for define name=" + defineName);
				return;
			}
			valueMap = new DestructiveHashMap<String, Object>(valueMap);

			String type = (String)valueMap.get("add");

			if ("obj".equals(type))
			{
				defineObj(world, relativeFolder, defineName, valueMap);
			}
			else if (type != null)
			{
				defineShape(type, extendName, world, valueMap, defineName);
			}
			else
			{
				defineMaterial(defineName, extendName, valueMap);
			}

			if (valueMap.size() > 0)
				System.err.printf("Leftovers for define %s, values = %s\n", defineName, valueMap);
		}

		if (defineMap.size() > 0)
			System.err.printf("Leftovers for define %s = %s\n", defineName, defineMap);
	}

	private void defineTransform(String defineName, String extendName,
		Object valueObj)
	{
		@SuppressWarnings("unchecked")
		List<List<String>> transformList = (List<List<String>>)valueObj;
		Matrix definedTransform = parseManyXforms(transformList);

		Matrix extendedTransform = xformDefines.get(extendName);
		if (extendedTransform != null)
			definedTransform = definedTransform.multiply(extendedTransform);
		xformDefines.put(defineName, definedTransform);
	}

	private void defineMaterial(String defineName, String extendName,
		Map<String, Object> valueMap)
	{
		Material mat = null;
		if (extendName == null)
			mat = new Material();
		else
		{
			mat = materialDefines.get(extendName);
			if (mat == null)
			{
				System.err.printf("Unknown material extend name %s\n", extendName);
//						return;
			}
			mat = mat.duplicate();
		}

		if (valueMap != null)
		{
			parseRawMaterial(mat, valueMap);
			// Note: parseRawMaterial does its own values.
			materialDefines.put(defineName, mat);
		}
	}

	private void defineObj(World world, File relativeFolder, String defineName,
		Map<String, Object> valueMap) throws IOException, FileNotFoundException
	{
		String file = (String)valueMap.get("file");
		try (FileReader fr = new FileReader(new File(relativeFolder, file));
			BufferedReader br = new BufferedReader(fr))
		{
			List<String> lines = br.lines().collect(Collectors.toList());
			ObjFileParser parser = new ObjFileParser(lines);
			Shape shape = parser.getGroup();
			processShapeProperties(world, valueMap, shape);
			shapeDefines.put(defineName, shape);
		}
	}

	private void parseRawMaterial(Material mat,
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
				case "map":
					String uvMappingType = (String)patternMap.get("mapping");
					UvMap uvMapping = new SphericalUvMap();
					if ("planar".equals(uvMappingType))
						uvMapping = new PlanarUvMap();
					else if ("cylindrical".equals(uvMappingType))
						uvMapping = new CylindricalUvMap();
					else if (!"spherical".equals(uvMappingType))
						System.err.println("Unknown uv mapping type " + uvMappingType);

					@SuppressWarnings("unchecked")
					Map<String, Object> uvPatternAttribs = (Map<String, Object>)patternMap.get("uv_pattern");
					String uvPatternType = (String)uvPatternAttribs.get("type");
					UvPattern uvPattern;
					switch (uvPatternType)
					{
						case "align_check":
							@SuppressWarnings("unchecked") Map<String, List<String>> alignColors =
								(Map<String, List<String>>)uvPatternAttribs.get("colors");
							Color alignMain = listToColor(alignColors.get("main"));
							Color alignUl = listToColor(alignColors.get("ul"));
							Color alignUr = listToColor(alignColors.get("ur"));
							Color alignBl = listToColor(alignColors.get("bl"));
							Color alignBr = listToColor(alignColors.get("br"));
							uvPattern = new AlignCheckUvPattern(alignMain,
								alignUl, alignUr, alignBl, alignBr);
							break;
						case "checkers":
							int uSquares = stringToInt((String) uvPatternAttribs.get("width"));
							int vSquares = stringToInt((String) uvPatternAttribs.get("height"));
							@SuppressWarnings("unchecked")
							List<List<String>> uvColors = (List<List<String>>)uvPatternAttribs.get("colors");
							Color uvColor1 = listToColor(uvColors.get(0));
							Color uvColor2 = listToColor(uvColors.get(1));
							uvPattern = new CheckersUvPattern(uSquares, vSquares, uvColor1, uvColor2);
							break;
						default:
							System.err.println("Unknown uv pattern type="+uvPatternType);
							uvPattern = new UvPattern() {
								@Override
								public Color colorAt(double u, double v)
								{ return new Color(0, 1, 0); }
							};
					}

					pattern = new TextureMapPattern(uvPattern, uvMapping);
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

	private void addObject(World world, Map<String, Object> objMap)
		throws FileNotFoundException, IOException
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
				if (at != null)
				{
					@SuppressWarnings("unchecked")
					ArrayList<String> intensity = (ArrayList<String>)objMap.get("intensity");

					PointLight pointLight = new PointLight(listToPoint(at),
						listToColor(intensity));
					world.addLight(pointLight);
				}
				else
				{
					@SuppressWarnings("unchecked")
					ArrayList<String> corner = (ArrayList<String>)objMap.get("corner");
					@SuppressWarnings("unchecked")
					ArrayList<String> uvec = (ArrayList<String>)objMap.get("uvec");
					@SuppressWarnings("unchecked")
					ArrayList<String> vvec = (ArrayList<String>)objMap.get("vvec");
					int usteps = stringToInt((String)objMap.get("usteps"));
					int vsteps = stringToInt((String)objMap.get("vsteps"));
					@SuppressWarnings("unchecked")
					ArrayList<String> intensity = (ArrayList<String>)objMap.get("intensity");
					AreaLight areaLight = new AreaLight(listToPoint(corner),
						listToVector(uvec), usteps, listToVector(vvec), vsteps,
						listToColor(intensity));
					boolean jitter = "true".equals(objMap.get("jitter"));
					if (jitter)
						areaLight.setJitterBy(new RandomSequence());
					world.addLight(areaLight);
				}
				break;
			case "obj":
				@SuppressWarnings("unchecked")
				ArrayList<String> files = (ArrayList<String>)objMap.get("file");
				String file = files.get(0);
				try (FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);)
				{
					Stream<String> lines = br.lines();
					ObjFileParser parser = new ObjFileParser(lines);
					Shape shape = parser.getGroup();
					processShapeProperties(world, objMap, shape);
					world.addSceneObjects(shape);
				}
				break;
			default:
				if (!addShape(type, world, objMap))
				{
					System.err.println("Unknown shape type to add: " + type +
						": data=" + objMap);
				}
		}
		if (objMap.size() > 0)
			System.err.printf("Leftovers for type %s = %s\n", type, objMap);
	}

	public boolean addShape(String shapeName, World world,
		Map<String, Object> objMap)
	{
		Shape shape = createBasicShape(shapeName, null);
		if (shape == null)
		{
			shape = shapeDefines.get(shapeName);
//System.out.println("addShape: shapeName="+shapeName);
			if (shape != null)
				shape = shape.deepCopy();
		}
		if (shape == null)
			return false;

		processShapeProperties(world, objMap, shape);

		world.addSceneObjects(shape);
		return shape != null;
	}

	private void defineShape(String shapeName, String extendName,
		World world, Map<String, Object> objMap, String defineName)
	{
		Shape shape = createBasicShape(shapeName, extendName);

		if (shape != null)
			processShapeProperties(world, objMap, shape);

		shapeDefines.put(defineName, shape);
	}

	private Shape createBasicShape(String shapeName, String extendName)
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
		return shape;
	}

	private Shape processShapeProperties(World world,
		Map<String, Object> objMap, Shape shape)
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
			setMaterialForShape(shape, shapeMaterial);

		String shadow = (String)objMap.get("shadow");
		if (shadow != null)
		{
			boolean castShadow = "true".equals(shadow);
			shape.setCastShadow(castShadow);
		}

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
			List<Map<String, Object>> childrenPropMapList =
				(List<Map<String, Object>>)objMap.get("children");
			if (childrenPropMapList != null)
			{
				for (Map<String, Object> childPropMap : childrenPropMapList)
				{
					String shapeName = (String)childPropMap.get("add");
					Shape childShape = createBasicShape(shapeName, null);
					if (childShape == null)
					{
						childShape = shapeDefines.get(shapeName);
						if (childShape != null)
							childShape = childShape.deepCopy();
					}
					if (childShape != null)
					{
						processShapeProperties(world, childPropMap, childShape);
						group.addChild(childShape.deepCopy());
					}
				}
			}
		}

		return shape;
	}

	private void setMaterialForShape(Shape s, Object materialData)
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

	private void parseTransform(Transformable xform,
		List<List<String>> object)
	{
		Matrix originalTransform = xform.getTransform();
		Matrix newXform = parseManyXforms(object);
		xform.setTransform(newXform.multiply(originalTransform));
	}

	private Matrix parseManyXforms(List<?> object)
	{
		Matrix newXform = null;
		for (Object obj : object)
		{
			Matrix mtxForRow = null;
			if (obj instanceof List)
			{
				@SuppressWarnings("unchecked")
				List<String> stringList = (List<String>)obj;
				mtxForRow = parseXform(stringList);
			}
			else
			{
				mtxForRow = xformDefines.get(obj);
			}
			if (newXform == null)
				newXform = mtxForRow;
			else
				newXform = mtxForRow.multiply(newXform);
		}
		return newXform;
	}

	private static Matrix parseXform(List<String> obj)
	{
		String transformType = obj.get(0);
		switch (transformType)
		{
			case "scale":
				return
					Matrix.newScaling(
						stringToDbl(obj.get(1)),
						stringToDbl(obj.get(2)),
						stringToDbl(obj.get(3))
					);
			case "translate":
				return Matrix.newTranslation(
						stringToDbl(obj.get(1)),
						stringToDbl(obj.get(2)),
						stringToDbl(obj.get(3))
					);
			case "rotate-z":
				return Matrix.newRotationZ(
						stringToDbl(obj.get(1))
					);
			case "rotate-x":
				return Matrix.newRotationX(
						stringToDbl(obj.get(1))
					);
			case "rotate-y":
				return Matrix.newRotationY(
						stringToDbl(obj.get(1))
					);
			default:
				System.err.println("Unknown transform property " + transformType);
				return null;
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