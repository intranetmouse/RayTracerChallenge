package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.PointLight;
import org.intranet.graphics.raytrace.Tuple;
import org.intranet.graphics.raytrace.Vector;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
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
		Color color = Material.lighting(material, pointLight, position, eyev, normalv);
		data.put(resultingColorName, color);
	}


	@Then(wordPattern + ".color = color\\(" + threeDoublesPattern + "\\)")
	public void mColorColor(String materialName, double red, double green,
		double blue)
	{
		Color expectedColor = new Color(red, green, blue);
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedColor, m.getColor());
	}

	@Then(wordPattern + ".ambient = " + doublePattern)
	public void mAmbient(String materialName, double expectedAmbient)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedAmbient, m.getAmbient(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".diffuse = " + doublePattern)
	public void mDiffuse(String materialName, double expectedDiffuse)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedDiffuse, m.getDiffuse(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".specular = " + doublePattern)
	public void mSpecular(String materialName, double expectedSpecular)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedSpecular, m.getSpecular(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".shininess = " + doublePattern)
	public void mShininess(String materialName, double expectedShininess)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedShininess, m.getShininess(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".reflective = " + doublePattern)
	public void mReflective(String materialName, double expectedReflective)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedReflective, m.getReflective(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".transparency = " + doublePattern)
	public void mTransparency(String materialName, double expectedTransparency)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedTransparency, m.getTransparency(), Tuple.EPSILON);
	}

	@Then(wordPattern + ".refractive_index = " + doublePattern)
	public void mRefractive_index(String materialName, double expectedRefractive)
	{
		Material m = data.getMaterial(materialName);
		Assert.assertEquals(expectedRefractive, m.getRefractive(), Tuple.EPSILON);
	}

}
