package org.intranet.graphics.raytrace.app;

import java.awt.FlowLayout;
import java.time.Duration;
import java.time.Instant;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.RayTraceStatistics.StatisticsListener;

public final class StatisticsView
	extends JPanel
	implements StatisticsListener
{
	private static final long MILLIS_PER_SECOND = 1000;
	private static final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
	private static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;

	private static final long serialVersionUID = 1L;

	private final JTextField statusLabel = new JTextField(15);
	private final JTextField pctCompleteLabel = new JTextField(4);
	private final JTextField timeUsedTxt = new JTextField(6);
	private final JTextField timeRemainingTxt = new JTextField(6);
	private final JTextField speedTxt = new JTextField(5);

	public StatisticsView(RayTraceStatistics stats)
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));

		statusLabel.setEditable(false);
		pctCompleteLabel.setEditable(false);
		timeUsedTxt.setEditable(false);
		timeRemainingTxt.setEditable(false);
		speedTxt.setEditable(false);

		add(new JLabel("Status:"));
		add(statusLabel);
		add(new JLabel("Speed pixels/ms"));
		add(speedTxt);
		add(new JLabel("Complete:"));
		add(pctCompleteLabel);
		add(new JLabel("Time used:"));
		add(timeUsedTxt);
		add(new JLabel("Time remaining:"));
		add(timeRemainingTxt);

		stats.addStatsListener(this);
	}

	private Instant nextUpdate;
	@Override
	public void statisticsUpdated(RayTraceStatistics stats)
	{
		Instant stopTime = stats.getStopTime();
		Instant startTime = stats.getStartTime();

		boolean inProgress = stopTime == null;
		boolean done = stopTime != null;
		boolean started = startTime != null;

		String status = done ? "Done" :
			!started ? "Not started" :
			"In Progress";
		if (!status.equals(statusLabel.getText()))
			statusLabel.setText(status);

		Instant now = Instant.now();

		String timeUsedStr = "";
		String progressPctStr = "";
		String timeRemainingStr = "";
		String ppsStr = "";
		if (started)
		{
			Instant endUsedTime = inProgress ? now : stopTime;
			Duration timeUsed = Duration.between(startTime, endUsedTime);
			timeUsedStr = formatDuration(timeUsed);

			int numPixelsCompleted = stats.getNumPixelsCompleted();
			int numTotalPixelsToRender = stats.getNumTotalPixelsToRender();

			long millisUsed = timeUsed.getSeconds() * MILLIS_PER_SECOND + timeUsed.getNano() / 1000000;
			double pixelsPerMs = 1.0 * numPixelsCompleted / millisUsed;
			ppsStr = String.format("%.3f", pixelsPerMs);

			if (inProgress)
			{
				progressPctStr = String.format("%.2f%%",
					100.0 * numPixelsCompleted / numTotalPixelsToRender);

				int pixelsRemaining = numTotalPixelsToRender - numPixelsCompleted;
				long msRemaining = (long)(pixelsRemaining / pixelsPerMs);
				timeRemainingStr = formatDuration(Duration.ofMillis(msRemaining));
			}
		}
		if (done || nextUpdate == null || nextUpdate.isBefore(now))
		{
			pctCompleteLabel.setText(progressPctStr);
			timeUsedTxt.setText(timeUsedStr);
			timeRemainingTxt.setText(timeRemainingStr);
			speedTxt.setText(ppsStr);

			revalidate();
			repaint();

			nextUpdate = now.plusSeconds(1);
		}
	}

	private String formatDuration(Duration timeUsed)
	{
		long millisUsed = timeUsed.toMillis();
		return String.format("%02d:%02d:%02d.%03d",
			millisUsed / MILLIS_PER_HOUR,
			millisUsed / MILLIS_PER_MINUTE % 60,
			millisUsed / MILLIS_PER_SECOND % 60,
			millisUsed % 1000);
	}
}