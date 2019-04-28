package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.StripePattern;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
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
						Shape expectedObject = expectedIntersection.getObject();
						Shape actualObject = actualComps.getObject();
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

	@Then(wordPattern + "." + wordPattern + "." + wordPattern + " < -EPSILON\\/" + doublePattern)
	public void compsOver_pointZEPSILON(String objectName,
		String objectVariableName, String coordinateName, double divisor)
	{
		double maxNighttimeValue = -Tuple.EPSILON / divisor;
		IntersectionComputations comps = data.getComputations(objectName);
		if (comps != null)
		{
			switch (objectVariableName)
			{
				case "over_point":
					Point overPoint = comps.getOverPoint();
					switch (coordinateName)
					{
						case "z":
							Assert.assertTrue(overPoint.getZ() < maxNighttimeValue);
							return;
						default:
							throw new cucumber.api.PendingException(
								"Unknown coordinate name " + coordinateName +
								" for variable name " + objectVariableName +
								" on obj name=" + objectName);
					}
				default:
					throw new cucumber.api.PendingException(
						"Unknown variable name " + objectVariableName +
						" on obj name=" + objectName);
			}
		}
		throw new cucumber.api.PendingException(
			"Unknown data type for variable " + objectName);
	}

	@Then("comps.point.z > comps.over_point.z")
	public void compsPointZCompsOver_pointZ()
	{
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
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
		objPropEqualsThreeDouble(expectedObjName, propertyName, objType,
			Math.sqrt(xNum) / xDenom, y, -Math.sqrt(zNum) / zDenom);
	}

	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern + "\\("
		+ threeDoublesPattern + "\\)")
	public void objPropEqualsThreeDouble(String expectedObjName,
		String propertyName, String objType, double x, double y, double z)
	{
		Object expected = getObject(objType, x, y, z);
		Object value = getObjPropValue(expectedObjName, propertyName);
		Assert.assertEquals(expected, value);
	}

	private Object getObject(String objType, double x, double y, double z)
	{
		switch (objType)
		{
			case "point":
				return new Point(x, y, z);
			case "vector":
				return new Vector(x, y, z);
			case "color":
				return new Vector(x, y, z);
			case "translation":
				return Matrix.newTranslation(x, y, z);
			default:
				Assert.fail("Unrecognized object type " + objType);
				return null;
		}
	}

	private Object getObjPropValue(String expectedObjName, String propertyName)
	{
		Object value = null;
		IntersectionComputations comps = data.getComputations(expectedObjName);
		Ray ray = data.getRay(expectedObjName);
		Material material = data.getMaterial(expectedObjName);
		Shape shape = data.getShape(expectedObjName);
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
		else if (shape != null)
		{
			value = "transform".equals(propertyName) ? shape.getTransform() :
				"saved_ray".equals(propertyName) ? shape.getSavedRay() :
				null;
		}
		else
		{
			Assert.fail("object with name " + expectedObjName
				+ " not found. obj=" + comps);
		}

		Assert.assertNotNull("Property name does not match: " + propertyName,
			value);
		return value;
	}

	@Then(wordPattern + "\\." + wordPattern + "\\." + wordPattern + " = "
		+ wordPattern + "\\(" + threeDoublesPattern + "\\)")
	public void objPropPropEqualsThreeDouble(String expectedObjName,
		String prop1Name, String prop2Name, String objType, double x,
		double y, double z)
	{
		Object expected = getObject(objType, x, y, z);
		Ray prop1Value = (Ray)getObjPropValue(expectedObjName, prop1Name);
		Object value = null;
		switch (prop2Name)
		{
			case "origin":
				Assert.assertEquals(expected, prop1Value.getOrigin());
				return;
			case "direction":
				Assert.assertEquals(expected, prop1Value.getDirection());
				return;
			default:
				Assert.fail("unknown property: " + prop2Name);
		}
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

		PixelCoordinate pixelCoord = data.getPixelCoordinate(objectName);
		if (pixelCoord != null)
		{
			Integer coord = "x".equals(propertyName) ? pixelCoord.getX() :
				"y".equals(propertyName) ? pixelCoord.getY() :
				null;
			Assert.assertNotNull("Illegal property name " + propertyName, coord);
			Assert.assertEquals((int)expectedValue, coord.intValue());
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

	@Then(wordPattern + "\\." + wordPattern + " = " + wordPattern)
	public void patternAWhite(String objectName, String propertyName,
		String expectedObjectName)
	{
		Shape obj = data.getShape(objectName);
		if (obj != null)
		{
			Matrix expectedMatrix = getMatrix(expectedObjectName);
			Assert.assertEquals(expectedMatrix, obj.getTransform());
			return;
		}

		Camera camera = data.getCamera(objectName);
		if (camera != null)
		{
			Matrix expectedMatrix = getMatrix(expectedObjectName);
			Assert.assertEquals(expectedMatrix, camera.getTransform());
			return;
		}

		StripePattern actualPattern = (StripePattern)data.getPattern(objectName);
		if (actualPattern != null)
		{
			Color expectedColor = data.getColor(expectedObjectName);
			Color actualColor = "a".equals(propertyName) ? actualPattern.getA() :
				"b".equals(propertyName) ? actualPattern.getB() :
				null;
			Assert.assertEquals(expectedColor, actualColor);
			return;
		}

		Assert.fail("Unknown object type for object name " + objectName);
	}

	private Matrix getMatrix(String matrixName)
	{
		Matrix expectedMatrix;
		switch (matrixName)
		{
			case "identity_matrix":
				expectedMatrix = Matrix.identity(4);
				break;
			default:
				expectedMatrix = data.getMatrix(matrixName);
		}
		return expectedMatrix;
	}

	@Then("^stripe_at\\(" + wordPattern + ", point\\(" + threeDoublesPattern
		+ "\\)\\) = " + wordPattern)
	public void stripe_atPatternPointWhite(String patternName, double x,
		double y, double z, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);

		StripePattern stripePattern = (StripePattern)data.getPattern(patternName);

		Point point = new Point(x, y, z);

		Color actualColor = stripePattern.stripeAt(point);
		Assert.assertEquals(expectedColor, actualColor);
	}

}
