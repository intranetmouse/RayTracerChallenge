package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Matrix;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Sphere;
import org.intranet.graphics.raytrace.Tuple;
import org.intranet.graphics.raytrace.Vector;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SpheresSteps
	extends StepsParent
{
	public SpheresSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← sphere\\(\\)")
	public void sSphere(String sphereName)
	{
		data.put(sphereName, new Sphere());
	}

	@Given(wordPattern + " ← scaling\\(" + threeDoublesPattern +
		"\\) \\* rotation_z\\(π\\/" + doublePattern + "\\)")
	public void mScalingRotation_zPi(String matrixName, double scaleX,
		double scaleY, double scaleZ, double rotateZdenom)
	{
		Matrix scalingMtx = Matrix.newScaling(scaleX, scaleY, scaleZ);
		Matrix rotateZMtx = Matrix.newRotationZ(Math.PI / rotateZdenom);
		Matrix product = scalingMtx.multiply(rotateZMtx);
		data.put(matrixName, product);
	}

	@Given(wordPattern + ".material.ambient ← " + doublePattern)
	public void outerMaterialAmbient(String objectName, double ambientValue)
	{
		Shape obj = data.getShape(objectName);
		obj.getMaterial().setAmbient(ambientValue);
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

	@When(wordPattern + " = vector\\(√" + doublePattern + "\\/" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern +
		", √" + doublePattern + "\\/" + doublePattern + "\\)")
	public void nNormal_atSPoint(String expectedVectorName, double xNum,
		double xDenom, double yNum, double yDenom, double zNum, double zDenom)
	{
		Vector expectedVector = data.getVector(expectedVectorName);

		Vector actualVector = new Vector(Math.sqrt(xNum) / xDenom,
			Math.sqrt(yNum) / yDenom, Math.sqrt(zNum) / zDenom);

		Assert.assertEquals(expectedVector, actualVector);
	}

	@When(wordPattern + " ← " + wordPattern + ".material")
	public void mSMaterial(String materialName, String sphereName)
	{
		Shape obj = data.getShape(sphereName);
		data.put(materialName, obj.getMaterial());
	}

	@When(wordPattern + ".material ← " + wordPattern)
	public void setSphereMaterial(String sphereName, String materialName)
	{
		Material m = data.getMaterial(materialName);
		Shape obj = data.getShape(sphereName);
		obj.setMaterial(m);
	}


	@Then(wordPattern + "\\[" + intPattern + "\\] = " + doublePattern)
	public void xs(String intersectionName, int index, double expectedValue)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionName);
		Intersection intersection = ilist.get(index);
		double value = intersection.getDistance();
		Assert.assertEquals(expectedValue, value, Tuple.EPSILON);
	}

	@Then(wordPattern + ".transform = " + wordPattern)
	public void xs(String objectName, String matrixName)
	{
		Shape obj = data.getShape(objectName);

		Matrix expectedMatrix;
		switch (matrixName)
		{
			case "identity_matrix":
				expectedMatrix = Matrix.identity(4);
				break;
			default:
				expectedMatrix = data.getMatrix(matrixName);
		}

		if (obj != null)
		{
			Assert.assertEquals(expectedMatrix, obj.getTransform());
			return;
		}

		Camera camera = data.getCamera(objectName);
		if (camera != null)
		{
			Assert.assertEquals(expectedMatrix, camera.getTransform());
			return;
		}

		Assert.fail("Unknown object type for object name " + objectName);
	}

	@Then(wordPattern + " = normalize\\(" + wordPattern + "\\)")
	public void nNormalizeN(String resultVectorName, String vectorName)
	{
		Vector expectedVector = data.getVector(resultVectorName);

		Vector vectorToNormalized = data.getVector(vectorName);
		Vector normalizedVector = vectorToNormalized.normalize();

		Assert.assertEquals(expectedVector, normalizedVector);
	}

	@Then(wordPattern + " = material\\(\\)")
	public void mMaterial(String actualMaterialName)
	{
		Material expectedMaterial = new Material();
		Material actualMaterial = data.getMaterial(actualMaterialName);
		Assert.assertEquals(actualMaterial, expectedMaterial);
	}

	@Then(wordPattern + ".material = " + wordPattern)
	public void sphereMaterialEqualsMaterial(String sphereName,
		String actualMaterialName)
	{
		Shape obj = data.getShape(sphereName);
		Material actualMaterial = data.getMaterial(actualMaterialName);
		Assert.assertEquals(actualMaterial, obj.getMaterial());
	}

	@Then(wordPattern + " = " + wordPattern + ".material.color")
	public void cInnerMaterialColor(String colorName, String objectName)
	{
		Color color = data.getColor(colorName);
		Shape object = data.getShape(objectName);
		Assert.assertEquals(color, object.getMaterial().getColor());
	}
}
