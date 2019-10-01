package org.intranet.graphics.raytrace.steps;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CameraSteps
	extends StepsParent
{

	public CameraSteps(RaytraceData data)
	{
		super(data);
	}

	@When(wordPattern + " ← camera\\(" + wordPattern + ", " + wordPattern + ", "
		+ wordPattern + "\\)")
	public void cCameraHsizeVsizeField_of_view(String cameraName,
		String hsizeName, String vsizeName, String fieldOfViewName)
	{
		int hsize = data.getInt(hsizeName);
		int vsize = data.getInt(vsizeName);
		double fieldOfView = data.getDouble(fieldOfViewName);

		Camera c = new Camera(hsize, vsize, fieldOfView);
		data.put(cameraName, c);
	}

	@When(wordPattern + " ← camera\\(" + intPattern + ", " + intPattern + ", π\\/"
		+ doublePattern + "\\)")
	public void cCameraPi(String cameraName,
		int hsize, int vsize, double fieldOfViewDenominator)
	{
		double fieldOfView = Math.PI / fieldOfViewDenominator;

		Camera c = new Camera(hsize, vsize, fieldOfView);
		data.put(cameraName, c);
	}

	@When(wordPattern + " ← ray_for_pixel\\(" + wordPattern + ", " + intPattern
		+ ", " + intPattern + "\\)")
	public void rRay_for_pixelC(String rayName, String cameraName, int x, int y)
	{
		Camera camera = data.getCamera(cameraName);
		PixelCoordinate coord = new PixelCoordinate(x, y);
		Ray ray = camera.rayForPixel(coord);
		data.put(rayName, ray);
	}

	@When(wordPattern + "\\.transform ← rotation_y\\(π\\/" + doublePattern
		+ "\\) \\* translation\\(" + threeDoublesPattern + "\\)")
	public void cTransformRotation_yPiTranslation(String cameraName,
		double rotY_divisor, double x, double y, double z)
	{
		Camera camera = data.getCamera(cameraName);
		Matrix rotY = Matrix.newRotationY(Math.PI / rotY_divisor);
		Matrix translation = Matrix.newTranslation(x, y, z);

		Matrix transform = rotY.multiply(translation);
		camera.setTransform(transform);
	}

	@Given(wordPattern + ".transform ← view_transform\\(" + wordPattern + ", "
		+ wordPattern + ", " + wordPattern + "\\)")
	public void cTransformView_transformFromToUp(String cameraName,
		String fromPointName, String toPointName, String upVectorName)
	{
		Camera camera = data.getCamera(cameraName);
		Point fromPoint = data.getPoint(fromPointName);
		Point toPoint = data.getPoint(toPointName);
		Vector upVector = data.getVector(upVectorName);
		Matrix viewTransform = Matrix.newView(fromPoint, toPoint, upVector);
		camera.setTransform(viewTransform);
	}

	@When(wordPattern + " ← render\\(" + wordPattern + ", " + wordPattern
		+ "\\)")
	public void imageRenderCW(String imageName, String cameraName,
		String worldName)
	{
		Camera camera = data.getCamera(cameraName);
		World world = data.getWorld(worldName);
		Canvas canvas = new Canvas(camera.getHsize(), camera.getVsize());
		camera.render(world, canvas, false,
			CanvasTraversalType.AcrossDown.getTraversal(canvas));
		data.put(imageName, canvas);
	}

}
