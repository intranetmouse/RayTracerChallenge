package org.intranet.graphics.raytrace.test;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.surface.map.Canvas;
import org.junit.Assert;
import org.junit.Test;

public class CanvasTest
{
//	private static final Color white = new Color(1, 1, 1);
	private static final Color black = new Color(0, 0, 0);
	private static final Color red = new Color(1, 0, 0);

	@Test
	public void testCreatingCanvas()
	{
		Canvas canvas = new Canvas(10, 20);
		Assert.assertEquals(10, canvas.getWidth());
		Assert.assertEquals(20, canvas.getHeight());

		for (int w = 0; w < canvas.getWidth(); w++)
			for (int h = 0; h < canvas.getHeight(); h++)
			{
				Color color = canvas.getPixelColor(w, h);
				Assert.assertEquals(black, color);
			}
	}

	@Test
	public void testWritingPixelsToCanvas()
	{
		Canvas canvas = new Canvas(10, 20);
		canvas.writePixel(2, 3, red);
		Assert.assertEquals(red, canvas.getPixelColor(2, 3));
	}

	@Test
	public void testConstructPpmHeader()
	{
		Canvas c = new Canvas(5, 3);
		List<String> ppm = c.toPpm();

		Assert.assertEquals("Row 1", "P3", ppm.get(0));
		Assert.assertEquals("Row 2", "5 3", ppm.get(1));
		Assert.assertEquals("Row 3", "255", ppm.get(2));
	}

	@Test
	public void testConstructPpmPixelData()
	{
		Canvas c = new Canvas(5, 3);
		Color c1 = new Color(1.5, 0, 0);
		Color c2 = new Color(0, 0.5, 0);
		Color c3 = new Color(-0.5, 0, 1);
		c.writePixel(0, 0, c1);
		c.writePixel(2, 1, c2);
		c.writePixel(4, 2, c3);
		List<String> ppm = c.toPpm();

		Assert.assertEquals("Row 4", "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0", ppm.get(3));
		Assert.assertEquals("Row 5", "0 0 0 0 0 0 0 128 0 0 0 0 0 0 0", ppm.get(4));
		Assert.assertEquals("Row 6", "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255", ppm.get(5));
	}

	@Test
	public void testConstructPpmPixelLongLineData()
	{
		Canvas c = new Canvas(10, 2);
		Color c1 = new Color(1, 0.8, 0.6);
		c.writeAllPixels(c1);
		List<String> ppm = c.toPpm();

		Assert.assertEquals("Row 4", "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204", ppm.get(3));
		Assert.assertEquals("Row 5", "153 255 204 153 255 204 153 255 204 153 255 204 153", ppm.get(4));
		Assert.assertEquals("Row 6", "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204", ppm.get(5));
		Assert.assertEquals("Row 7", "153 255 204 153 255 204 153 255 204 153 255 204 153", ppm.get(6));
	}
}
