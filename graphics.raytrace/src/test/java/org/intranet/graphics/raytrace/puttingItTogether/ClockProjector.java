package org.intranet.graphics.raytrace.puttingItTogether;

import java.io.IOException;
import java.util.stream.IntStream;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.Color;
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

		projectToCanvas(canvas, false);

		canvas.writeFile("clock.ppm");
	}

	@Override
	public void projectToCanvas(Canvas canvas, boolean parallel)
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

		IntStream stream = IntStream.range(0, 12);
		if (parallel) stream = stream.parallel();
		stream.forEach(i -> {
			Matrix rotate = Matrix.newRotationZ(Math.PI / 6 * i);
			Point transformedPoint = scaleMove.multiply(rotate).multiply(p);
			canvas.writePixel((int)transformedPoint.getX(),
				(int)transformedPoint.getY(), color);
		});

		for (int i = imageCenter - 1; i <= imageCenter + 1; i++)
			for (int j = imageCenter - 1; j <= imageCenter + 1; j++)
				canvas.writePixel(i, j, red);
	}

	@Override
	public String getName()
	{ return "Clock"; }
}
