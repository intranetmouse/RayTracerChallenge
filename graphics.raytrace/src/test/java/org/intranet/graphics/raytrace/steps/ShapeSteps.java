package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.When.Whens;

public class ShapeSteps extends StepsParent
{
	public ShapeSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier}.material = {identifier}")
	public void compareShapeSetMaterial(String shapeName, String expectedMaterialName)
	{
		Shape shape = data.getShape(shapeName);
		Material expectedMaterial = data.getMaterial(expectedMaterialName);
		Material actualMaterial = shape.getMaterial();
		Assert.assertEquals(expectedMaterial, actualMaterial);
	}

	@Given("{identifier} ← {shape} with:")
	public void sGlass_sphere(String shapeName, Shape shape, DataTable dataTable)
	{
		WorldSteps.setShapePropertiesFromDataTable(dataTable, shape);
		data.put(shapeName, shape);
	}

	@Given("{identifier} ← {shape}")
	public void pPlane(String shapeName, Shape shape)
	{
		data.put(shapeName, shape);
	}

	@Given("{identifier}.material.ambient ← {dbl}")
	public void outerMaterialAmbient(String objectName, double doubleValue)
	{
		Shape obj = data.getShape(objectName);
		Material material = obj.getMaterial();
		material.setAmbient(doubleValue);
	}

	@Given("{identifier}.material.transparency ← {dbl}")
	public void outerMaterialTransparency(String objectName, String propertyName,
		double doubleValue)
	{
		Shape obj = data.getShape(objectName);
		Material material = obj.getMaterial();
		material.setTransparency(doubleValue);
	}

	@Given("{identifier}.material.refractive_index ← {dbl}")
	public void outerMaterialRefractive(String objectName, String propertyName,
		double doubleValue)
	{
		Shape obj = data.getShape(objectName);
		Material material = obj.getMaterial();
		material.setRefractive(doubleValue);
	}


	@When("{identifier} ← intersect\\({identifier}, {identifier})")
	public void xsIntersectSR(String intersectionName, String sphereName,
		String rayName)
	{
		Shape obj = data.getShape(sphereName);
		Ray ray = data.getRay(rayName);
		IntersectionList intersections = obj.intersections(ray);
		data.put(intersectionName, intersections);
	}

	@When("set_transform\\({identifier}, {identifier})")
	public void set_transform_s_t(String sphereName, String matrixName)
	{
		Shape obj = data.getShape(sphereName);
		Matrix mtx = data.getMatrix(matrixName);
		obj.setTransform(mtx);
	}

	@When("set_transform\\({identifier}, {matrix})")
	public void set_transform_s_t(String shapeName, Matrix mtx)
	{
		Shape obj = data.getShape(shapeName);
		obj.setTransform(mtx);
	}

	@Given("set_transform\\({identifier}, {matrixRotationPiDiv})")
	public void set_transformGRotation_yΠ(String shapeName, Matrix mtx)
	{
		Shape s = data.getShape(shapeName);
		s.setTransform(mtx);
	}

	@Given("set_transform\\({identifier}, {matrix} \\* {matrix})")
	public void setTransformShapeTranslationScaling(String shapeName,
		Matrix first, Matrix second)
	{
		Shape s = data.getShape(shapeName);
		s.setTransform(first.multiply(second));
	}

	@Whens({
		@When("{identifier} ← normal_at\\({identifier}, {point})"),
		@When("{identifier} ← normal_at\\({identifier}, {pointNSS})")
	})
	public void n_normal_at_s_point(String normalVectorName, String sphereName,
		Point point)
	{
		Shape obj = data.getShape(sphereName);

		Vector normalVector = obj.normalAt(point);

		data.put(normalVectorName, normalVector);
	}

	@When("{identifier} ← normal_at\\({identifier}, {pointSSS})")
	public void nNormal_atSPoint(String normalVectorName, String sphereName,
		Point point)
	{
		Shape obj = data.getShape(sphereName);

		Vector normalVector = obj.normalAt(point);

		data.put(normalVectorName, normalVector);
	}
}
