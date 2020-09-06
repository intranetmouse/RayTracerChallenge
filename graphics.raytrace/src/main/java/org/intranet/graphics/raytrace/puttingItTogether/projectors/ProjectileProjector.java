package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.IOException;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.puttingItTogether.projector.Projector;
import org.junit.Test;

public class ProjectileProjector
	implements Projector
{
	@Test
	public void testProjectile()
	{
		// projectile starts one unit above the origin.
		// velocity is normalized to 1 unit/tick.
		Projectile p = new Projectile(
			new Point(0, 1, 0),
			new Vector(1, 1, 0).normalize());

		// gravity -0.1 unit/tick, and wind is -0.01 unit/tick.
		Environment e = new Environment(
			new Vector(0, -0.1, 0), new Vector(-0.01, 0, 0));

		while (p.getPosition().getY() > 0)
		{
			p = p.tick(e);
		}
	}

	@Test
	public void testProjectileCanvas()
		throws IOException
	{
		Canvas c = createProjectileCanvas();

		c.writeFile("projectile.ppm");
	}

	private static Canvas createProjectileCanvas()
	{
		int canvasHeight = 550;
		int canvasWidth = 900;
		Canvas canvas = new Canvas(canvasWidth, canvasHeight);

		new ProjectileProjector().projectToCanvas(canvas, false);
		return canvas;
	}

	@Override
	public String getName()
	{ return "Projectile"; }

	@Override
	public void projectToCanvas(Canvas canvas, boolean parallel)
	{
		// projectile starts one unit above the origin.
		// velocity is normalized to 1 unit/tick.
		Projectile p = new Projectile(
			new Point(0, 1, 0),
			new Vector(1, 1.8, 0).normalize().multiply(11.25));

		// gravity -0.1 unit/tick, and wind is -0.01 unit/tick.
		Environment e = new Environment(
			new Vector(0, -0.1, 0), new Vector(-0.01, 0, 0));

		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();
		double projectileMaxHeight = canvasHeight / 550.0;
		double projectileMaxWidth = canvasWidth / 900.0;
		Color color = new Color(1, 1, 0);
		do
		{
			int x = (int)(p.getPosition().getX() * projectileMaxWidth);
			int y = (int)(p.getPosition().getY() * projectileMaxHeight);
			if (x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight)
				System.out.printf("Got x,y %d,%d out of bounds for canvas w,h %d,%d\n",
					x, y, canvasWidth, canvasHeight);
			canvas.writePixel(x, canvasHeight - y - 1, color);

			p = p.tick(e);
		}
		while (p.getPosition().getY() > 0);
	}
}
