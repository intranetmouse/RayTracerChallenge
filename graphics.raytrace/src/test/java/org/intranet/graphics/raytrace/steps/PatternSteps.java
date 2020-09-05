package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.AlignCheckUvPattern;
import org.intranet.graphics.raytrace.surface.CheckersUvPattern;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.CubeSide;
import org.intranet.graphics.raytrace.surface.CylindricalUvMap;
import org.intranet.graphics.raytrace.surface.PlanarUvMap;
import org.intranet.graphics.raytrace.surface.SphericalUvMap;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.intranet.graphics.raytrace.surface.TextureMapPattern;
import org.intranet.graphics.raytrace.surface.UvMap;
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

		data.putUvPattern(mapName, uv);
	}

	@When("{identifier} ← uv_pattern_at\\({identifier}, {double}, {double})")
	public void colorUv_pattern_atCheckers(String colorName,
		String uvPatternName, Double u, Double v)
	{
		UvPattern uvPattern = data.getUvPattern(uvPatternName);

		Color color = uvPattern.colorAt(u, v);

		data.putColor(colorName, color);
	}

	@When("\\({identifier}, {identifier}) ← spherical_map\\({identifier})")
	public void uVSpherical_mapP(String uVariable, String vVariable,
		String pointName)
	{
		Point p = data.getPoint(pointName);
		UvMap uvMap = new SphericalUvMap();
		DoublePair uv = uvMap.map(p);
		data.putDouble(uVariable, uv.getFirst());
		data.putDouble(vVariable, uv.getSecond());
	}

	@When("\\({identifier}, {identifier}) ← planar_map\\({identifier})")
	public void uVPlanar_mapP(String uVariable, String vVariable,
		String pointName)
	{
		Point p = data.getPoint(pointName);
		UvMap uvMap = new PlanarUvMap();
		DoublePair uv = uvMap.map(p);
		data.putDouble(uVariable, uv.getFirst());
		data.putDouble(vVariable, uv.getSecond());
	}

	@When("\\({identifier}, {identifier}) ← cylindrical_map\\({identifier})")
	public void uVCylindrical_mapP(String uVariable, String vVariable,
		String pointName)
	{
		Point p = data.getPoint(pointName);
		UvMap uvMap = new CylindricalUvMap();
		DoublePair uv = uvMap.map(p);
		data.putDouble(uVariable, uv.getFirst());
		data.putDouble(vVariable, uv.getSecond());
	}

	@Given("{identifier} ← {pointSSN}")
	public void pPoint22(String pointToAssign, Point p)
	{
		data.putPoint(pointToAssign, p);
	}

	@Given("{identifier} ← texture_map\\({identifier}, spherical_map)")
	public void patternTexture_mapCheckersSpherical_map(String patternName,
		String uvPatternName)
	{
		UvPattern uvPattern = data.getUvPattern(uvPatternName);

		UvMap uvMap = new SphericalUvMap();
		data.putPattern(patternName, new TextureMapPattern(uvPattern, uvMap));
	}

	@Given("{identifier} ← uv_align_check\\({identifier}, {identifier}, {identifier}, {identifier}, {identifier})")
	public void patternUv_align_checkMainUlUrBlBr(String uvPatternName,
		String color1name, String color2name, String color3name,
		String color4name, String color5name)
	{
		Color main = data.getColor(color1name);
		Color ul = data.getColor(color2name);
		Color ur = data.getColor(color3name);
		Color bl = data.getColor(color4name);
		Color br = data.getColor(color5name);

		UvPattern uvPattern = new AlignCheckUvPattern(main, ul, ur, bl, br);
		data.putUvPattern(uvPatternName, uvPattern);
	}

	@When("{identifier} ← face_from_point\\({point})")
	public void faceFace_from_pointPoint(String stringName, Point p)
	{
		CubeSide faceFromPoint = CubeSide.faceFromPoint(p);
		data.putString(stringName, faceFromPoint.toString());
	}

	public static final class CubeMapPattern
	{

	}

	@Then("{identifier} = {string}")
	public void face(String stringName, String expectedString)
	{
		String actualString = data.getString(stringName);
		Assert.assertEquals(expectedString.toUpperCase(), actualString);
	}

	@When("\\({identifier}, {identifier}) ← cube_uv_{identifier}\\({point})")
	public void uVCube_uv_sidePoint(String uName, String vName, String side,
		Point point)
	{
		UvMap map = CubeSide.valueOf(side.toUpperCase());
System.out.println("side="+side+", map type="+map.toString());
		DoublePair values = map.map(point);

		data.putDouble(uName, values.getFirst());
		data.putDouble(vName, values.getSecond());
	}
}