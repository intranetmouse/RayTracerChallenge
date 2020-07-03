package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MaterialsSteps
	extends StepsParent
{
	public MaterialsSteps(RaytraceData data)
	{
		super(data);
	}

	@When("{identifier}.ambient ← {dbl}")
	public void materialSetAmbient(String materialName, double newAmbient)
	{
		Material m = data.getMaterial(materialName);
		m.setAmbient(newAmbient);
	}

	@When("{identifier}.diffuse ← {dbl}")
	public void materialSetDiffuse(String materialName, double newDiffuse)
	{
		Material m = data.getMaterial(materialName);
		m.setDiffuse(newDiffuse);
	}

	@When("{identifier}.specular ← {dbl}")
	public void materialSetSpecular(String materialName, double newSpecular)
	{
		Material m = data.getMaterial(materialName);
		m.setSpecular(newSpecular);
	}

	@When("{identifier} ← {identifier}.material")
	public void mSMaterial(String materialName, String shapeName)
	{
		Shape obj = data.getShape(shapeName);
		data.put(materialName, obj.getMaterial());
	}

	@When("{identifier}.material ← {identifier}")
	public void setSphereMaterial(String shapeName, String materialName)
	{
		Material m = data.getMaterial(materialName);
		Shape shape = data.getShape(shapeName);
		shape.setMaterial(m);
	}

	@Given("{identifier} ← {emptyMaterial}")
	public void mSetMaterial(String materialName, Material material)
	{
		data.put(materialName, material);
	}

	@Then("{identifier} = {emptyMaterial}")
	public void mCompareMaterial(String actualMaterialName, Material expectedMaterial)
	{
		Material actualMaterial = data.getMaterial(actualMaterialName);
		Assert.assertEquals(actualMaterial, expectedMaterial);
	}

	@Then("{identifier}.color = {color}")
	public void materialColorEqColor(String materialName, Color expectedColor)
	{
		Material m = data.getMaterial(materialName);
		Color actualColor = m.getColor();
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("{identifier}.ambient = {dbl}")
	public void materialAmbientEqDbl(String materialName, double expectedAmbient)
	{
		Material m = data.getMaterial(materialName);
		double actualAmbient = m.getAmbient();
		Assert.assertEquals(expectedAmbient, actualAmbient, Tuple.EPSILON);
	}

	@Then("{identifier}.diffuse = {dbl}")
	public void materialDiffuseEqDbl(String materialName, double expectedDiffuse)
	{
		Material m = data.getMaterial(materialName);
		double actualDiffuse = m.getDiffuse();
		Assert.assertEquals(expectedDiffuse, actualDiffuse, Tuple.EPSILON);
	}

	@Then("{identifier}.specular = {dbl}")
	public void materialSpecularEqDbl(String materialName, double expectedSpecular)
	{
		Material m = data.getMaterial(materialName);
		double actualSpecular = m.getSpecular();
		Assert.assertEquals(expectedSpecular, actualSpecular, Tuple.EPSILON);
	}

	@Then("{identifier}.shininess = {dbl}")
	public void materialShininessEqDbl(String materialName, double expectedShininess)
	{
		Material m = data.getMaterial(materialName);
		double actualShininess = m.getShininess();
		Assert.assertEquals(expectedShininess, actualShininess, Tuple.EPSILON);
	}

	@Then("{identifier}.reflective = {dbl}")
	public void materialReflectiveEqDbl(String materialName, double expectedReflective)
	{
		Material m = data.getMaterial(materialName);
		double actualReflective = m.getReflective();
		Assert.assertEquals(expectedReflective, actualReflective, Tuple.EPSILON);
	}

	@Then("{identifier}.refractive_index = {dbl}")
	public void materialRefractiveIdxEqDbl(String materialName, double expectedRefractive)
	{
		Material m = data.getMaterial(materialName);
		double actualRefractive = m.getRefractive();
		Assert.assertEquals(expectedRefractive, actualRefractive, Tuple.EPSILON);
	}

	@Then("{identifier}.transparency = {dbl}")
	public void materialTransparencyEqDbl(String materialName, double expectedTransparency)
	{
		Material m = data.getMaterial(materialName);
		double actualTransparency = m.getTransparency();
		Assert.assertEquals(expectedTransparency, actualTransparency, Tuple.EPSILON);
	}

	@When("{identifier} ← lighting\\({identifier}, {identifier}, {identifier}, {identifier}, {identifier})")
	public void resultLightingMLightPositionEyevNormalv(
		String resultingColorName, String materialName, String pointLightName,
		String positionName, String eyeVectorName, String normalVectorName)
	{
		Material material = data.getMaterial(materialName);
		Point position = data.getPoint(positionName);
		PointLight pointLight = data.getPointLight(pointLightName);
		Vector eyev = data.getVector(eyeVectorName);
		Vector normalv = data.getVector(normalVectorName);
		Color color = Tracer.lighting(material, new Sphere(), pointLight,
			position, eyev, normalv, false);
		data.put(resultingColorName, color);
	}

	@When("{identifier} ← lighting\\({identifier}, {identifier}, {identifier}, {identifier}, {identifier}, {identifier})")
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
		Color color = Tracer.lighting(material, new Sphere(), pointLight,
			position, eyev, normalv, inShadow);
		data.put(resultingColorName, color);
	}

	@When("{identifier} ← lighting\\({identifier}, {identifier}, {point}, {identifier}, {identifier}, {boolean})")
	public void cLightingMLightPointEyevNormalvFalse(String resultingColorName,
		String materialName, String pointLightName, Point position,
		String eyeVectorName, String normalVectorName, Boolean inShadow)
	{
		Material material = data.getMaterial(materialName);
		PointLight pointLight = data.getPointLight(pointLightName);
		Vector eyev = data.getVector(eyeVectorName);
		Vector normalv = data.getVector(normalVectorName);
		Color color = Tracer.lighting(material, new Sphere(), pointLight,
			position, eyev, normalv, inShadow);
		data.put(resultingColorName, color);
	}
}
