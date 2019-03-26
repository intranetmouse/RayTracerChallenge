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
					java.awt.Color awtColor = new java.awt.Color(
						(int)(color.getRed() * 255),
						(int)(color.getGreen() * 255),
						(int)(color.getBlue() * 255));
					g.setColor(awtColor);
					g.drawLine(col, row, col, row);
				}
				catch (Exception e)
				{
					System.err.println("Bad color: " + color);
				}
			}
		}
	}
}