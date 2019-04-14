package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.PointLight;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.Vector;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class MaterialsSteps
	extends StepsParent
{
	public MaterialsSteps(RaytraceData data)
	{
		super(data);
	}

	@Given(wordPattern + " ← material\\(\\)")
	public void mMaterial(String materialName)
	{
		data.put(materialName, new Material());
	}

	@Given(wordPattern + ".ambient ← " + doublePattern)
	public void mAssignAmbient(String materialName, double ambientValue)
	{
		Material m = data.getMaterial(materialName);
		m.setAmbient(ambientValue);
	}


	@When(wordPattern + " ← lighting\\(" + wordPattern + ", " + wordPattern +
		", " + wordPattern + ", " + wordPattern + ", " + wordPattern + "\\)")
	public void resultLightingMLightPositionEyevNormalv(
		String resultingColorName, String materialName, String pointLightName,
		String positionName, String eyeVectorName, String normalVectorName)
	{
		Material material = data.getMaterial(materialName);
		Point position = data.getPoint(positionName);
		PointLight pointLight = data.getPointLight(pointLightName);
		Vector eyev = data.getVector(eyeVectorName);
		Vector normalv = data.getVector(normalVectorName);
		Color color = Tracer.lighting(material, pointLight, position, eyev,
			normalv, false);
		data.put(resultingColorName, color);
	}

	@When(wordPattern + " ← lighting\\(" + wordPattern + ", " + wordPattern +
		", " + wordPattern + ", " + wordPattern + ", " + wordPattern + ", " +
		wordPattern + "\\)")
	public void resultLightingMLightPositionEyevNormalvIn_shadow(
		String resultingColorName, String materialName, String pointLightName,
		String positionName, String eyeVectorName, String normalVectorName,
		String inShadowName)
	{
		Material material = data.getMaterial(materialName);
		Point position = data.getPoint(positionName);
		PointLight pointLight = data.getPointLight(pointLightName);
		Vector eyev = data.getVector(eyeVectorName);
		Vector normalv = data.getVector(normalVectorName);
		boolean inShadow = data.getBoolean(inShadowName);
		Color color = Tracer.lighting(material, pointLight, position, eyev,
			normalv, inShadow);
		data.put(resultingColorName, color);
	}

}
