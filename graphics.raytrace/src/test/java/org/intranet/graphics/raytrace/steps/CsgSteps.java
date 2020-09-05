package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.shape.Csg;
import org.intranet.graphics.raytrace.shape.CsgOperation;
import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CsgSteps
	extends StepsParent
{
	public CsgSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier} ← csg\\({string}, {identifier}, {identifier})")
	public void cCsgS1S2(String objAssign, String csgOperationStr, String shape1Name,
		String shape2Name)
	{
		Shape shape1 = data.getShape(shape1Name);
		Shape shape2 = data.getShape(shape2Name);

		CsgOperation csgType = CsgOperation.get(csgOperationStr);

		Csg csgObj = new Csg(csgType, shape1, shape2);
		data.putShape(objAssign, csgObj);
	}

	@When("{identifier} ← csg\\({string}, {shape}, {shape})")
	public void cCsgS1S2(String objAssign, String csgOperationStr, Shape shape1,
		Shape shape2)
	{
		CsgOperation csgType = CsgOperation.get(csgOperationStr);

		Csg csgObj = new Csg(csgType, shape1, shape2);
		data.putShape(objAssign, csgObj);
	}

	@Then("{identifier}.operation = {string}")
	public void cOperation(String csgName, String expectedOperationName)
	{
		Csg csg = (Csg)data.getShape(csgName);

		CsgOperation actualOperation = csg.getCsgOperation();

		Assert.assertEquals(expectedOperationName, actualOperation.getName());
	}

	@Then("{identifier}.left = {identifier}")
	public void cLeftS1(String csgName, String expectedShapeName)
	{
		Csg csg = (Csg)data.getShape(csgName);
		Shape expectedShape = data.getShape(expectedShapeName);

		Shape actualLeftShape = csg.getLeft();
		Assert.assertEquals(expectedShape, actualLeftShape);
	}

	@Then("{identifier}.right = {identifier}")
	public void cRightS2(String csgName, String expectedShapeName)
	{
		Csg csg = (Csg)data.getShape(csgName);
		Shape expectedShape = data.getShape(expectedShapeName);

		Shape actualRightShape = csg.getRight();
		Assert.assertEquals(expectedShape, actualRightShape);
	}

	@When("{identifier} ← intersection_allowed\\({string}, {boolean}, {boolean}, {boolean})")
	public void resultIntersection_allowedTrueTrueTrue(String resultVarName,
		String opName, boolean lhit, boolean inl, boolean inr)
	{
		CsgOperation operation = CsgOperation.get(opName);

		boolean allowed = operation.intersectionAllowed(lhit, inl, inr);
		data.putBoolean(resultVarName, allowed);
	}

	@When("{identifier} ← filter_intersections\\({identifier}, {identifier})")
	public void resultFilter_intersectionsCXs(String resultVarName, String csgName, String intersectionListName)
	{
		Csg csg = (Csg)data.getShape(csgName);
		IntersectionList ilist = data.getIntersectionList(intersectionListName);
		ilist = csg.filterIntersections(ilist);
		data.putIntersectionList(resultVarName, ilist);
	}

	@Then("{identifier}[{int}] = {identifier}[{int}]")
	public void resultXs(String firstIlistName, Integer int1, String secondIlistName, Integer int2)
	{
		IntersectionList firstIlist = data.getIntersectionList(firstIlistName);
		Intersection firstIntersection = firstIlist.getIntersections().get(int1);

		IntersectionList secondIlist = data.getIntersectionList(secondIlistName);
		Intersection secondIntersection = secondIlist.getIntersections().get(int2);

		Assert.assertEquals(firstIntersection, secondIntersection);
	}
}
