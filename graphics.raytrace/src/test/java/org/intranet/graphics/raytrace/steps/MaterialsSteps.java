package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;

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

	@When(wordPattern + " ← lighting\\(" + wordPattern + ", " + wordPattern
		+ ", point\\(" + threeDoublesPattern + "\\), " + wordPattern + ", "
		+ wordPattern + ", (true|false)\\)")
	public void cLightingMLightPointEyevNormalvFalse(String resultingColorName,
		String materialName, String pointLightName, double p1x, double p1y,
		double p1z, String eyeVectorName, String normalVectorName,
		String inShadowString)
	{
		Material material = data.getMaterial(materialName);
		Point position = new Point(p1x, p1y, p1z);
		PointLight pointLight = data.getPointLight(pointLightName);
		Vector eyev = data.getVector(eyeVectorName);
		Vector normalv = data.getVector(normalVectorName);
		boolean inShadow = "true".equals(inShadowString);
		Color color = Tracer.lighting(material, pointLight, position, eyev,
			normalv, inShadow);
		data.put(resultingColorName, color);
	}

}
