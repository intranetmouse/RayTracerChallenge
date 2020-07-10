package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionComputations;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.ShapeParent;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.BoundingBox;
import org.intranet.graphics.raytrace.shape.Cylinder;
import org.intranet.graphics.raytrace.shape.Group;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.junit.Assert;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Then.Thens;

public class ThenSteps
	extends StepsParent
{
	public ThenSteps(RaytraceData data)
	{
		super(data);
	}

	@Then("{identifier}.t = {identifier}.t")
	public void compsTeqT(String actualObjName, String expectedObjName)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);
		IntersectionComputations expectedComps = data.getComputations(expectedObjName);
		if (expectedComps != null)
		{
			double expectedDistance = expectedComps.getDistance();
			double actualDistance = actualComps.getDistance();
			Assert.assertEquals(expectedDistance,
				actualDistance, Tuple.EPSILON);
			return;
		}

		Intersection expectedIntersection = data.getIntersection(expectedObjName);
		if (expectedIntersection != null)
		{
			double expectedDistance = expectedIntersection.getDistance();
			double actualDistance = actualComps.getDistance();
			Assert.assertEquals(expectedDistance,
				actualDistance, Tuple.EPSILON);
			return;
		}

		throw new PendingException("Unknown object name " + expectedObjName);
	}

	@Then("{identifier}.object = {identifier}.object")
	public void compsOeqO(String actualObjName, String expectedObjName)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);

		Intersection expectedIntersection = data.getIntersection(expectedObjName);
		if (expectedIntersection != null)
		{
			Shape expectedObject = expectedIntersection.getObject();
			Shape actualObject = actualComps.getObject();
			Assert.assertEquals(expectedObject, actualObject);
			return;
		}

		throw new PendingException("Unknown object name " + expectedObjName);
	}

	@Then("{identifier}.point = {point}")
	public void objPointEqPoint(String actualObjName, Point expectedPoint)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);
		Point actualPoint = actualComps.getPoint();
		Assert.assertEquals(expectedPoint, actualPoint);
	}

	@Then("{identifier}.eyev = {vector}")
	public void objEyevEqVector(String actualObjName, Vector expectedVector)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);
		Vector actualEyeVector = actualComps.getEyeVector();
		Assert.assertEquals(expectedVector, actualEyeVector);
	}

	@Then("{identifier}.normalv = {vector}")
	public void objNormalvEqVector(String actualObjName, Vector expectedVector)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);
		Vector actualNormalVector = actualComps.getNormalVector();
		Assert.assertEquals(expectedVector, actualNormalVector);
	}

	@Then("{identifier}.reflectv = {vectorNSS}")
	public void objReflectvEqVectorNss(String actualObjName, Vector expectedVector)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);
		Vector actualNormalVector = actualComps.getReflectVector();
		Assert.assertEquals(expectedVector, actualNormalVector);
	}

	@Then("{identifier}.inside = {boolean}")
	public void objInsideEqBoolean(String actualObjName, boolean expected)
	{
		IntersectionComputations actualComps = data.getComputations(actualObjName);
		boolean actualInside = actualComps.isInside();
		Assert.assertEquals(expected, actualInside);
	}

	@Then("{identifier}.{identifier}.{identifier} < -EPSILON\\/{dbl}")
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
							throw new PendingException(
								"Unknown coordinate name " + coordinateName +
								" for variable name " + objectVariableName +
								" on obj name=" + objectName);
					}
				default:
					throw new PendingException(
						"Unknown variable name " + objectVariableName +
						" on obj name=" + objectName);
			}
		}
		throw new PendingException(
			"Unknown data type for variable " + objectName);
	}

	@Then("{identifier}.point.z > {identifier}.over_point.z")
	public void assertCompsPointZGtCompsOverPointZ(String object1Name,
		String object2Name)
	{
		IntersectionComputations comps1 = data.getComputations(object1Name);
		IntersectionComputations comps2 = data.getComputations(object2Name);
		Assert.assertTrue(comps1.getPoint().getZ() > comps2.getOverPoint().getZ());
	}

	@Then("{identifier} is nothing")
	public void assertIsNothing(String intersectionName)
	{
		Intersection i = data.getIntersection(intersectionName);
		Assert.assertNull(i);
	}

	@Thens({
		@Then("{identifier}.{minMax} = {point}"),
		@Then("{identifier}.{minMax} = {pointInfinity}"),
		@Then("{identifier}.{minMax} = {pointYinfinity}"),
		@Then("{identifier}.{minMax} = {pointXZinfinity}")
	})
	public void assertBoxMinMaxEqPoint(String objName, String varName,
		Point expectedPoint)
	{
		BoundingBox box = data.getBoundingBox(objName);
		Point point = "min".equals(varName) ? box.getMin() : box.getMax();
		Assert.assertEquals(expectedPoint, point);
	}


	@Then("{identifier}.saved_ray.origin = {point}")
	public void assertSavedRayOriginEqPoint(String shapeName, Point expectedOrigin)
	{
		Shape shape = data.getShape(shapeName);
		Point actualOrigin = shape.getSavedRay().getOrigin();
		Assert.assertEquals(expectedOrigin, actualOrigin);
	}

	@Then("{identifier}.saved_ray.direction = {vector}")
	public void assertSavedRayDirectionEqVector(String shapeName, Vector expectedDirection)
	{
		Shape shape = data.getShape(shapeName);
		Vector actualDirection = shape.getSavedRay().getDirection();
		Assert.assertEquals(expectedDirection, actualDirection);
	}

	@Then("{identifier}.saved_ray is unset")
	public void assertSavedRayIsUnset(String shapeName)
	{
		Shape shape = data.getShape(shapeName);
		Ray savedRay = shape.getSavedRay();
		Assert.assertNull(savedRay);
	}

	@Then("{identifier}.saved_ray is set")
	public void assertSavedRayIsSet(String shapeName)
	{
		Shape shape = data.getShape(shapeName);
		Ray savedRay = shape.getSavedRay();
		Assert.assertNotNull(savedRay);
	}

	@Then("{identifier}.count = {int}")
	public void assertCountEqualsInt(String objectName, int expectedValue)
	{
		Shape shape = data.getShape(objectName);
		if (shape != null)
		{
			Group g = (Group)shape;
			Assert.assertEquals((int)expectedValue, g.getChildren().size());
			return;
		}
		IntersectionList intersectionList = data.getIntersectionList(objectName);
		if (intersectionList == null)
			Assert.fail("Unrecognized object Name " + objectName);
		Assert.assertEquals((int)expectedValue, intersectionList.count());
	}

	@Then("{identifier}.maximum = {identifier}")
	public void assertMaximumEqualsInt(String objectName, String identifier)
	{
		Double expectedValue = "infinity".equals(identifier) ? Double.POSITIVE_INFINITY : data.getDouble(identifier);
		Assert.assertNotNull("No value for " + identifier, expectedValue);

		Shape s = data.getShape(objectName);
		if (s == null)
			Assert.fail("Unrecognized object name " + objectName);

		Double value = ((Cylinder)s).getMaximum();
		Assert.assertEquals(expectedValue, value, Tuple.EPSILON);
	}

	@Then("{identifier}.width = {int}")
	public void assertWidthEqualsInt(String objectName, int expectedValue)
	{
		Canvas canvas = data.getCanvas(objectName);
		if (canvas == null)
			Assert.fail("Unrecognized object name " + objectName);

		Assert.assertEquals((int)expectedValue, canvas.getWidth());
	}

	@Then("{identifier}.x = {dbl}")
	public void assertXEqualsDbl(String objectName, Double expectedValue)
	{
		PixelCoordinate pixelCoord = data.getPixelCoordinate(objectName);
		if (pixelCoord != null)
		{
			int actualX = pixelCoord.getX();
			Assert.assertEquals(expectedValue.intValue(), actualX);
			return;
		}

		Tuple t = data.getTuple(objectName);
		if (t != null)
		{
			double actualX = t.getX();
			Assert.assertEquals(expectedValue, actualX, Tuple.EPSILON);
			return;
		}

		Assert.fail("Unrecognized tuple name " + objectName);
	}

	@Then("{identifier}.y = {dbl}")
	public void assertYEqualsDbl(String objectName, Double expectedValue)
	{
		PixelCoordinate pixelCoord = data.getPixelCoordinate(objectName);
		if (pixelCoord != null)
		{
			int actualY = pixelCoord.getY();
			Assert.assertEquals(expectedValue.intValue(), actualY);
			return;
		}

		Tuple t = data.getTuple(objectName);
		if (t != null)
		{
			double actualY = t.getY();
			Assert.assertEquals(expectedValue, actualY, Tuple.EPSILON);
			return;
		}

		Assert.fail("Unrecognized tuple name " + objectName);
	}

	@Then("{identifier}.z = {dbl}")
	public void assertZEqualsDbl(String objectName, Double expectedValue)
	{
		Tuple t = data.getTuple(objectName);
		if (t != null)
		{
			double actualZ = t.getZ();
			Assert.assertEquals(expectedValue, actualZ, Tuple.EPSILON);
			return;
		}

		Assert.fail("Unrecognized tuple name " + objectName);
	}

	@Then("{identifier}.w = {dbl}")
	public void assertWEqualsDbl(String objectName, Double expectedValue)
	{
		Tuple t = data.getTuple(objectName);
		if (t != null)
		{
			double actualW = t.getW();
			Assert.assertEquals(expectedValue, actualW, Tuple.EPSILON);
			return;
		}

		Assert.fail("Unrecognized tuple name " + objectName);
	}

	@Then("{identifier}.t = {dbl}")
	public void assertIntersectionTEqualsDbl(String objectName, double expectedValue)
	{
		Intersection intersection = data.getIntersection(objectName);
		if (intersection == null)
			Assert.fail("Unrecognized object name " + objectName);

		Assert.assertEquals(expectedValue, intersection.getDistance(), Tuple.EPSILON);
	}

	@Then("{identifier}.n1 = {dbl}")
	public void assertIntersectionN1Equalsbl(String objectName, double expectedValue)
	{
		IntersectionComputations comps = data.getComputations(objectName);
		if (comps == null)
			Assert.fail("Unrecognized object name " + objectName);

		Double value = comps.getN1();
		Assert.assertEquals(expectedValue, value, Tuple.EPSILON);
	}

	@Then("{identifier}.n2 = {dbl}")
	public void assertIntersectionN2EqualsDbl(String objectName, double expectedValue)
	{
		IntersectionComputations comps = data.getComputations(objectName);
		if (comps == null)
			Assert.fail("Unrecognized object name " + objectName);

		Double value = comps.getN2();
		Assert.assertEquals(expectedValue, value, Tuple.EPSILON);
	}


	@Then("{identifier} = {dbl}")
	public void identifierEqDbl(String doubleName, Double expectedValue)
	{
		double actualValue = data.getDouble(doubleName);

		Assert.assertEquals(expectedValue, actualValue, Tuple.EPSILON);
	}

	@Then("{identifier}.field_of_view = π\\/{dbl}")
	public void cFieldOfViewPi(String cameraName, double divisor)
	{
		Camera camera = data.getCamera(cameraName);

		double value = Math.PI / divisor;

		Assert.assertEquals(value, camera.getFieldOfView(), Tuple.EPSILON);
	}


	private void unknownProperty(String objName, String propertyName)
	{
		throw new IllegalArgumentException(
			"Unknown " + objName + " property name " + propertyName);
	}


	@Then("{identifier}.object = {identifier}")
	public void testPropertyEqualsObject(String objectName,String expectedObjectName)
	{
		Intersection intersection = data.getIntersection(objectName);
		if (intersection != null)
		{
			Shape shape = data.getShape(expectedObjectName);
			Assert.assertEquals(shape, intersection.getObject());
			return;
		}
		unknownProperty("intersection", "object");
	}

	@Then("{identifier}.transform = {identifier}")
	public void testShapeIdentifierEqualsMatrix(String objectName,
		String expectedMatrixName)
	{
		Matrix expectedMatrix = getMatrix(expectedMatrixName);

		Camera c = data.getCamera(objectName);
		if (c != null)
		{
			Matrix actualTransform = c.getTransform();
			Assert.assertEquals(expectedMatrix, actualTransform);
			return;
		}

		Pattern p = data.getPattern(objectName);
		if (p != null)
		{
			Matrix actualTransform = p.getTransform();
			Assert.assertEquals(expectedMatrix, actualTransform);
			return;
		}

		Shape shape = data.getShape(objectName);
		Assert.assertNotNull(shape);

		Matrix actualTransform = shape.getTransform();
		Assert.assertEquals(expectedMatrix, actualTransform);
	}

	@Then("{identifier}.position = {identifier}")
	public void testLightPostionEqualsPosition(String objectName,
		String expectedPositionName)
	{
		PointLight pointLight = data.getPointLight(objectName);
		Assert.assertNotNull(pointLight);

		Point expectedPosition = data.getPoint(expectedPositionName);
		Assert.assertEquals(expectedPosition, pointLight.getPosition());
	}

	@Then("{identifier}.intensity = {identifier}")
	public void testLightIntensityEqualsPosition(String objectName,
		String expectedPositionName)
	{
		PointLight pointLight = data.getPointLight(objectName);
		Assert.assertNotNull(pointLight);

		Color expectedIntensity = data.getColor(expectedPositionName);
		Assert.assertEquals(expectedIntensity, pointLight.getIntensity());
	}

	@Then("{identifier}.transform = {matrix}")
	public void identTransformEqMtx(String objectName, Matrix expectedMtx)
	{
		Shape shape = data.getShape(objectName);
		if (shape != null)
		{
			Matrix actualTransform = shape.getTransform();
			Assert.assertEquals(expectedMtx, actualTransform);
			return;
		}

		Pattern pattern = data.getPattern(objectName);
		if (pattern != null)
		{
			Matrix actualTransform = pattern.getTransform();
			Assert.assertEquals(expectedMtx, actualTransform);
			return;
		}

		Assert.fail("Unknown object type " + objectName);
	}

	@Then("{identifier}.parent = {identifier}")
	public void identTransformEqMtx(String shapeName, String expectedShapeName)
	{
		Shape shape = data.getShape(shapeName);
		ShapeParent actualParent = shape.getParent();
		Shape expectedShape = data.getShape(expectedShapeName);
		Assert.assertEquals(expectedShape, actualParent);
	}

	@Then("{identifier} = {color}")
	public void colorEqColor(String colorName, Color expectedColor)
	{
		Color actualColor = data.getColor(colorName);
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("{identifier}.{identifier} is nothing")
	public void sParentIsNothing(String objectName, String propertyName)
	{
		Shape s = data.getShape(objectName);
		if (s != null)
		{
			switch (propertyName)
			{
				case "parent":
					Assert.assertNull(s.getParent());
					return;
				default:
					Assert.fail("Unknown property name " + propertyName +
						" for object name " + objectName);
			}
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

	@Thens({
		@Then("stripe_at\\({identifier}, {point}) = {identifier}"),
		@Then("pattern_at\\({identifier}, {point}) = {identifier}")
	})
	public void stripe_atPatternPointWhite(String patternName, Point point,
		String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);

		Pattern pattern = (Pattern)data.getPattern(patternName);

		Color actualColor = pattern.colorAt(point);
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Thens({
		@Then("stripe_at\\({identifier}, {point}) = {color}"),
		@Then("pattern_at\\({identifier}, {point}) = {color}")
	})
	public void stripe_atPatternPointColor(String patternName, Point point, Color expectedColor)
	{
		Pattern pattern = (Pattern)data.getPattern(patternName);

		Color actualColor = pattern.colorAt(point);
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("{identifier}.material.transparency = {dbl}")
	public void sMaterialTransparency(String shapeName, Double doubleValue)
	{
		Shape s = data.getShape(shapeName);
		Material material = s.getMaterial();
		material.setTransparency(doubleValue);
	}

	@Then("{identifier}.material.refractive_index = {dbl}")
	public void sMaterialRefractive(String shapeName, Double doubleValue)
	{
		Shape s = data.getShape(shapeName);
		Material material = s.getMaterial();
		material.setRefractive(doubleValue);
	}
}
