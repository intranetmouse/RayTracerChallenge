package org.intranet.graphics.raytrace;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

public final class ScatteredTraversal
	extends AbstractSpliterator<PixelCoordinate>
{
	private boolean done;

	private Random r;

	private List<Rectangle> currentLevel = new ArrayList<>();
	private Rectangle removeCurrent()
	{
		int currentSize = currentLevel.size();
		int idx = r == null ? currentSize - 1 : r.nextInt(currentSize);
		return currentLevel.remove(idx);
	}

	private List<Rectangle> nextLevel = new ArrayList<>();
	private void addToNextLevel(Rectangle rect)
	{
		if (rect.width > 0 && rect.height > 0)
			nextLevel.add(rect);
	}

	public ScatteredTraversal(int width, int height, Long seed)
	{
		super(width * height, Spliterator.NONNULL);
		if (seed != null)
			r = new Random(seed);
		Rectangle rect = new Rectangle(0, 0, width, height);
		currentLevel.add(rect);
//System.out.println("Creating scatteredTraversal with rect="+rect);
		if (width == 0 || height == 0)
			done = true;
	}

	@Override
	public boolean tryAdvance(Consumer<? super PixelCoordinate> action)
	{
		if (done)
			return false;

		Rectangle curr = removeCurrent();
//System.out.println(" Processing curr="+curr);

		int centerX = curr.x + curr.width / 2;
		int centerY = curr.y + curr.height / 2;
		action.accept(new PixelCoordinate(centerX, centerY));

		Rectangle upperLeft = new Rectangle(curr.x, curr.y,
			centerX - curr.x + 1, centerY - curr.y);
		addToNextLevel(upperLeft);
		Rectangle upperRight = new Rectangle(centerX + 1, curr.y,
			curr.x + curr.width - centerX - 1, centerY - curr.y + 1);
		addToNextLevel(upperRight);
		Rectangle lowerLeft = new Rectangle(curr.x, centerY,
			centerX - curr.x, curr.y + curr.height - centerY);
		addToNextLevel(lowerLeft);
		Rectangle lowerRight = new Rectangle(centerX, centerY + 1,
			curr.x + curr.width - centerX, curr.y + curr.height - centerY - 1);
		addToNextLevel(lowerRight);

//System.out.println(" centerX="+centerX+",centerY="+centerY+", nextLevel="+nextLevel);

		if (currentLevel.size() == 0)
		{
			List<Rectangle> temp = nextLevel;
			nextLevel = currentLevel;
			currentLevel = temp;
		}
		if (currentLevel.size() == 0)
			done = true;

		return true;
	}
}