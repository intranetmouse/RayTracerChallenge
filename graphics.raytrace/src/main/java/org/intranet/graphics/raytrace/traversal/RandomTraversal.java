package org.intranet.graphics.raytrace.traversal;

import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.intranet.graphics.raytrace.PixelCoordinate;

public final class RandomTraversal
	extends AbstractSpliterator<PixelCoordinate>
{
	private boolean done;

	private List<PixelCoordinate> allPoints;
	int index;

	public RandomTraversal(int width, int height, Long seed)
	{
		super(width * height, Spliterator.NONNULL);
		allPoints = IntStream.range(0, height)
			.mapToObj((int y) -> IntStream.range(0, width).mapToObj((int x) -> {
				return new PixelCoordinate(x, y);
			})).flatMap(Function.identity()).collect(Collectors.toList());
		Collections.shuffle(allPoints);

		if (width == 0 || height == 0)
			done = true;
	}

	@Override
	public boolean tryAdvance(Consumer<? super PixelCoordinate> action)
	{
		if (done)
			return false;

		action.accept(allPoints.get(index));

		allPoints.set(index++, null);

		if (index >= allPoints.size())
			done = true;
		return true;
	}
}