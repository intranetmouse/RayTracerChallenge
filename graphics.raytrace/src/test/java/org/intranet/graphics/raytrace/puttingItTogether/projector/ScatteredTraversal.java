package org.intranet.graphics.raytrace.puttingItTogether.projector;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

import org.intranet.graphics.raytrace.PixelCoordinate;

public final class ScatteredTraversal
	extends AbstractSpliterator<PixelCoordinate>
{
	private boolean done;

	private Random r;

	private List<Rectangle> currentLevel = new ArrayList<>();
	private Rectangle removeCurrent()
	{
		int currentSize = currentLevel.size();
		int idx = currentSize - 1;
		return currentLevel.remove(idx);
	}

	private List<Rectangle> nextLevel = new ArrayList<>();

	private boolean hasArea(Rectangle rect)
	{
		return rect.width > 0 && rect.height > 0;
	}

	private void swapNextLevelToCurrent()
	{
		List<Rectangle> temp = nextLevel;
		nextLevel = currentLevel;
		currentLevel = temp;
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

		PixelCoordinate chosenPoint = choosePointInRectangle(curr);
		action.accept(chosenPoint);

		List<Rectangle> newRectangles =
			subdivideRectangleAroundPoint(curr, chosenPoint);
		nextLevel.addAll(newRectangles);

//System.out.println(" centerX="+centerX+",centerY="+centerY+", nextLevel="+nextLevel);

		if (currentLevel.size() == 0)
		{
			swapNextLevelToCurrent();
			if (r != null)
				Collections.shuffle(currentLevel, r);
		}
		if (currentLevel.size() == 0)
			done = true;

		return true;
	}

	private List<Rectangle> subdivideRectangleAroundPoint(Rectangle curr,
		PixelCoordinate chosenPoint)
	{
		List<Rectangle> newRectangles = new ArrayList<>();
		Rectangle upperLeft = new Rectangle(curr.x, curr.y,
			chosenPoint.getX() - curr.x + 1, chosenPoint.getY() - curr.y);
		if (hasArea(upperLeft)) newRectangles.add(upperLeft);
		Rectangle upperRight = new Rectangle(chosenPoint.getX() + 1, curr.y,
			curr.x + curr.width - chosenPoint.getX() - 1, chosenPoint.getY() - curr.y + 1);
		if (hasArea(upperRight)) newRectangles.add(upperRight);
		Rectangle lowerLeft = new Rectangle(curr.x, chosenPoint.getY(),
			chosenPoint.getX() - curr.x, curr.y + curr.height - chosenPoint.getY());
		if (hasArea(lowerLeft)) newRectangles.add(lowerLeft);
		Rectangle lowerRight = new Rectangle(chosenPoint.getX(), chosenPoint.getY() + 1,
			curr.x + curr.width - chosenPoint.getX(), curr.y + curr.height - chosenPoint.getY() - 1);
		if (hasArea(lowerRight)) newRectangles.add(lowerRight);
		return newRectangles;
	}

	private PixelCoordinate choosePointInRectangle(Rectangle curr)
	{
		int centerX = curr.x + curr.width / 2;
		int centerY = curr.y + curr.height / 2;
		PixelCoordinate chosenPoint = new PixelCoordinate(centerX, centerY);
		return chosenPoint;
	}
}