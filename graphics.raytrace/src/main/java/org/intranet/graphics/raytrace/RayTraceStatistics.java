package org.intranet.graphics.raytrace;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RayTraceStatistics
{
	private Instant startTime;
	public Instant getStartTime() { return startTime; }

	private Instant stopTime;
	public Instant getStopTime() { return stopTime; }

	private int numPixelsToRender;
	public int getNumTotalPixelsToRender() { return numPixelsToRender; }

	private int numPixelsInProgress;
	public int getNumPixelsInProgress() { return numPixelsInProgress; }

	private int numPixelsCompleted;
	public int getNumPixelsCompleted() { return numPixelsCompleted; }

	public double calcPercentageComplete(RayTraceStatistics stats)
	{
		int numPixelsCompleted = stats.getNumPixelsCompleted();
		int numPixelsToRender = stats.getNumTotalPixelsToRender();
		double percentage = (100.0 * numPixelsCompleted) / numPixelsToRender;
		return percentage;
	}

	public interface StatisticsListener
	{
		void statisticsUpdated(RayTraceStatistics stats);
	}

	public RayTraceStatistics()
	{
	}

	public synchronized void start(int numPixelsToRender)
	{
		this.numPixelsToRender = numPixelsToRender;
		startTime = Instant.now();
		stopTime = null;
		numPixelsInProgress = 0;
		numPixelsCompleted = 0;
		fireChange();
	}

	public synchronized void startPixel()
	{
		numPixelsInProgress++;
		fireChange();
	}

	public synchronized void finishPixel()
	{
		numPixelsInProgress--;
		numPixelsCompleted++;
		fireChange();
	}

	public synchronized void stop()
	{
		if (numPixelsInProgress != 0 || numPixelsCompleted != numPixelsToRender)
			throw new IllegalStateException();
		stopTime = Instant.now();
		fireChange();
	}

	List<StatisticsListener> listeners = new ArrayList<>();
	public void addStatsListener(StatisticsListener statisticsView)
	{
		listeners.add(statisticsView);
	}

	private void fireChange()
	{
		for (StatisticsListener l : listeners)
			l.statisticsUpdated(this);
	}
}