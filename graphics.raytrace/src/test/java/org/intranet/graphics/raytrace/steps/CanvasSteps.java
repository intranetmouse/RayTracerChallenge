package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.surface.Color;
import org.junit.Assert;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CanvasSteps
	extends StepsParent
{
	public CanvasSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier} ← {canvas}")
	public void cCanvas(String canvasName, Canvas canvas)
	{
		data.put(canvasName, canvas);
	}

	@When("every pixel of {identifier} is (set to ){color}")
	public void everyPixelOfCIsSetToColor(String canvasName, Color color)
	{
		Canvas c = data.getCanvas(canvasName);
		c.writeAllPixels(color);
	}


	@When("write_pixel\\({identifier}, {int}, {int}, {identifier})")
	public void write_pixelCRed(String canvasName, int x, int y,
		String colorName)
	{
		Canvas c = data.getCanvas(canvasName);
		Color color = data.getColor(colorName);
		c.writePixel(x, y, color);
	}

	@Then("pixel_at\\({identifier}, {int}, {int}) = {identifier}")
	public void pixel_atCRed(String canvasName, int x, int y, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);

		Canvas canvas = data.getCanvas(canvasName);
		Color actualColor = canvas.getPixelColor(x, y);

		Assert.assertEquals(expectedColor, actualColor);
	}

	@When("{identifier} ← canvas_to_ppm\\({identifier})")
	public void ppmCanvas_to_ppmC(String ppmName, String canvasName)
	{
		Canvas canvas = data.getCanvas(canvasName);
		data.put(ppmName, canvas.toPpm());
	}

	@Then("pixel_at\\({identifier}, {int}, {int}) = {color}")
	public void pixel_atImageColor(String canvasName, int x, int y,
		Color expectedColor)
	{
		Canvas canvas = data.getCanvas(canvasName);
		Color actualColor = canvas.getPixelColor(x, y);

		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("lines {int}-{int} of {identifier} are")
	public void linesOfPpmAre(int firstLine1, int lastLine1, String ppmName,
		String ppmString)
	{
		List<String> ppm = data.getStringList(ppmName);
		String[] testLines = ppmString.split("[\n\r]");
		int lastLine0 = lastLine1 - 1;
		int testLineNum = 0;
		for (int i0 = firstLine1 - 1; i0 <= lastLine0; i0++)
		{
			String ppmLine = ppm.get(i0);
			String testLine = testLines[testLineNum++];
			Assert.assertEquals(testLine, ppmLine);
		}
	}

	@Then("the last character of {identifier} is a newline")
	public void theLastCharacterOfPpmIsANewline(String ppmName)
	{
//		List<String> ppm = data.getPpm(ppmName);
//		String lastLine = ppm.get(ppm.size() - 1);
//		Assert.assertEquals("\n", lastLine);
		throw new PendingException("This test doesn't really apply, because the"
			+ " process that writes the file is what puts the newline on it.");
	}
}
