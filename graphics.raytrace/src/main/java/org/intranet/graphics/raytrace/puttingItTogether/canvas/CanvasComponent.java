package org.intranet.graphics.raytrace.puttingItTogether.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Canvas.CanvasListener;
import org.intranet.graphics.raytrace.surface.Color;

public final class CanvasComponent
	extends JComponent
{
	private static final long serialVersionUID = 1L;
	private final Canvas canvas;

	private RepaintMode repaintMode = RepaintMode.UPDATE_PER_PIXEL;
	public RepaintMode getRepaintMode() { return repaintMode; }
	public void setRepaintMode(RepaintMode value) { repaintMode = value; }

	public CanvasComponent(Canvas c)
	{
		c.addCanvasListener(
			new CanvasListener() {
				@Override
				public void resized(int x, int y)
				{
					setSizeFromCanvas();
				}

				@Override
				public void initialized()
				{
					repaint();
				}

				@Override
				public void pixelUpdated(int x, int y, Color color)
				{
					repaintMode.pixelUpdated(CanvasComponent.this, x, y);
				}

				@Override
				public void completed()
				{
					repaint();
				}
			}
		);
		this.canvas = c;
		setSizeFromCanvas();
	}

	private void setSizeFromCanvas()
	{
		Dimension canvasSize = new Dimension(canvas.getWidth(), canvas.getHeight());
		setMinimumSize(canvasSize);
		setPreferredSize(canvasSize);
		revalidate();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		Rectangle bounds = g.getClipBounds();
		int minCol = Math.max(bounds.x, 0);
		int maxCol = Math.min(bounds.x + bounds.width, canvas.getWidth());
		int minRow = Math.max(bounds.y, 0);
		int maxRow = Math.min(bounds.y + bounds.height, canvas.getHeight());

		for (int col = minCol; col < maxCol; col++)
		{
			for (int row = minRow; row < maxRow; row++)
			{
				if (!g.hitClip(col, row, 1, 1))
					continue;
				Color color = canvas.getPixelColor(col, row);
				try
				{
					java.awt.Color awtColor = Color.rayColorToAwtColor(color);
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
}