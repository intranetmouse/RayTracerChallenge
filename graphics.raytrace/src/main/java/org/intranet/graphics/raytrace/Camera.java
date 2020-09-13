package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Matrix;

public class Camera
{
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
		setTransform(transform);
		viewPort = new CameraViewPort(hsize, vsize, fieldOfView);
	}

	private CameraViewPort viewPort;
	public CameraViewPort getViewPort() { return viewPort; }

	public Camera(int hsize, int vsize, double fieldOfView)
	{
		this(hsize, vsize, fieldOfView, Matrix.identity(4));
	}

	@Override
	public String toString()
	{
		return "Camera [viewPort=" + viewPort + ", transform=" + transform + "]";
	}
}
