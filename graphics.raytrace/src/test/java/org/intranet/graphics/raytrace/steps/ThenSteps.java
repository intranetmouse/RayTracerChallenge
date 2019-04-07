package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.SceneObject;
import org.intranet.graphics.raytrace.Tuple;
import org.intranet.graphics.raytrace.Vector;
import org.junit.Assert;

import cucumber.api.java.en.Then;

public class ThenSteps
	extends StepsParent
{
	public ThenSteps(RaytraceData data)
	{
		super(data);
	}

	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern + "\\." + wordPattern)
	public void compsTIT(String actualObjName, String actualObjVariableName,
		String expectedObjName, String expectedObjVariableName)
	{
//System.out.printf("obj=%s, var=%s, expected obj=%s, var=%s\n", actualObjName, actualObjVariableName, expectedObjName, expectedObjVariableName);
		Assert.assertEquals(expectedObjVariableName, actualObjVariableName);

		IntersectionComputations actualComps = data.getComputations(actualObjName);
		if (actualComps != null)
		{
			IntersectionComputations expectedComps = data.getComputations(expectedObjName);

			if (expectedComps != null)
			{
				switch (actualObjVariableName)
				{
					case "t":
						double expectedDistance = expectedComps.getDistance();
						double actualDistance = actualComps.getDistance();
						Assert.assertEquals(expectedDistance,
							actualDistance, Tuple.EPSILON);
						return;
					default:
						throw new cucumber.api.PendingException(
							"Unknown variable name " + actualObjVariableName +
							" on obj name=" + actualObjName);
				}
			}

			Intersection expectedIntersection = data.getIntersection(expectedObjName);
			if (expectedIntersection != null)
			{
				switch (actualObjVariableName)
				{
					case "t":
						double expectedDistance = expectedIntersection.getDistance();
						double actualDistance = actualComps.getDistance();
						Assert.assertEquals(expectedDistance,
							actualDistance, Tuple.EPSILON);
						return;
					case "object":
						SceneObject expectedObject = expectedIntersection.getObject();
						SceneObject actualObject = actualComps.getObject();
						Assert.assertEquals(expectedObject, actualObject);
						return;
					default:
						throw new cucumber.api.PendingException(
							"Unknown variable name " + actualObjVariableName +
							" on obj name=" + actualObjName);
				}
			}
		}
		throw new cucumber.api.PendingException(
			"Unknown data type for variable " + actualObjName);
	}

	@Then(wordPattern + " is nothing")
	public void iIsNothing(String intersectionName)
	{
		Intersection i = data.getIntersection(intersectionName);
		Assert.assertNull(i);
	}


	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern + "\\(√"
		+ doublePattern + "\\/" + doublePattern + ", " + doublePattern + ", -√"
		+ doublePattern + "\\/" + doublePattern + "\\)")
	public void objPropEqualsTupleSqrtX_negSqrtZ(String expectedObjName,
		String propertyName, String objType, double xNum, double xDenom,
		double y, double zNum, double zDenom)
	{
		objPropEqualsTuple(expectedObjName, propertyName, objType,
			Math.sqrt(xNum) / xDenom, y, -Math.sqrt(zNum) / zDenom);
	}

	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern + "\\("
		+ threeDoublesPattern + "\\)")
	public void objPropEqualsTuple(String expectedObjName, String propertyName,
		String objType, double x, double y, double z)
	{
		Tuple expected = "point".equals(objType) ? new Point(x, y, z) :
			"vector".equals(objType) ? new Vector(x, y, z) :
			"color".equals(objType) ? new Color(x, y, z) :
			null;
		Assert.assertNotNull("Unrecognized object type " + objType, expected);

		Object value = null;
		IntersectionComputations comps = data.getComputations(expectedObjName);
		Ray ray = data.getRay(expectedObjName);
		Material material = data.getMaterial(expectedObjName);
		if (comps != null)
		{
			value = "point".equals(propertyName) ? comps.getPoint() :
				"eyev".equals(propertyName) ? comps.getEyeVector() :
				"normalv".equals(propertyName) ? comps.getNormalVector() :
				null;
		}
		else if (ray != null)
		{
			value = "origin".equals(propertyName) ? ray.getOrigin() :
				"direction".equals(propertyName) ? ray.getDirection() :
				null;
		}
		else if (material != null)
		{
			value = "color".equals(propertyName) ? material.getColor() :
				null;
		}
		else
		{
			Assert.fail("object with name " + expectedObjName
				+ " not found. obj=" + comps);
		}

		Assert.assertNotNull("Property name does not match: " + propertyName,
			value);

		Assert.assertEquals(expected, value);
	}

	@Then(wordPattern + "." + wordPattern + " = " + doublePattern)
	public void objPropertyEqualsDouble(String objectName, String propertyName,
		double expectedValue)
	{
		Camera camera = data.getCamera(objectName);
		if (camera != null)
		{
			switch (propertyName)
			{
				case "hsize":
					Assert.assertEquals((int)expectedValue, camera.getHsize());
					return;
				case "vsize":
					Assert.assertEquals((int)expectedValue, camera.getVsize());
					return;
				case "field_of_view":
					Assert.assertEquals(expectedValue, camera.getFieldOfView(), Tuple.EPSILON);
					return;
				case "pixel_size":
					Assert.assertEquals(expectedValue, camera.getPixelSize(), Tuple.EPSILON);
					return;
				default:
					Assert.fail("Unrecognized propertyName " + propertyName);
			}
			return;
		}

		Canvas canvas = data.getCanvas(objectName);
		if (canvas != null)
		{
			switch (propertyName)
			{
				case "width":
					Assert.assertEquals((int)expectedValue, canvas.getWidth());
					return;
				case "height":
					Assert.assertEquals((int)expectedValue, canvas.getHeight());
					return;
				default:
					Assert.fail("Unrecognized propertyName " + propertyName);
			}
		}

		Intersection intersection = data.getIntersection(objectName);

		if (intersection != null)
		{
			switch (propertyName)
			{
				case "t":
					Assert.assertEquals(expectedValue, intersection.getDistance(), Tuple.EPSILON);
					return;
				default:
					Assert.fail("Unrecognized propertyName " + propertyName);
			}
		}

		IntersectionList intersectionList = data.getIntersectionList(objectName);
		if (intersectionList != null)
		{
			switch (propertyName)
			{
				case "count":
					Assert.assertEquals((int)expectedValue, intersectionList.count());
					return;
				default:
					Assert.fail("Unrecognized propertyName " + propertyName);
			}
		}

		Material material = data.getMaterial(objectName);
		if (material != null)
		{
			Double actualValue = "ambient".equals(propertyName) ? material.getAmbient() :
				"diffuse".equals(propertyName) ? material.getDiffuse() :
				"specular".equals(propertyName) ? material.getSpecular() :
				"shininess".equals(propertyName) ? material.getShininess() :
				"reflective".equals(propertyName) ? material.getReflective() :
				"transparency".equals(propertyName) ? material.getTransparency() :
				"refractive_index".equals(propertyName) ? material.getRefractive() :
				null;
			Assert.assertNotNull("Illegal property name " + propertyName, actualValue);
			Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
			return;
		}

		Tuple a = data.getTuple(objectName);
		if (a == null)
			a = data.getPoint(objectName);
		if (a == null)
			a = data.getVector(objectName);
		if (a != null)
		{
			Double actualValue = "x".equals(propertyName) ? a.getX() :
				"y".equals(propertyName) ? a.getY() :
				"z".equals(propertyName) ? a.getZ() :
				"w".equals(propertyName) ? a.getW() : null;
			Assert.assertNotNull("Illegal color name " + propertyName, actualValue);
			Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
			return;
		}

		Color color = data.getColor(objectName);
		if (color != null)
		{
			Double colorValue = "red".equals(propertyName) ? color.getRed() :
				"green".equals(propertyName) ? color.getGreen() :
				"blue".equals(propertyName) ? color.getBlue() :
				null;
			Assert.assertNotNull("Illegal color name " + propertyName, colorValue);
			Assert.assertEquals(expectedValue, colorValue, Tuple.EPSILON);
			return;
		}

		Assert.fail("Unrecognized objectName " + objectName);
	}

	@Then(wordPattern + "." + wordPattern + " = π\\/" + doublePattern)
	public void cFieldOfViewPi(String cameraName, String propertyName,
		double divisor)
	{
		Camera camera = data.getCamera(cameraName);

		double value = Math.PI / divisor;

		switch (propertyName)
		{
			case "field_of_view":
				Assert.assertEquals(value, camera.getFieldOfView(), Tuple.EPSILON);
				return;
			default:
				Assert.fail("Unrecognized propertyName " + propertyName);
		}
	}

}
