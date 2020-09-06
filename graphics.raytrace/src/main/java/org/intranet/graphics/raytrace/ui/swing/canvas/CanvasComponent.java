package org.intranet.graphics.raytrace.ui.swing.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.Canvas.CanvasListener;
import org.intranet.graphics.raytrace.primitive.Color;

public final class CanvasComponent
	extends JComponent
{
	private static final long serialVersionUID = 1L;
	private Canvas canvas;

	private RepaintMode repaintMode = RepaintMode.UPDATE_PERIODICALLY;
	public RepaintMode getRepaintMode() { return repaintMode; }
	public void setRepaintMode(RepaintMode value) { repaintMode = value; }

	public CanvasComponent() { this(null); }
	public CanvasComponent(Canvas c)
	{
		setCanvas(c);
	}

	private final CanvasListener listener = new CanvasListener() {
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
	};

	public void setCanvas(Canvas c)
	{
		if (canvas != null)
			canvas.removeCanvasListener(listener);

		canvas = c;
		if (canvas != null)
			canvas.addCanvasListener(listener);
		setSizeFromCanvas();
	}

	private void setSizeFromCanvas()
	{
		Dimension canvasSize = canvas == null ? new Dimension(640, 480) :
			new Dimension(canvas.getWidth(), canvas.getHeight());
		setMinimumSize(canvasSize);
		setPreferredSize(canvasSize);
		revalidate();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(java.awt.Color.GRAY);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		if (canvas == null)
			return;

		g.setColor(java.awt.Color.BLACK);
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