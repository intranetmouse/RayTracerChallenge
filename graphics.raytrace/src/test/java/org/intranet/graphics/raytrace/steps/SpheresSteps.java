package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SpheresSteps
	extends StepsParent
{
	public SpheresSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← {matrix} * {matrixRotationPiDiv}")
	public void mScalingRotation_zPi(String matrixName, Matrix scalingMtx,
		Matrix rotateZMtx)
	{
		Matrix product = scalingMtx.multiply(rotateZMtx);
		data.put(matrixName, product);
	}

//	@When(wordPattern + " = vector\\(√" + doublePattern + "\\/" + doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern +
//		", √" + doublePattern + "\\/" + doublePattern + "\\)")
//	public void nNormal_atSPoint(String expectedVectorName, double xNum,
//		double xDenom, double yNum, double yDenom, double zNum, double zDenom)
//	{
//		Vector expectedVector = data.getVector(expectedVectorName);
//
//		Vector actualVector = new Vector(Math.sqrt(xNum) / xDenom,
//			Math.sqrt(yNum) / yDenom, Math.sqrt(zNum) / zDenom);
//
//		Assert.assertEquals(expectedVector, actualVector);
//	}


	@Then("{identifier}[{int}] = {dbl}")
	public void xs(String intersectionName, int index, double expectedValue)
	{
		IntersectionList ilist = data.getIntersectionList(intersectionName);
		Intersection intersection = ilist.get(index);
		double value = intersection.getDistance();
		Assert.assertEquals(expectedValue, value, Tuple.EPSILON);
	}

	@Then("{identifier} = normalize\\({identifier})")
	public void nNormalizeN(String resultVectorName, String vectorName)
	{
		Vector expectedVector = data.getVector(resultVectorName);

		Vector vectorToNormalized = data.getVector(vectorName);
		Vector normalizedVector = vectorToNormalized.normalize();

		Assert.assertEquals(expectedVector, normalizedVector);
	}

	@Then("{identifier} = {identifier}.material.color")
	public void cInnerMaterialColor(String colorName, String objectName)
	{
		Color color = data.getColor(colorName);
		Shape object = data.getShape(objectName);
		Assert.assertEquals(color, object.getMaterial().getColor());
	}
}
