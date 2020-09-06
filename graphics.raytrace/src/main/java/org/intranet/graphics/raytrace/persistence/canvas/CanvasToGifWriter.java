package org.intranet.graphics.raytrace.persistence.canvas;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Canvas.CanvasListener;
import org.intranet.graphics.raytrace.persistence.canvas.gif.AnimatedGifEncoder;
import org.intranet.graphics.raytrace.primitive.Color;

public final class CanvasToGifWriter
	implements CanvasListener
{
	static final int NUM_PIXELS_WRITE = 10;
	private final Canvas canvas;
	private int pixelNumber = 0;
	private AnimatedGifEncoder age;
	private BufferedImage img;

	private boolean enabled = false;
	public void disable() { enabled = false; }

	private File file = null;
	public void setFile(File value)
	{
		file = value;
		enabled = true;
	}

	public CanvasToGifWriter(Canvas canvas)
	{
		this.canvas = canvas;
	}

	@Override
	public void resized(int x, int y)
	{
	}

	@Override
	public void initialized()
	{
		pixelNumber = 0;
		if (enabled)
		{
			age = new AnimatedGifEncoder();
			age.setSize(canvas.getWidth(), canvas.getHeight());
			age.setDelay(0);
			age.start(file.getPath());
			img = new BufferedImage(canvas.getWidth(),
				canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		}
	}

	@Override
	public void pixelUpdated(int x, int y, Color color)
	{
		if (enabled)
		{
			Graphics2D g2d = img.createGraphics();
			g2d.setColor(Color.rayColorToAwtColor(color));
			g2d.drawLine(x, y, x, y);
			g2d.dispose();
			pixelNumber++;
			if (pixelNumber % NUM_PIXELS_WRITE == 0)
				age.addFrame(img);
		}
	}

	@Override
	public void completed()
	{
		if (enabled)
		{
			if (pixelNumber % NUM_PIXELS_WRITE != 0)
				age.addFrame(img);
			age.finish();
		}
	}
}