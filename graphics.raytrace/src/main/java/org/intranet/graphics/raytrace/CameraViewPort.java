package org.intranet.graphics.raytrace;

public final class CameraViewPort
{
	public CameraViewPort(int hsize, int vsize, double fieldOfView)
	{
		this.hsize = hsize;
		this.vsize = vsize;
		this.fieldOfView = fieldOfView;
		updatePixelSize();
	}

	private int hsize;
	public int getHsize() { return hsize; }
	public void setHsize(int value) { hsize = value; }

	private int vsize;
	public int getVsize() { return vsize; }
	public void setVsize(int value) { vsize = value; }

	private double fieldOfView;
	public double getFieldOfView() { return fieldOfView; }

	private double halfWidth;
	public double getHalfWidth() { return halfWidth; }

	private double halfHeight;
	public double getHalfHeight() { return halfHeight; }

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
}