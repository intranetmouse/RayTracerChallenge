package org.intranet.graphics.raytrace.traversal;

import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

import org.intranet.graphics.raytrace.PixelCoordinate;

public final class AcrossDownTraversal
	extends AbstractSpliterator<PixelCoordinate>
{
	private final int width;
	private final int height;

	private int x = 0;
	private int y = 0;
	private boolean done;

	public AcrossDownTraversal(int width, int height)
	{
		super(width * height, Spliterator.NONNULL);
		this.width = width;
		this.height = height;
		if (width == 0 || height == 0)
			done = true;
	}

	@Override
	public boolean tryAdvance(Consumer<? super PixelCoordinate> action)
	{
		if (done)
			return false;
		action.accept(new PixelCoordinate(x, y));
		if (++x >= width)
		{
			if (++y < height)
				x = 0;
			else
				done = true;
		}
		return true;
	}
}