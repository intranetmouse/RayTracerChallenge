package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.shape.DefaultWorld;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class WorldSteps
	extends StepsParent
{
	public WorldSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← world\\(\\)")
	public void wWorld(String worldName)
	{
		World w = new World();
		data.put(worldName, w);
	}

	@Given(wordPattern + " ← (sphere|plane)\\(\\) with:")
	public void sSphereWith(String sphereName, String shapeName,
		DataTable dataTable)
	{
		Shape shape = "sphere".equals(shapeName) ? new Sphere() : new Plane();
		setShapePropertiesFromDataTable(dataTable, shape);
		data.put(sphereName, shape);
	}

	public static void setShapePropertiesFromDataTable(DataTable dataTable, Shape shape)
	{
		Material material = shape.getMaterial();
		for (List<String> strings : dataTable.asLists())
		{
			String property = strings.get(0);
			String value = strings.get(1);
			switch (property)
			{
				case "transform":
					String[] args = value.split("\\(");
					switch (args[0])
					{
						case "scaling":
							String scalingValue = args[1].replaceAll("[\\) ]", "");
							String[] scalingValues = scalingValue.split(",");
							double scaleX = Double.parseDouble(scalingValues[0]);
							double scaleY = Double.parseDouble(scalingValues[1]);
							double scaleZ = Double.parseDouble(scalingValues[2]);
							shape.setTransform(Matrix.newScaling(scaleX, scaleY, scaleZ));
							break;
						case "translation":
							String translationValue = args[1].replaceAll("[\\) ]", "");
							String[] translationValues = translationValue.split(",");
							double xlateX = Double.parseDouble(translationValues[0]);
							double xlateY = Double.parseDouble(translationValues[1]);
							double xlateZ = Double.parseDouble(translationValues[2]);
							shape.setTransform(Matrix.newTranslation(xlateX, xlateY, xlateZ));
							break;
						default:
							throw new cucumber.api.PendingException("Unknown sphere transform property " + args[0]);
					}
					break;
				case "material.color":
					value = value.replaceAll("[() ]", "");
					String[] values = value.split(",");
					double red = Double.parseDouble(values[0]);
					double green = Double.parseDouble(values[1]);
					double blue = Double.parseDouble(values[2]);
					material.setColor(new Color(red, green, blue));
					break;
				case "material.diffuse":
					double diffuse = Double.parseDouble(value);
					material.setDiffuse(diffuse);
					break;
				case "material.specular":
					double specular = Double.parseDouble(value);
					material.setSpecular(specular);
					break;
				case "material.reflective":
					double reflective = Double.parseDouble(value);
					material.setReflective(reflective);
					break;
				case "material.refractive_index":
					double refractiveIndex = Double.parseDouble(value);
					material.setRefractive(refractiveIndex);
					break;
				default:
					throw new cucumber.api.PendingException("Unknown sphere property " + property);
			}
		}
	}

	@Given(wordPattern + " ← the (first|second) object in " + wordPattern)
	public void assignNthObjectInWorldToObj(String objectName, String order,
		String worldName)
	{
		World world = data.getWorld(worldName);
		int sceneObjIdx = "first".equals(order) ? 0 : 1;
		Shape firstObject = world.getSceneObjects().get(sceneObjIdx);
		data.put(objectName, firstObject);
	}

	@Given("^" + wordPattern + "\\." + wordPattern + " ← point_light\\(point\\(" +
		threeDoublesPattern + "\\), color\\(" + threeDoublesPattern + "\\)\\)$")
	public void worldSetLightToPointLight(String worldName, String propertyName,
		double pointX, double pointY, double pointZ, double red, double green,
		double blue)
	{
		Assert.assertEquals("Only light is supported property name", "light",
			propertyName);

		Point position = new Point(pointX, pointY, pointZ);
		Color color = new Color(red, green, blue);
		PointLight pointLight = new PointLight(position, color);
		World world = data.getWorld(worldName);
		world.getLightSources().clear();
		world.getLightSources().add(pointLight);
	}

	@Given(wordPattern + " is added to " + wordPattern)
	public void sIsAddedToW(String objName, String worldName)
	{
		Shape obj = data.getShape(objName);
		World world = data.getWorld(worldName);
		world.getSceneObjects().add(obj);
	}


	@When(wordPattern + " ← default_world\\(\\)")
	public void wDefault_world(String worldName)
	{
		World w = DefaultWorld.defaultWorld();
		data.put(worldName, w);
	}

	@When(wordPattern + " ← intersect_world\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void xsIntersect_worldWR(String intersectionListName,
		String worldName, String rayName)
	{
		World w = data.getWorld(worldName);
		Ray r = data.getRay(rayName);

		IntersectionList il = w.intersect(r);

		data.put(intersectionListName, il);
	}

	@When(wordPattern + " ← shade_hit\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void cShade_hitWComps(String colorName, String worldName,
		String intersectionComputationsName)
	{
		World world = data.getWorld(worldName);
		IntersectionComputations comps = data.getComputations(
			intersectionComputationsName);

		Color c = comps.shadeHit(world, Camera.MAX_REFLEXION_RECURSION);

		data.put(colorName, c);
	}

	@When(wordPattern + " ← reflected_color\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void colorReflected_colorWComps(String colorName, String worldName,
		String intersectionComputationsName)
	{
		World world = data.getWorld(worldName);
		IntersectionComputations comps = data.getComputations(
			intersectionComputationsName);

		Color c = Tracer.reflectedColor(world, comps, Camera.MAX_REFLEXION_RECURSION);

		data.put(colorName, c);
	}

	@When(wordPattern + " ← color_at\\(" + wordPattern + ", " + wordPattern + "\\)")
	public void cColor_atWR(String colorName, String worldName, String rayName)
	{
		World world = data.getWorld(worldName);
		Ray ray = data.getRay(rayName);

		Color color = Tracer.colorAt(world, ray, Camera.MAX_REFLEXION_RECURSION);
		data.put(colorName, color);
	}


	@Then(wordPattern + " contains no objects")
	public void wContainsNoObjects(String worldName)
	{
		World w = data.getWorld(worldName);
		List<Shape> lightSources = w.getSceneObjects();
		Assert.assertEquals(0, lightSources.size());
	}

	@Then(wordPattern + " has no light source")
	public void wHasNoLightSource(String worldName)
	{
		World w = data.getWorld(worldName);
		List<Light> lightSources = w.getLightSources();
		Assert.assertEquals(0, lightSources.size());
	}

	@Then(wordPattern + " contains " + wordPattern)
	public void wContainsS(String worldName, String objectName)
	{
		World world = data.getWorld(worldName);
		Shape object = data.getShape(objectName);
		List<Shape> sceneObjects = world.getSceneObjects();
		boolean contains = sceneObjects.contains(object);
		Assert.assertTrue(contains);
	}

	@Then("^is_shadowed\\(" + wordPattern + ", " + wordPattern
		+ "\\) is (true|false)")
	public void is_shadowedWPIsFalse(String worldName, String pointName,
		String expectedResultStr)
	{
		World world = data.getWorld(worldName);
		Point point = data.getPoint(pointName);
		boolean actualResult = Tracer.isShadowed(world, point);
		boolean expectedResult = "true".equals(expectedResultStr);
		Assert.assertEquals(expectedResult, actualResult);
	}

	@When(wordPattern + " ← reflected_color\\(" + wordPattern + ", " + wordPattern + ", " + intPattern + "\\)")
	public void colorReflected_colorWComps(String colorName, String worldName, String compsName, Integer remaining)
	{
		World world = data.getWorld(worldName);
		IntersectionComputations comps = data.getComputations(compsName);
		Color color = Tracer.reflectedColor(world, comps, remaining);
		data.put(colorName, color);
	}
}
