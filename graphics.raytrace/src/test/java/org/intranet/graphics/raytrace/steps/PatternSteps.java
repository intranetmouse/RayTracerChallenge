package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.PlanarUvMap;
import org.intranet.graphics.raytrace.surface.SphericalUvMap;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.intranet.graphics.raytrace.surface.TextureMapPattern;
import org.intranet.graphics.raytrace.surface.UvMap;
import org.intranet.graphics.raytrace.primitive.Pair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.CheckersUvPattern;
import org.intranet.graphics.raytrace.surface.UvPattern;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatternSteps
	extends StepsParent
{
	public PatternSteps(RaytraceData data)
	{
		super(data);
	}

	@Then("{identifier}.a = {identifier}")
	public void patternA_eqColor(String patternName, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);
		StripePattern pattern = (StripePattern)data.getPattern(patternName);
		Color actualColor = pattern.getA();
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("{identifier}.b = {identifier}")
	public void patternB_eqColor(String patternName, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);
		StripePattern pattern = (StripePattern)data.getPattern(patternName);
		Color actualColor = pattern.getB();
		Assert.assertEquals(expectedColor, actualColor);
	}

	@Given("{identifier} ← uv_checkers\\({int}, {int}, black, white)")
	public void checkersUv_checkersBlackWhite(String mapName, int uSquares,
		int vSquares)
	{
		UvPattern uv = new CheckersUvPattern(uSquares, vSquares, Color.BLACK,
			Color.WHITE);

		data.put(mapName, uv);
	}

	@When("{identifier} ← uv_pattern_at\\({identifier}, {double}, {double})")
	public void colorUv_pattern_atCheckers(String colorName,
		String uvPatternName, Double u, Double v)
	{
		UvPattern uvPattern = data.getUvPattern(uvPatternName);

		Color color = uvPattern.colorAt(u, v);

		data.put(colorName, color);
	}

	@When("\\({identifier}, {identifier}) ← spherical_map\\({identifier})")
	public void uVSpherical_mapP(String uVariable, String vVariable,
		String pointName)
	{
		Point p = data.getPoint(pointName);
		UvMap uvMap = new SphericalUvMap();
		Pair<Double> uv = uvMap.map(p);
		data.put(uVariable, uv.getFirst());
		data.put(vVariable, uv.getSecond());
	}

	@When("\\({identifier}, {identifier}) ← planar_map\\({identifier})")
	public void uVPlanar_mapP(String uVariable, String vVariable,
		String pointName)
	{
		Point p = data.getPoint(pointName);
		UvMap uvMap = new PlanarUvMap();
		Pair<Double> uv = uvMap.map(p);
		data.put(uVariable, uv.getFirst());
		data.put(vVariable, uv.getSecond());
	}

	@Given("{identifier} ← {pointSSN}")
	public void pPoint22(String pointToAssign, Point p)
	{
		data.put(pointToAssign, p);
	}

	@Given("{identifier} ← texture_map\\({identifier}, spherical_map)")
	public void patternTexture_mapCheckersSpherical_map(String patternName,
		String uvPatternName)
	{
		UvPattern uvPattern = data.getUvPattern(uvPatternName);

		UvMap uvMap = new SphericalUvMap();
		data.put(patternName, new TextureMapPattern(uvPattern, uvMap));
	}
}