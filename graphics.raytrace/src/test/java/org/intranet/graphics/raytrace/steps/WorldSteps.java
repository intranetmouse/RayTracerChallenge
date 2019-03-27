package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Matrix;
import org.intranet.graphics.raytrace.PointLight;
import org.intranet.graphics.raytrace.SceneObject;
import org.intranet.graphics.raytrace.Sphere;
import org.intranet.graphics.raytrace.World;
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

	@Given(wordPattern + " ← sphere\\(\\) with:")
	public void sSphereWith(String sphereName,
		DataTable dataTable)
	{
		Sphere sphere = new Sphere();
		Material material = sphere.getMaterial();
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
							sphere.setTransform(Matrix.newScaling(scaleX, scaleY, scaleZ));
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
				default:
					throw new cucumber.api.PendingException("Unknown sphere property " + property);
			}
		}
		data.put(sphereName, sphere);
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
//		throw new cucumber.api.PendingException();
	}


	@When(wordPattern + " ← default_world\\(\\)")
	public void wDefault_world(String worldName)
	{
		World w = World.defaultWorld();
		data.put(worldName, w);
	}


	@Then(wordPattern + " contains no objects")
	public void wContainsNoObjects(String worldName)
	{
		World w = data.getWorld(worldName);
		List<SceneObject> lightSources = w.getSceneObjects();
		Assert.assertEquals(0, lightSources.size());
	}

	@Then(wordPattern + " has no light source")
	public void wHasNoLightSource(String worldName)
	{
		World w = data.getWorld(worldName);
		List<PointLight> lightSources = w.getLightSources();
		Assert.assertEquals(0, lightSources.size());
	}

	@Then(wordPattern + ".light = " + wordPattern)
	public void wLightLight(String worldName, String lightName)
	{
		World w = data.getWorld(worldName);
		PointLight light = data.getPointLight(lightName);
		Assert.assertEquals(light, w.getLightSources().get(0));
	}

	@Then(wordPattern + " contains " + wordPattern)
	public void wContainsS(String worldName, String objectName)
	{
		World world = data.getWorld(worldName);
		SceneObject object = data.getSceneObject(objectName);
		List<SceneObject> sceneObjects = world.getSceneObjects();
		boolean contains = sceneObjects.contains(object);
		Assert.assertTrue(contains);
	}

}
