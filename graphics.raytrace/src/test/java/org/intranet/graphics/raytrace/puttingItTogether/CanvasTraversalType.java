package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.Spliterators.AbstractSpliterator;

import org.intranet.graphics.raytrace.AcrossDownTraversal;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.ScatteredTraversal;

public enum CanvasTraversalType
{
	AcrossDown {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new AcrossDownTraversal(canvas.getWidth(), canvas.getHeight());
		}

	},
	QuadrantsFromEnd {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new ScatteredTraversal(canvas.getWidth(), canvas.getHeight(), null);
		}
	},
	QuandrantsRandom {
		@Override
		public AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas)
		{
			return new ScatteredTraversal(canvas.getWidth(), canvas.getHeight(), 5L);
		}
	};
	public abstract AbstractSpliterator<PixelCoordinate> getTraversal(Canvas canvas);
}