package org.intranet.graphics.raytrace.puttingItTogether;

import java.io.IOException;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.junit.Test;

public class ClockProjector
	implements Projector
{
	@Test
	public void testClock() throws IOException
	{
		int imageWidth = 301;
		int imageHeight = 301;
		Canvas canvas = new Canvas(imageWidth, imageHeight);

		projectToCanvas(canvas);

		canvas.writeFile("clock.ppm");
	}

	@Override
	public void projectToCanvas(Canvas canvas)
	{
		int imageWidth = Math.min(canvas.getWidth(), canvas.getHeight());
		int imageCenter = imageWidth / 2 + 1;
		int circleRadius = (int)(imageWidth * 0.7 / 2);

		Point p = new Point(1, 0, 0);

		Matrix scale = Matrix.newScaling(circleRadius, circleRadius, 1);
		Matrix moveToCenter = Matrix.newTranslation(imageCenter, imageCenter, 0);

		Matrix scaleMove = moveToCenter.multiply(scale);

		Color red = new Color(1, 0, 1);
		Color color = new Color(1, 1, 0);

		for (int i = 0; i < 12; i++)
		{
			Matrix rotate = Matrix.newRotationZ(Math.PI / 6 * i);
			Point transformedPoint = scaleMove.multiply(rotate).multiply(p);
			canvas.writePixel((int)transformedPoint.getX(),
				(int)transformedPoint.getY(), color);
		}
		for (int i = imageCenter - 1; i <= imageCenter + 1; i++)
			for (int j = imageCenter - 1; j <= imageCenter + 1; j++)
				canvas.writePixel(i, j, red);
	}

	@Override
	public String getName()
	{ return "Clock"; }
}
