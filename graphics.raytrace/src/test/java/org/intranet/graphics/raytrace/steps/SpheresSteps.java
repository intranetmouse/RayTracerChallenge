package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

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
		data.putMatrix(matrixName, product);
	}

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
