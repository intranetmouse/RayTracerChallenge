package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.CubeMapPattern;
import org.intranet.graphics.raytrace.surface.Pattern;
import org.intranet.graphics.raytrace.surface.StripePattern;
import org.intranet.graphics.raytrace.surface.TextureMapPattern;
import org.intranet.graphics.raytrace.surface.map.CubeSide;
import org.intranet.graphics.raytrace.surface.map.CylindricalUvMap;
import org.intranet.graphics.raytrace.surface.map.PlanarUvMap;
import org.intranet.graphics.raytrace.surface.map.SphericalUvMap;
import org.intranet.graphics.raytrace.surface.map.UvMap;
import org.intranet.graphics.raytrace.surface.pattern2d.AlignCheckUvPattern;
import org.intranet.graphics.raytrace.surface.pattern2d.CheckersUvPattern;
import org.intranet.graphics.raytrace.surface.pattern2d.ImageUvPattern;
import org.intranet.graphics.raytrace.surface.pattern2d.UvPattern;
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
		DoublePair values = map.map(point);

		data.putDouble(uName, values.getFirst());
		data.putDouble(vName, values.getSecond());
	}

	@When("{identifier} ← cube_map\\({identifier}, {identifier}, {identifier}, {identifier}, {identifier}, {identifier})")
	public void patternCube_mapLeftFrontRightBackUpDown(String patternName,
		String leftName, String frontName, String rightName, String backName,
		String upName, String downName)
	{
		UvPattern left = data.getUvPattern(leftName);
		UvPattern front = data.getUvPattern(frontName);
		UvPattern right = data.getUvPattern(rightName);
		UvPattern back = data.getUvPattern(backName);
		UvPattern up = data.getUvPattern(upName);
		UvPattern down = data.getUvPattern(downName);
		Pattern pattern = new CubeMapPattern(left, front, right, back, up, down);
		// Write code here that turns the phrase above into concrete actions
		data.putPattern(patternName, pattern);
	}

	@Given("{identifier} ← uv_image\\({identifier})")
	public void patternUv_imageCanvas(String uvMapName, String canvasName)
	{
		Canvas canvas = data.getCanvas(canvasName);

		UvPattern uvMap = new ImageUvPattern(canvas);

		data.putUvPattern(uvMapName, uvMap);
	}
}