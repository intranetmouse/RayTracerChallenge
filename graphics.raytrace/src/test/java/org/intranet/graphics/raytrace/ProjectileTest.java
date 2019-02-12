package org.intranet.graphics.raytrace;

import java.io.IOException;

import org.intranet.graphics.raytrace.projectile.Environment;
import org.intranet.graphics.raytrace.projectile.Projectile;
import org.junit.Test;

public class ProjectileTest
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
		// projectile starts one unit above the origin.
		// velocity is normalized to 1 unit/tick.
		Projectile p = new Projectile(
			new Point(0, 1, 0),
			new Vector(1, 1.8, 0).normalize().multiply(11.25));

		// gravity -0.1 unit/tick, and wind is -0.01 unit/tick.
		Environment e = new Environment(
			new Vector(0, -0.1, 0), new Vector(-0.01, 0, 0));

		int canvasHeight = 550;
		int canvasWidth = 900;
		Canvas c = new Canvas(canvasWidth, canvasHeight);

		Color color = new Color(1, 1, 0);
		do
		{
			int x = (int)p.getPosition().getX();
			int y = (int)p.getPosition().getY();
			if (x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight)
				System.out.printf("Got x,y %d,%d out of bounds for canvas w,h %d,%d\n",
					x, y, canvasWidth, canvasHeight);
			c.writePixel(x, canvasHeight - y - 1, color);

			p = p.tick(e);
		}
		while (p.getPosition().getY() > 0);

		c.writeFile("projectile.ppm");
	}
}
