package org.intranet.graphics.raytrace.traversal;

import java.util.Spliterators.AbstractSpliterator;

import javax.swing.ImageIcon;

import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.surface.map.Canvas;

public enum CanvasTraversalType
{
	AcrossDown("icon_down_across") {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new AcrossDownTraversal(canvas.getWidth(), canvas.getHeight());
		}
	},
	Random("icon_random") {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new RandomTraversal(canvas.getWidth(), canvas.getHeight(), null);
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