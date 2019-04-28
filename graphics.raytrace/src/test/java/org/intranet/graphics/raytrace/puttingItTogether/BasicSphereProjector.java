package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.stream.StreamSupport;

import org.intranet.graphics.raytrace.AcrossDownTraversal;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;
import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.IntersectionList;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.Sphere;

public class BasicSphereProjector
	implements Projector
{
	private Color color = new Color(1.0, 0.0, 0.0);
	private final SphereProjectionType projType;

	@Override
	public String getName()
	{
		return projType.getName();
	}

	public BasicSphereProjector(SphereProjectionType t)
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
		if (projType.getTransform() != null)
			sphere.setTransform(projType.getTransform());

		int screenHeight = canvas.getHeight();
		int screenWidth = canvas.getWidth();
		double halfWallHeight = wallSize / 2;
		double halfWallWidth = wallSize / 2;

		double pixelSize = wallSize / Math.min(screenWidth, screenHeight);

		StreamSupport.stream(new AcrossDownTraversal(screenWidth, screenHeight), parallel).forEach(pixel ->
			{
				int screenX = pixel.getX();
				int screenY = pixel.getY();
				double worldY = halfWallHeight - screenY * pixelSize;
				double worldX = -halfWallWidth + screenX * pixelSize;
				Ray ray = new Ray(rayOrigin, new Vector(worldX, worldY, wallZ));

				IntersectionList ilist = sphere.intersections(ray);

				if (ilist.count() > 0)
				{
					Color colorAtPoint = determineColorAtPoint(ilist);
					canvas.writePixel(screenX, screenY, colorAtPoint);
				}
			});
	}

	private Color determineColorAtPoint(IntersectionList ilist)
	{
		Intersection hit = ilist.get(0);
		double dist = hit.getDistance();
//System.out.println("dist="+dist);
		// distance range is 0.4 to 0.47808764940239135
		double colorMultiplier = 1 - (dist - 0.4) * 10;
		Color colorAtPoint = color.multiply(colorMultiplier);
		return colorAtPoint;
	}
}
