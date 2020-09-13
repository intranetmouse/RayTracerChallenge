package org.intranet.graphics.raytrace.steps;

import java.util.Arrays;
import java.util.List;

import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Lighting;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.light.PointLight;
import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.When.Whens;

public class WorldSteps
	extends StepsParent
{
	public WorldSteps(RaytraceData data)
	{
		super(data);
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
							throw new PendingException("Unknown sphere transform property " + args[0]);
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
				case "material.transparency":
					double transparency = Double.parseDouble(value);
					material.setTransparency(transparency);
					break;
				case "material.ambient":
					double ambient = Double.parseDouble(value);
					material.setAmbient(ambient);
					break;
				case "material.refractive_index":
					double refractiveIndex = Double.parseDouble(value);
					material.setRefractive(refractiveIndex);
					break;
				case "material.pattern":
					if (!"test_pattern()".equals(value))
						return;
					material.setPattern(new TestPattern());
					break;
				default:
					throw new PendingException("Unknown shape property " + property);
			}
		}
	}

	@Given("{identifier} ← the first object in {identifier}")
	public void assignFirstObjectInWorldToObj(String objectName,
		String worldName)
	{
		assignNthObjectToObj(objectName, 0, worldName);
	}

	@Given("{identifier} ← the second object in {identifier}")
	public void assignSecondObjectInWorldToObj(String objectName,
		String worldName)
	{
		assignNthObjectToObj(objectName, 1, worldName);
	}

	private void assignNthObjectToObj(String objectName, int sceneObjIdx,
		String worldName)
	{
		World world = data.getWorld(worldName);
		Shape firstObject = world.getSceneObjects().get(sceneObjIdx);
		data.putShape(objectName, firstObject);
	}

	@Given("{identifier}.light ← point_light\\({point}, {color})")
	public void worldSetLightToPointLight(String worldName, Point position,
		Color color)
	{
		PointLight pointLight = new PointLight(position, color);
		World world = data.getWorld(worldName);
		world.getLightSources().clear();
		world.getLightSources().add(pointLight);
	}

	@Given("{identifier} is added to {identifier}")
	public void sIsAddedToW(String childObjName, String parentObjName)
	{
		World world = data.getWorld(parentObjName);
		BoundingBox box = data.getBoundingBox(parentObjName);
		if (world != null)
		{
			Shape child = data.getShape(childObjName);
			world.getSceneObjects().add(child);
		}
		else if (box != null)
		{
			Point p = data.getPoint(childObjName);
			BoundingBox otherBox = data.getBoundingBox(childObjName);
			if (p != null)
			{
				BoundingBox newBox = box.add(p);
				data.putBoundingBox(parentObjName, newBox);
			}
			else if (otherBox != null)
			{
				BoundingBox newBox = box.add(otherBox);
				data.putBoundingBox(parentObjName, newBox);
			}
			else
				Assert.fail("Unknown type of object for " + childObjName);
		}
		else
			Assert.fail("Unknown type of object for " + parentObjName);
	}


	@Whens({
		@When("{identifier} ← {default_world}"),
		@When("{identifier} ← {world}")
	})
	public void wDefault_world(String worldName, World w)
	{
		data.putWorld(worldName, w);
	}

	@When("{identifier} ← intersect_world\\({identifier}, {identifier})")
	public void xsIntersect_worldWR(String intersectionListName,
		String worldName, String rayName)
	{
		World w = data.getWorld(worldName);
		Ray r = data.getRay(rayName);

		IntersectionList il = new IntersectionList(w.getSceneObjects(), r,
			false);

		data.putIntersectionList(intersectionListName, il);
	}

	@When("{identifier} ← shade_hit\\({identifier}, {identifier})")
	public void cShade_hitWComps(String colorName, String worldName,
		String intersectionComputationsName)
	{
		cShade_hitWComps(colorName, worldName, intersectionComputationsName,
			Tracer.MAX_REFLECTION_RECURSION);
	}

	@When("{identifier} ← shade_hit\\({identifier}, {identifier}, {int})")
	public void cShade_hitWComps(String colorName, String worldName,
		String intersectionComputationsName, Integer numRecursion)
	{
		World world = data.getWorld(worldName);
		IntersectionComputations comps = data.getComputations(
			intersectionComputationsName);

		Color c = comps.shadeHit(world, numRecursion);

		data.putColor(colorName, c);
	}

	@When("{identifier} ← reflected_color\\({identifier}, {identifier})")
	public void colorReflected_colorWComps(String colorName, String worldName,
		String compsName)
	{
		colorReflected_colorWComps(colorName, worldName, compsName,
			Tracer.MAX_REFLECTION_RECURSION);
	}

	@When("{identifier} ← reflected_color\\({identifier}, {identifier}, {int})")
	public void colorReflected_colorWComps(String colorName, String worldName,
		String compsName, Integer remaining)
	{
		World world = data.getWorld(worldName);
		IntersectionComputations comps = data.getComputations(compsName);
		Color color = comps.reflectedColor(world, remaining);
		data.putColor(colorName, color);
	}

	@When("{identifier} ← refracted_color\\({identifier}, {identifier})")
	public void colorRefracted_colorWComps(String colorName, String worldName,
		String intersectionComputationsName)
	{
		colorRefracted_colorWCompsInt(colorName, worldName,
			intersectionComputationsName, Tracer.MAX_REFLECTION_RECURSION);
	}

	@When("{identifier} ← refracted_color\\({identifier}, {identifier}, {int})")
	public void colorRefracted_colorWCompsInt(String colorName, String worldName,
		String intersectionComputationsName, Integer remaining)
	{
		World world = data.getWorld(worldName);
		IntersectionComputations comps = data.getComputations(
			intersectionComputationsName);

		Color c = comps.refractedColor(world, remaining);

		data.putColor(colorName, c);
	}

	@When("{identifier} ← color_at\\({identifier}, {identifier})")
	public void cColor_atWR(String colorName, String worldName, String rayName)
	{
		World world = data.getWorld(worldName);
		Ray ray = data.getRay(rayName);

		Color color = IntersectionComputations.colorAt(world, ray, Tracer.MAX_REFLECTION_RECURSION);
		data.putColor(colorName, color);
	}

	@Then("color_at\\({identifier}, {identifier}) should terminate successfully")
	public void color_atWRShouldTerminateSuccessfully(String worldName,
		String rayName)
	{
		World world = data.getWorld(worldName);
		Ray ray = data.getRay(rayName);
		Color c = IntersectionComputations.colorAt(world, ray, 5);
		// TODO: Figure out how to test for failure case (not terminated)
		Assert.assertNotNull(c);
	}


	@Then("{identifier} contains no objects")
	public void wContainsNoObjects(String worldName)
	{
		World w = data.getWorld(worldName);
		List<Shape> sceneObjects = w.getSceneObjects();
		Assert.assertEquals(0, sceneObjects.size());
	}

	@Then("{identifier} has no light source")
	public void wHasNoLightSource(String worldName)
	{
		World w = data.getWorld(worldName);
		List<Light> lightSources = w.getLightSources();
		Assert.assertEquals(0, lightSources.size());
	}

	@Then("{identifier}.light = {identifier}")
	public void wContainsNoObjects(String worldName, String expectedLightName)
	{
		Light expectedLight = data.getLight(expectedLightName);

		World w = data.getWorld(worldName);
		List<Light> lightSources = w.getLightSources();
		Assert.assertEquals(1, lightSources.size());
		Light actualLight = lightSources.get(0);

		Assert.assertEquals(expectedLight, actualLight);
	}

	@Then("{identifier} contains {identifier}")
	public void wContainsS(String worldName, String objectName)
	{
		World world = data.getWorld(worldName);
		Shape object = data.getShape(objectName);
		List<Shape> sceneObjects = world.getSceneObjects();
		boolean contains = sceneObjects.contains(object);
		Assert.assertTrue(contains);
	}

	@Then("is_shadowed\\({identifier}, {identifier}) is false")
	public void is_shadowedWPIsFalse(String worldName, String pointName)
	{
		is_shadowedWPBoolean(worldName, pointName, false);
	}

	@Then("is_shadowed\\({identifier}, {identifier}) is true")
	public void is_shadowedWPIsTrue(String worldName, String pointName)
	{
		is_shadowedWPBoolean(worldName, pointName, true);
	}

	private void is_shadowedWPBoolean(String worldName, String pointName,
		boolean expectedResult)
	{
		World world = data.getWorld(worldName);
		Point point = data.getPoint(pointName);
		Light light = world.getLightSources().get(0);
		double actualResult = Lighting.isShadowed(world, point, light);
		Assert.assertEquals(expectedResult, Tuple.isZero(actualResult));
	}

	@Then("is_shadowed\\({identifier}, {identifier}, {identifier}) is {boolean}")
	public void is_shadowedWLight_positionPointIsFalse(String worldName,
		String lightPositionName, String otherPointName, Boolean expectedResult)
	{
		World world = data.getWorld(worldName);
		Point lightPosition = data.getPoint(lightPositionName);
		Point otherPoint = data.getPoint(otherPointName);

		double actualResult = Lighting.isShadowed(world,
			Arrays.asList(lightPosition), otherPoint);
		Assert.assertEquals(expectedResult, Tuple.isZero(actualResult));
	}
}
