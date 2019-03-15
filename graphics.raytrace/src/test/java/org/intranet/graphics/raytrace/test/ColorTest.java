package org.intranet.graphics.raytrace.test;

import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Tuple;
import org.junit.Assert;
import org.junit.Test;

public class ColorTest
{
	@Test
	public void testColor()
	{
		Color c = new Color(-0.5, 0.4, 1.7);
		Assert.assertEquals(-0.5, c.getRed(), Tuple.EPSILON);
		Assert.assertEquals(0.4, c.getGreen(), Tuple.EPSILON);
		Assert.assertEquals(1.7, c.getBlue(), Tuple.EPSILON);
	}

	@Test
	public void testAddColor()
	{
		Color c1 = new Color(0.9, 0.6, 0.75);
		Color c2 = new Color(0.7, 0.1, 0.25);
		Color sum = c1.add(c2);
		Color expectedColor = new Color(1.6, 0.7, 1.0);
		Assert.assertEquals(expectedColor, sum);
	}

	@Test
	public void testSubtractColor()
	{
		Color c1 = new Color(0.9, 0.6, 0.75);
		Color c2 = new Color(0.7, 0.1, 0.25);
		Color sum = c1.subtract(c2);
		Tuple expectedColor = new Color(0.2, 0.5, 0.5);
		Assert.assertEquals(expectedColor, sum);
	}

	@Test
	public void testMultiplyColor()
	{
		Color c = new Color(0.2, 0.3, 0.4);
		Color result = c.multiply(2);
		Color expectedColor = new Color(0.4, 0.6, 0.8);
		Assert.assertEquals(expectedColor, result);
	}

	@Test
	public void testMultiplyColorByColor()
	{
		Color c1 = new Color(1, 0.2, 0.4);
		Color c2 = new Color(0.9, 1, 0.1);
		Color result = c1.hadamard_product(c2);
		Color expectedColor = new Color(0.9, 0.2, 0.04);
		Assert.assertEquals(expectedColor, result);
	}
}
