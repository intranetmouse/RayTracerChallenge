package org.intranet.graphics.raytrace.traversal;

import java.util.Spliterators.AbstractSpliterator;

import javax.swing.ImageIcon;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.PixelCoordinate;

public enum CanvasTraversalType
{
	AcrossDown("icon_down_across") {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new AcrossDownTraversal(canvas.getWidth(), canvas.getHeight());
		}

	},
	QuadrantsFromEnd("icon_quadrants") {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new ScatteredTraversal(canvas.getWidth(), canvas.getHeight(), null);
		}
	},
	QuandrantsRandom("icon_scattered") {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new ScatteredTraversal(canvas.getWidth(), canvas.getHeight(), 5L);
		}
	};
	public abstract AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas);

	private final ImageIcon icon;
	public ImageIcon getIcon() { return icon; }

	CanvasTraversalType(String iconName)
	{
		icon = new ImageIcon(getClass().getResource(iconName + ".png"));
	}
}