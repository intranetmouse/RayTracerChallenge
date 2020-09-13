package org.intranet.graphics.raytrace.steps;

import java.util.Spliterators.AbstractSpliterator;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.CameraViewPort;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.map.Canvas;
import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CameraSteps
	extends StepsParent
{

	public CameraSteps(RaytraceData data)
	{
		super(data);
	}

	@Given("{identifier}.transform ← view_transform\\({identifier}, {identifier}, {identifier})")
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

	@When("{identifier} ← camera\\({identifier}, {identifier}, {identifier})")
	public void cCameraHsizeVsizeField_of_view(String cameraName,
		String hsizeName, String vsizeName, String fieldOfViewName)
	{
		int hsize = data.getInt(hsizeName);
		int vsize = data.getInt(vsizeName);
		double fieldOfView = data.getDouble(fieldOfViewName);

		Camera c = new Camera(hsize, vsize, fieldOfView);
		data.putCamera(cameraName, c);
	}

	@When("{identifier} ← camera\\({int}, {int}, π\\/{dbl}\\)")
	public void cCameraPi(String cameraName,
		int hsize, int vsize, double fieldOfViewDenominator)
	{
		double fieldOfView = Math.PI / fieldOfViewDenominator;

		Camera c = new Camera(hsize, vsize, fieldOfView);
		data.putCamera(cameraName, c);
	}

	@When("{identifier} ← ray_for_pixel\\({identifier}, {int}, {int})")
	public void rRay_for_pixelC(String rayName, String cameraName, int x, int y)
	{
		Camera camera = data.getCamera(cameraName);
		PixelCoordinate coord = new PixelCoordinate(x, y);
		Ray ray = Tracer.rayForPixel(camera, coord);
		data.putRay(rayName, ray);
	}

	@When("{identifier}.transform ← {matrixRotationPiDiv} * {matrix}")
	public void cTransformRotation_yPiTranslation(String cameraName,
		Matrix rotY, Matrix translation)
	{
		Camera camera = data.getCamera(cameraName);

		Matrix transform = rotY.multiply(translation);
		camera.setTransform(transform);
	}

	@When("{identifier} ← {matrixRotationPiDiv} * {matrixRotationPiDiv}")
	public void setVarEqMtxTimesMtx(String matrixName,
		Matrix rotY, Matrix translation)
	{
		Matrix transform = rotY.multiply(translation);
		data.putMatrix(matrixName, transform);
	}

	@When("{identifier} ← render\\({identifier}, {identifier})")
	public void imageRenderCW(String imageName, String cameraName,
		String worldName)
	{
		Camera camera = data.getCamera(cameraName);
		World world = data.getWorld(worldName);
		CameraViewPort viewPort = camera.getViewPort();
		Canvas canvas = new Canvas(viewPort.getHsize(), viewPort.getVsize());
		AbstractSpliterator<PixelCoordinate> traversal = CanvasTraversalType.AcrossDown.getTraversal(canvas);
		RayTraceStatistics stats = new RayTraceStatistics();
		Tracer.render(camera, viewPort, world, canvas, false, traversal, stats);
		data.putCanvas(imageName, canvas);
	}


	@Then("{identifier}.hsize = {int}")
	public void assertHsizeEqualsInt(String objectName, int expectedValue)
	{
		Camera camera = data.getCamera(objectName);
		if (camera == null)
			Assert.fail("Unrecognized object name " + objectName);
		Assert.assertEquals(expectedValue, camera.getViewPort().getHsize());
	}

	@Then("{identifier}.vsize = {int}")
	public void assertVsizeEqualsInt(String objectName, int expectedValue)
	{
		Camera camera = data.getCamera(objectName);
		if (camera == null)
			Assert.fail("Unrecognized object name " + objectName);
		Assert.assertEquals(expectedValue, camera.getViewPort().getVsize());
	}

	@Then("{identifier}.pixel_size = {dbl}")
	public void assertPixelSizeEqualsInt(String objectName, double expectedValue)
	{
		Camera camera = data.getCamera(objectName);
		if (camera == null)
			Assert.fail("Unrecognized object name " + objectName);
		Assert.assertEquals(expectedValue, camera.getViewPort().getPixelSize(),
			Tuple.EPSILON);
	}


	@Then("{identifier}.field_of_view = π\\/{dbl}")
	public void cFieldOfViewPi(String cameraName, double divisor)
	{
		Camera camera = data.getCamera(cameraName);

		double value = Math.PI / divisor;

		Assert.assertEquals(value, camera.getViewPort().getFieldOfView(), Tuple.EPSILON);
	}
}
