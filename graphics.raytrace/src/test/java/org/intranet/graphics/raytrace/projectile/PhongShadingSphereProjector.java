package org.intranet.graphics.raytrace.projectile;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Point;
import org.intranet.graphics.raytrace.PointLight;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Sphere;
import org.intranet.graphics.raytrace.Vector;

public class PhongShadingSphereProjector
	implements SphereProjector
{
	private Color sphereColor = new Color(1.0, 0.2, 1.0);

	@Override
	public void projectToCanvas(SphereProjectionType projType, Canvas canvas)
	{
		Point rayOrigin = new Point(0, 0, -5);
		double wallZ = 10;
		double wallSize = 7;

		Sphere sphere = new Sphere();
		sphere.getMaterial().setColor(sphereColor);
		if (projType.getTransform() != null)
			sphere.setTransform(projType.getTransform());

		int screenHeight = canvas.getHeight();
		int screenWidth = canvas.getWidth();
		double halfWallHeight = wallSize / 2;
		double halfWallWidth = wallSize / 2;

		Point light_position = new Point(-10, 10, -10);
		Color light_color = new Color(1, 1, 1);
		PointLight light = new PointLight(light_position, light_color);

		double pixelSize = wallSize / Math.min(screenWidth, screenHeight);

		for (int screenY = 0; screenY < screenHeight; screenY++)
		{
			double worldY = halfWallHeight - screenY * pixelSize;
			for (int screenX = 0; screenX < screenWidth; screenX++)
			{
				double worldX = -halfWallWidth + screenX * pixelSize;
				Ray ray = new Ray(rayOrigin, new Vector(worldX, worldY, wallZ));

				IntersectionList ilist = sphere.intersections(ray);

				if (ilist.count() > 0)
				{
					Color colorAtPoint = determineColorAtPoint(ilist, ray,
						light);
					canvas.writePixel(screenX, screenY, colorAtPoint);
				}
			}
		}
	}

	private Color determineColorAtPoint(IntersectionList ilist, Ray ray,
		PointLight light)
	{
		Intersection hit = ilist.get(0);
		Point point = ray.position(hit.getDistance());
		Sphere sphere = hit.getObject();
		Vector normal = sphere.normalAt(point);
		Vector eye = ray.getDirection().negate();

		Material material = sphere.getMaterial();
		return material.lighting(light, point, eye, normal);
	}
}
