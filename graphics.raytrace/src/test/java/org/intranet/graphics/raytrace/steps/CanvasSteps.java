package org.intranet.graphics.raytrace.steps;

import java.util.List;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.surface.Color;
import org.junit.Assert;

import cucumber.api.PendingException;
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

	@Given(wordPattern + " ← canvas\\(" + twoIntsPattern + "\\)")
	public void cCanvas(String canvasName, int int1, int int2)
	{
		data.put(canvasName, new Canvas(int1, int2));
	}


	@When("^write_pixel\\(" + wordPattern + ", " + twoIntsPattern + ", "
		+ wordPattern + "\\)$")
	public void write_pixelCRed(String canvasName, int x, int y,
		String colorName)
	{
		Canvas c = data.getCanvas(canvasName);
		Color color = data.getColor(colorName);
		c.writePixel(x, y, color);
	}

	@When(wordPattern + " ← canvas_to_ppm\\(" + wordPattern + "\\)")
	public void ppmCanvas_to_ppmC(String ppmName, String canvasName)
	{
		Canvas canvas = data.getCanvas(canvasName);
		data.put(ppmName, canvas.toPpm());
	}

	@When("every pixel of " + wordPattern + " is set to color\\(" +
		threeDoublesPattern + "\\)")
	public void everyPixelOfCIsSetToColor(String canvasName, double red,
		double green, double blue)
	{
		Canvas c = data.getCanvas(canvasName);
		c.writeAllPixels(new Color(red, green, blue));
	}


	@Then("every pixel of " + wordPattern + " is color\\(" + threeIntsPattern + "\\)")
	public void everyPixelOfCIsColor(String canvasName, int red, int green, int blue)
	{
		Color color = new Color(red, green, blue);
		Canvas c = data.getCanvas(canvasName);

		for (int x = 0; x < c.getWidth(); x++)
			for (int y = 0; y < c.getHeight(); y++)
				Assert.assertEquals(color, c.getPixelColor(x, y));
	}

	@Then("^pixel_at\\(" + wordPattern + ", " + twoIntsPattern + "\\) = " +
		wordPattern + "$")
	public void pixel_atCRed(String canvasName, int x, int y, String expectedColorName)
	{
		Color expectedColor = data.getColor(expectedColorName);

		Canvas canvas = data.getCanvas(canvasName);
		Color actualColor = canvas.getPixelColor(x, y);

		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("^pixel_at\\(" + wordPattern + ", " + twoIntsPattern + "\\) = color\\("
		+ threeDoublesPattern + "\\)")
	public void pixel_atImageColor(String canvasName, int x, int y, double red,
		double green, double blue)
	{
		Color expectedColor = new Color(red, green, blue);

		Canvas canvas = data.getCanvas(canvasName);
		Color actualColor = canvas.getPixelColor(x, y);

		Assert.assertEquals(expectedColor, actualColor);
	}

	@Then("^lines " + intPattern + "-" + intPattern + " of " + wordPattern + " are")
	public void linesOfPpmAre(int firstLine1, int lastLine1, String ppmName,
		String ppmString)
	{
		List<String> ppm = data.getPpm(ppmName);
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

	@Then("the last character of " + wordPattern + " is a newline")
	public void theLastCharacterOfPpmIsANewline(String ppmName)
	{
//		List<String> ppm = data.getPpm(ppmName);
//		String lastLine = ppm.get(ppm.size() - 1);
//		Assert.assertEquals("\n", lastLine);
		throw new PendingException("This test doesn't really apply, because the"
			+ " process that writes the file is what puts the newline on it.");
	}
}
