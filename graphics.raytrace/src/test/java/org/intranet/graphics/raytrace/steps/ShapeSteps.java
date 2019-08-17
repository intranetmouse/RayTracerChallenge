package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class ShapeSteps extends StepsParent
{
	public ShapeSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← glass_sphere\\(\\) with:")
	public void sGlass_sphere(String sphereName, DataTable dataTable)
	{
		Sphere sphere = createGlassSphere(sphereName);
		WorldSteps.setShapePropertiesFromDataTable(dataTable, sphere);
	}

	@Given(wordPattern + " ← (sphere|glass_sphere|plane|test_shape|group)\\(\\)")
	public void pPlane(String shapeName, String shapeType)
	{
		switch (shapeType)
		{
			case "sphere":
				data.put(shapeName, new Sphere());
				break;
			case "glass_sphere":
				Sphere sphere = createGlassSphere(shapeName);
				sphere.getSavedRay();
				break;
			case "plane":
				data.put(shapeName, new Plane());
				break;
			case "test_shape":
				data.put(shapeName, new TestShape());
				break;
			case "group":
				data.put(shapeName, new Group());
				break;
			default:
				Assert.fail("Unknown shape type " + shapeType);
		}
	}

	private Sphere createGlassSphere(String sphereName)
	{
		Sphere sphere = new Sphere();
		Material material = sphere.getMaterial();
		material.setTransparency(1.0);
		material.setRefractive(1.5);
		data.put(sphereName, sphere);
		return sphere;
	}
}
