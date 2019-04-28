package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.stream.StreamSupport;

import org.intranet.graphics.raytrace.AcrossDownTraversal;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Material;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.shape.Sphere;

public class PhongShadingSphereProjector
	implements Projector
{
	private Color sphereColor = new Color(1.0, 0.2, 1.0);
	private final SphereProjectionType projType;

	@Override
	public String getName()
	{
		return projType.getName();
	}

	public PhongShadingSphereProjector(SphereProjectionType t)
	{
		this.projType = t;
	}

	@Override
	public void projectToCanvas(Canvas canvas, boolean parallel)
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

		StreamSupport.stream(new AcrossDownTraversal(screenWidth, screenHeight), parallel)
			.forEach(pixel -> {
				int screenY = pixel.getY();
				int screenX = pixel.getX();
				double worldY = halfWallHeight - screenY * pixelSize;
				double worldX = -halfWallWidth + screenX * pixelSize;
				Ray ray = new Ray(rayOrigin, new Vector(worldX, worldY, wallZ).normalize());

				IntersectionList ilist = sphere.intersections(ray);

				if (ilist.count() > 0)
				{
					Color colorAtPoint = determineColorAtPoint(ilist, ray,
						light);
					canvas.writePixel(screenX, screenY, colorAtPoint);
				}
			});
	}

	private Color determineColorAtPoint(IntersectionList ilist, Ray ray,
		PointLight light)
	{
		Intersection hit = ilist.get(0);
		Point point = ray.position(hit.getDistance());
		Shape sceneObject = hit.getObject();
		Vector normalV = sceneObject.normalAt(point);
		Vector eyeV = ray.getDirection().negate();

		Material material = sceneObject.getMaterial();
		return Tracer.lighting(material, light, point, eyeV, normalV, false);
	}
}
