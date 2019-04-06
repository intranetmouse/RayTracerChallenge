package org.intranet.graphics.raytrace;

public class Camera
{
	private int hsize;
	public int getHsize() { return hsize; }

	private int vsize;
	public int getVsize() { return vsize; }

	private double fieldOfView;
	public double getFieldOfView() { return fieldOfView; }

	private Matrix transform;
	public Matrix getTransform() { return transform; }
	public void setTransform(Matrix value) { transform = value; }

	public Camera(int hsize, int vsize, double fieldOfView, Matrix transform)
	{
		this.hsize = hsize;
		this.vsize = vsize;
		this.fieldOfView = fieldOfView;
		this.transform = transform;
	}

	public Camera(int hsize, int vsize, double fieldOfView)
	{
		this(hsize, vsize, fieldOfView, Matrix.identity(4));
	}

	double halfWidth;
	double halfHeight;
	double pixelSize;

	public double getPixelSize()
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
		return pixelSize;
	}

	public Ray rayForPixel(int x, int y)
	{
		// the offset from the edge of the canvas to the pixel's center
		double xOffset = (x + 0.5) * getPixelSize();
		double yOffset = (y + 0.5) * getPixelSize();

		// the untransformed coordinates of the pixel in world space.
		// (remember that the camera looks toward -z, so +x is to the *left*.)
		double world_x = halfWidth - xOffset;
		double world_y = halfHeight - yOffset;

		// using the camera matrix, transform the canvas point and the origin,
		// and then compute the ray's direction vector.
		// (remember that the canvas is at z=-1)
		Point pixel = transform.inverse().multiply(new Point(world_x, world_y, -1));
		Point origin = transform.inverse().multiply(new Point(0, 0, 0));
		Vector direction = pixel.subtract(origin).normalize();
		return new Ray(origin, direction);
	}

	public void render(World world, Canvas image)
	{
		hsize = image.getWidth();
		vsize = image.getHeight();
		getPixelSize();
//		Canvas image = new Canvas(hsize, vsize);
		for (int y = 0; y < vsize; y++)
		{
			for (int x = 0; x < hsize; x++)
			{
				Ray ray = rayForPixel(x, y);
				Color color = Tracer.colorAt(world, ray);
				image.writePixel(x, y, color);
			}
		}
	}
}
