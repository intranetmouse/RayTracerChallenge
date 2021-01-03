package org.intranet.graphics.raytrace;

import java.util.Spliterators.AbstractSpliterator;
import java.util.stream.StreamSupport;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.map.Canvas;

public final class Tracer
{
	public static Ray rayForPixel(Camera camera, PixelCoordinate coord)
	{
		CameraViewPort viewPort = camera.getViewPort();
		// the offset from the edge of the canvas to the pixel's center
		double xOffset = (coord.getX() + 0.5) * viewPort.getPixelSize();
		double yOffset = (coord.getY() + 0.5) * viewPort.getPixelSize();

		// the untransformed coordinates of the pixel in world space.
		// (remember that the camera looks toward -z, so +x is to the *left*.)
		double world_x = viewPort.getHalfWidth() - xOffset;
		double world_y = viewPort.getHalfHeight() - yOffset;

		// using the camera matrix, transform the canvas point and the origin,
		// and then compute the ray's direction vector.
		// (remember that the canvas is at z=-1)
		Matrix cameraTransform = camera.getTransform();
		Matrix inverse = cameraTransform.inverse();
		if (inverse == null)
			throw new NullPointerException(
				"null inverse from camera transform " + cameraTransform);

		Point pixel = inverse.multiply(new Point(world_x, world_y, -1));
		Point origin = inverse.multiply(new Point(0, 0, 0));
		Vector direction = pixel.subtract(origin).normalize();
		return new Ray(origin, direction);
	}

	public static void render(Camera camera, CameraViewPort viewPort,
		World world, Canvas image, boolean parallel,
		AbstractSpliterator<PixelCoordinate> traversal,
		RayTraceStatistics stats)
	{
		int hsize = image.getWidth();
		int vsize = image.getHeight();

		viewPort.setHsize(hsize);
		viewPort.setVsize(vsize);

		stats.start(hsize * vsize);
		new Thread().setPriority(1);
		StreamSupport.stream(traversal, parallel)
			.forEach(pixel -> {
				stats.startPixel();
				renderPixel(camera, world, image, pixel);
				stats.finishPixel();
			});
		image.setDone(true);
		stats.stop();
	}

	public static void renderPixel(Camera camera, World world, Canvas image,
		PixelCoordinate pixel)
	{
		Color color = renderPixel(camera, world, pixel);
		image.writePixel(pixel.getX(), pixel.getY(), color);
	}

	public static int MAX_REFLECTION_RECURSION = 4;

	static Color renderPixel(Camera camera, World world, PixelCoordinate pixel)
	{
		Ray ray = rayForPixel(camera, pixel);
		return IntersectionComputations.colorAt(world, ray,
			MAX_REFLECTION_RECURSION);
	}
}