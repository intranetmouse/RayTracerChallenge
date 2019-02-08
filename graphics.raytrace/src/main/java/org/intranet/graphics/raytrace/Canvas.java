package org.intranet.graphics.raytrace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Canvas
{
	private int width;
	public int getWidth() { return width; }
	public void setWidth(int value) { width = value; }

	private int height;
	public int getHeight() { return height; }
	public void setHeight(int value) { height = value; }

	private Color[][] pixels;

	public Canvas(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new Color[height][];
		for (int h = 0; h < height; h++)
		{
			pixels[h] = new Color[width];
			for (int w = 0; w < width; w++)
				pixels[h][w] = new Color(0, 0, 0);
		}
	}

	public Color getPixelColor(int w, int h)
	{
		return pixels[h][w];
	}

	public void writePixel(int w, int h, Color color)
	{
		getPixelColor(w, h).copy(color);
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

	private int scaleColor(double c)
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
				pixels[h][w].copy(c);
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
}
