package org.intranet.graphics.raytrace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.surface.Color;

public class Canvas
{
	private int width;
	public int getWidth() { return width; }

	private int height;
	public int getHeight() { return height; }

	public void resize(int width, int height)
	{
		this.width = width;
		this.height = height;
		initSize(width, height);
		fireResized();
	}

	private Color[][] pixels;

	public Canvas(int width, int height)
	{
		resize(width, height);
	}

	private void initSize(int width, int height)
	{
		pixels = new Color[height][];
		for (int row = 0; row < height; row++)
		{
			pixels[row] = new Color[width];
			for (int col = 0; col < width; col++)
				pixels[row][col] = new Color(0, 0, 0);
		}
	}

	public Color getPixelColor(int col, int row)
	{
		return pixels[row][col];
	}

	public void writePixel(int col, int row, Color color)
	{
		Color clipped = color.clipped();
		pixels[row][col] = clipped;
		firePixelUpdated(col, row, clipped);
	}

	public interface CanvasListener
	{
		void pixelUpdated(int x, int y, Color color);
		void allPixelsUpdated();
		void resized(int x, int y);
	}

	private final List<CanvasListener> canvasListeners = new ArrayList<>();
	public void addCanvasListener(CanvasListener l)
	{ canvasListeners.add(l); }
	public void removeCanvasListener(CanvasListener l)
	{ canvasListeners.remove(l); }
	private void firePixelUpdated(int x, int y, Color color)
	{
		for (CanvasListener l : canvasListeners)
			l.pixelUpdated(x, y, color);
	}
	private void fireAllPixelsUpdated()
	{
		for (CanvasListener l : canvasListeners)
			l.allPixelsUpdated();
	}
	private void fireResized()
	{
		for (CanvasListener l : canvasListeners)
			l.resized(width, height);
	}

	public List<String> toPpm()
	{
		List<String> lines = new ArrayList<>(height + 3);
		lines.add("P3");
		lines.add(String.format("%d %d", width, height));
		lines.add("255");
		for (int h = 0; h < height; h++)
		{
			StringBuilder sb = new StringBuilder();
			for (int w = 0; w < width; w++)
			{
				Color c = getPixelColor(w, h);
				appendColor(lines, sb, c.getRed());
				appendColor(lines, sb, c.getGreen());
				appendColor(lines, sb, c.getBlue());
			}
			if (sb.length() > 0)
				lines.add(sb.toString());
		}
		return lines;
	}

	private void appendColor(List<String> lines, StringBuilder sb, double color)
	{
		String colorStr = Integer.toString(scaleColor(color));
		int len = sb.length() + colorStr.length() + 1;
		if (len > 70)
		{
			lines.add(sb.toString());
			sb.setLength(0);
			sb.append(colorStr);
			return;
		}

		if (sb.length() > 0)
			sb.append(" ");
		sb.append(colorStr);
	}

	private static int scaleColor(double c)
	{
		int scaledColor = (int)(c * 255 + 0.5);
		int scaledColorMax = Math.min(255, scaledColor);
		int scaledColorMaxMin = Math.max(0, scaledColorMax);
		return scaledColorMaxMin;
	}

	public void writeAllPixels(Color c)
	{
		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++)
				pixels[h][w] = c;
		fireAllPixelsUpdated();
	}

	public void writeFile(String fname)
		throws IOException
	{
		File f1 = new File(fname);
		try (FileWriter fw = new FileWriter(f1))
		{
			for (String line : toPpm())
			{
				fw.write(line + "\n");
			}
		}
	}

	public void clear()
	{
		writeAllPixels(new Color(0, 0, 0));
	}

	public void setDone(boolean b)
	{
		fireAllPixelsUpdated();
	}
}
