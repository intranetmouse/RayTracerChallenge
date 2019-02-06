package org.intranet.graphics.raytrace;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest
{
	@Test
	public void testColor()
	{
		Tuple c = Tuple.color(-0.5, 0.4, 1.7);
		Assert.assertEquals(-0.5, c.getRed(), Tuple.EPSILON);
		Assert.assertEquals(0.4, c.getGreen(), Tuple.EPSILON);
		Assert.assertEquals(1.7, c.getBlue(), Tuple.EPSILON);
	}

	@Test
	public void testAddColor()
	{
		Tuple c1 = Tuple.color(0.9, 0.6, 0.75);
		Tuple c2 = Tuple.color(0.7, 0.1, 0.25);
		Tuple sum = c1.add(c2);
		Tuple expectedColor = Tuple.color(1.6, 0.7, 1.0);
		Assert.assertEquals(expectedColor, sum);
	}

	@Test
	public void testSubtractColor()
	{
		Tuple c1 = Tuple.color(0.9, 0.6, 0.75);
		Tuple c2 = Tuple.color(0.7, 0.1, 0.25);
		Tuple sum = c1.subtract(c2);
		Tuple expectedColor = Tuple.color(0.2, 0.5, 0.5);
		Assert.assertEquals(expectedColor, sum);
	}

	@Test
	public void testMultiplyColor()
	{
		Tuple c = Tuple.color(0.2, 0.3, 0.4);
		Tuple result = c.multiply(2);
		Tuple expectedColor = Tuple.color(0.4, 0.6, 0.8);
		Assert.assertEquals(expectedColor, result);
	}

	@Test
	public void testMultiplyColorByColor()
	{
		Tuple c1 = Tuple.color(1, 0.2, 0.4);
		Tuple c2 = Tuple.color(0.9, 1, 0.1);
		Tuple result = c1.hadamard_product(c2);
		Tuple expectedColor = Tuple.color(0.9, 0.2, 0.04);
		Assert.assertEquals(expectedColor, result);
	}
}
