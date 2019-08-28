package org.intranet.graphics.raytrace;

import java.util.Spliterators.AbstractSpliterator;
import java.util.stream.StreamSupport;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;

public class Camera
{
	private int hsize;
	public int getHsize() { return hsize; }
	public void setHsize(int value) { hsize = value; }

	private int vsize;
	public int getVsize() { return vsize; }
	public void setVsize(int value) { vsize = value; }

	private double fieldOfView;
	public double getFieldOfView() { return fieldOfView; }

	private Matrix transform;
	public Matrix getTransform() { return transform; }
	public void setTransform(Matrix value)
	{
		if (!value.isInvertible())
			throw new IllegalArgumentException(
				"Camera transform must be invertible. " + value);
		transform = value;
	}

	public Camera(int hsize, int vsize, double fieldOfView, Matrix transform)
	{
		this.hsize = hsize;
		this.vsize = vsize;
		this.fieldOfView = fieldOfView;
		setTransform(transform);
		updatePixelSize();
	}

	public Camera(int hsize, int vsize, double fieldOfView)
	{
		this(hsize, vsize, fieldOfView, Matrix.identity(4));
	}

	double halfWidth;
	double halfHeight;

	double pixelSize;
	public double getPixelSize() { return pixelSize; }
	public void updatePixelSize()
	{
		double halfView = Math.tan(fieldOfView / 2);
		double aspectRatio = hsize * 1.0 / vsize;
		if (aspectRatio >= 1)
		{
			halfWidth = halfView;
			halfHeight = halfView / aspectRatio;
		}
		else
		{
			halfHeight = halfView;
			halfWidth = halfView * aspectRatio;
		}
		pixelSize = halfWidth * 2 / hsize;
	}

	public Ray rayForPixel(PixelCoordinate coord)
	{
		// the offset from the edge of the canvas to the pixel's center
		double xOffset = (coord.getX() + 0.5) * pixelSize;
		double yOffset = (coord.getY() + 0.5) * pixelSize;

		// the untransformed coordinates of the pixel in world space.
		// (remember that the camera looks toward -z, so +x is to the *left*.)
		double world_x = halfWidth - xOffset;
		double world_y = halfHeight - yOffset;

		// using the camera matrix, transform the canvas point and the origin,
		// and then compute the ray's direction vector.
		// (remember that the canvas is at z=-1)
		Matrix inverse = transform.inverse();
if (inverse == null) throw new NullPointerException("null inverse from camera transform " + transform);
		Point pixel = inverse.multiply(new Point(world_x, world_y, -1));
		Point origin = inverse.multiply(new Point(0, 0, 0));
		Vector direction = pixel.subtract(origin).normalize();
		return new Ray(origin, direction);
	}

	public void render(World world, Canvas image, boolean parallel,
		AbstractSpliterator<PixelCoordinate> traversal)
	{
		hsize = image.getWidth();
		vsize = image.getHeight();
		StreamSupport.stream(traversal, parallel)
			.forEach(pixel -> {
				Color color = renderPixel(world, pixel);
				image.writePixel(pixel.getX(), pixel.getY(), color);
			});
		image.setDone(true);
	}

	public static int MAX_REFLECTION_RECURSION = 4;
	private Color renderPixel(World world, PixelCoordinate pixel)
	{
		Ray ray = rayForPixel(pixel);
		return Tracer.colorAt(world, ray, MAX_REFLECTION_RECURSION);
	}

	@Override
	public String toString()
	{
		return "Camera [hsize=" + hsize + ", vsize=" + vsize + ", fieldOfView="
			+ fieldOfView + ", transform=" + transform + ", halfWidth="
			+ halfWidth + ", halfHeight=" + halfHeight + ", pixelSize="
			+ pixelSize + "]";
	}
}
