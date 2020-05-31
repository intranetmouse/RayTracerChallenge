package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.Plane;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import cucumber.api.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

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

	@Given(wordPattern + ".material.(ambient|transparency|refractive_index) ← " + doublePattern)
	public void outerMaterialAmbient(String objectName, String propertyName,
		double doubleValue)
	{
		Shape obj = data.getShape(objectName);
		Material material = obj.getMaterial();
		switch (propertyName)
		{
			case "ambient":
				material.setAmbient(doubleValue);
				break;
			case "transparency":
				material.setTransparency(doubleValue);
				break;
			case "refractive_index":
				material.setRefractive(doubleValue);
				break;
			default:
				Assert.fail("Illegal property name " + propertyName);
		}
	}


	@When(wordPattern + " ← intersect\\(" + twoWordPattern + "\\)")
	public void xsIntersectSR(String intersectionName, String sphereName,
		String rayName)
	{
		Shape obj = data.getShape(sphereName);
		Ray ray = data.getRay(rayName);
		IntersectionList intersections = obj.intersections(ray);
		data.put(intersectionName, intersections);
	}

	@When("^set_transform\\(" + twoWordPattern + "\\)$")
	public void set_transform_s_t(String sphereName, String matrixName)
	{
		Shape obj = data.getShape(sphereName);
		Matrix mtx = data.getMatrix(matrixName);
		obj.setTransform(mtx);
	}

	@When("^set_transform\\(" + wordPattern +
		", (scaling|translation)\\(" + threeDoublesPattern + "\\)\\)$")
	public void set_transform_s_t(String sphereName, String operation, double x,
		double y, double z)
	{
		Shape obj = data.getShape(sphereName);
		Matrix mtx = "scaling".equals(operation) ? Matrix.newScaling(x, y, z) :
			Matrix.newTranslation(x, y, z);
		obj.setTransform(mtx);
	}

	@Given("set_transform\\(" + wordPattern + ", rotation_y\\(π\\/" + doublePattern + "\\)\\)")
	public void set_transformGRotation_yΠ(String shapeName, Double divisor)
	{
		Shape s = data.getShape(shapeName);
		Matrix mtx = Matrix.newRotationY(Math.PI / divisor);
		s.setTransform(mtx);
	}

//	@Given("^set_transform\\(" + wordPattern + ", translation\\(" + threeDoublesPattern + "\\) * scaling\\(" + threeDoublesPattern + "\\)\\)")
//	public void setTransformShapeTranslationScaling(//String shapeName,
//		double translateX, double translateY, double translateZ,
//		double scalingX, double scalingY, double scalingZ)
//	{
//		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
//	}

	@When(wordPattern + " ← normal_at\\(" + wordPattern +
		", point\\(" + threeDoublesPattern + "\\)\\)")
	public void n_normal_at_s_point(String normalVectorName, String sphereName,
		double x, double y, double z)
	{
		Shape obj = data.getShape(sphereName);
		Point point = new Point(x, y, z);

		Vector normalVector = obj.normalAt(point);

		data.put(normalVectorName, normalVector);
	}

	@When(wordPattern + " ← normal_at\\(" + wordPattern +
		", point\\(√" + doublePattern + "\\/" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern + "\\)\\)")
	public void nNormal_atSPoint(String normalVectorName, String sphereName,
		double xNum, double xDenom, double yNum, double yDenom, double zNum,
		double zDenom)
	{
		Shape obj = data.getShape(sphereName);
		Point point = new Point(Math.sqrt(xNum) / xDenom,
			Math.sqrt(yNum) / yDenom, Math.sqrt(zNum) / zDenom);

		Vector normalVector = obj.normalAt(point);

		data.put(normalVectorName, normalVector);
	}

	@When(wordPattern + " ← normal_at\\(" + wordPattern +
		", point\\(" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern +
		", -√" + doublePattern + "\\/" + doublePattern + "\\)\\)")
	public void nNormal_atSPoint(String normalVectorName, String objectName,
		double x, double yNum, double yDenom, double zNum, double zDenom)
	{
		Shape obj = data.getShape(objectName);
		Point normalPoint = new Point(x, Math.sqrt(yNum)/yDenom,
			-Math.sqrt(zNum)/zDenom);
		Vector normalVector = obj.normalAt(normalPoint);
		data.put(normalVectorName, normalVector);
	}
}
