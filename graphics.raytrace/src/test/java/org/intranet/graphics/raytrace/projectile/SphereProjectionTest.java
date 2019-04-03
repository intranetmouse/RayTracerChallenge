package org.intranet.graphics.raytrace.projectile;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Matrix;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Sphere;
import org.intranet.graphics.raytrace.Vector;

public class SphereProjectionTest
{
	enum SphereProjectionType
	{
		NORMAL("Normal", null),
		SHRINK_Y("Shrink Y", Matrix.newScaling(1, 0.5, 1)),
		SHRINK_X("Shrink X", Matrix.newScaling(0.5, 1, 1)),
		SHRINK_ROTATE("Shrink + Rotate", Matrix.newRotationZ(Math.PI / 4).multiply(Matrix.newScaling(0.5, 1, 1))),
		SHRINK_SKEW("Shink + Skew", Matrix.shearing(1, 0, 0, 0, 0, 0).multiply(Matrix.newScaling(0.5, 1, 1)));

		private final String name;
		public String getName() { return name; }

		private Matrix transform;
		public Matrix getTransform() { return transform; }

		private SphereProjectionType(String name, Matrix t) {
			this.name = name;
			transform = t;
		}
	}

	public static void renderSphereProjectionTo(Canvas c, SphereProjectionType t)
	{
		Point rayOrigin = new Point(0, 0, -5);
		Sphere sphere = new Sphere();
		if (t.getTransform() != null)
			sphere.setTransform(t.getTransform());
		Color color = new Color(1.0, 0.0, 0.0);

		double wallSize = 7;

		int screenHeight = c.getHeight();
		int screenWidth = c.getWidth();
		double halfWallHeight = wallSize / 2;
		double halfWallWidth = wallSize / 2;

		for (int screenY = 0; screenY < screenHeight; screenY++)
		{
			double y = -halfWallHeight + screenY * 1.0 / screenHeight * wallSize;
			for (int screenX = 0; screenX < screenWidth; screenX++)
			{
				double x = -halfWallWidth + screenX * 1.0 / screenWidth * wallSize;
				Ray ray = new Ray(rayOrigin, new Vector(x, y, 10));

				IntersectionList ilist = sphere.intersections(ray);

				if (ilist.count() > 0)
				{
					Intersection intersection = ilist.get(0);
					double dist = intersection.getDistance();
					// distance range is 0.4 to 0.47808764940239135
					double colorMultiplier = 1 - (dist - 0.4) * 10;
					c.writePixel(screenX, screenY, color.multiply(colorMultiplier));
				}
			}
		}
	}
}
