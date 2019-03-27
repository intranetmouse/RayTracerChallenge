package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Color;

public final class CanvasComponent
	extends JComponent
{
	private static final long serialVersionUID = 1L;
	private final Canvas canvas;

	public CanvasComponent(Canvas c)
	{
		Dimension canvasSize = new Dimension(c.getWidth(), c.getHeight());
		setMinimumSize(canvasSize);
		setPreferredSize(canvasSize);
		this.canvas = c;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		for (int col = 0; col < canvas.getWidth(); col++)
		{
			for (int row = 0; row < canvas.getHeight(); row++)
			{
				Color color = canvas.getPixelColor(col, row);
				try
				{
					java.awt.Color awtColor = rayColorToAwtColor(color);
					g.setColor(awtColor);
					g.drawLine(col, row, col, row);
				}
				catch (Exception e)
				{
					System.err.println("Bad color: " + color);
//					e.printStackTrace();
				}
			}
		}
	}

	private java.awt.Color rayColorToAwtColor(Color color)
	{
		java.awt.Color awtColor = new java.awt.Color(
			(int)(color.getRed() * 255.99),
			(int)(color.getGreen() * 255.99),
			(int)(color.getBlue() * 255.99));
		return awtColor;
	}

	// NOTE: Clipping is done in the canvas itself.
	private java.awt.Color rayColorToAwtColorClipped(Color color)
	{
		java.awt.Color awtColor = new java.awt.Color(
			scaleClip(color.getRed()),
			scaleClip(color.getGreen()),
			scaleClip(color.getBlue()));
		return awtColor;
	}
	private int scaleClip(double color)
	{
		double scaled = color * 255.9999;
		double clipped = Math.max(0.0, scaled);
		clipped = Math.min(255, clipped);
		return (int)clipped;
	}
}