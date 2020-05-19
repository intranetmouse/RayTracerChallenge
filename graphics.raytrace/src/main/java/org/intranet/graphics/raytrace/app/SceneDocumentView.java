package org.intranet.graphics.raytrace.app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.RayTraceStatistics.StatisticsListener;
import org.intranet.graphics.raytrace.ui.swing.canvas.CanvasComponent;
import org.intranet.graphics.raytrace.ui.swing.repaintMode.RepaintModeCombo;
import org.intranet.graphics.raytrace.ui.swing.resolution.CanvasResolutionCombo;
import org.intranet.graphics.raytrace.ui.swing.resolution.Resolution;
import org.intranet.graphics.raytrace.ui.swing.traversalType.TraversalTypeSelection;

public final class SceneDocumentView
	extends DocumentView<SceneDocument>
{
	private static final long serialVersionUID = 1L;
	private SceneDocument doc;

	private final Canvas canvas = new Canvas(640, 480);
	public Canvas getCanvas() { return canvas; }

	private final SceneTree sceneTree;

	private RenderSettings renderSettings = new RenderSettings(new RenderSettingsListener() {
		@Override
		public void resolutionChanged(Resolution e)
		{
			canvas.resize(e.getWidth(), e.getHeight());
		}
	});
	public RenderSettings getRenderSettings() { return renderSettings; }

	private final JButton renderButton;
	private final CanvasComponent canvasComp = new CanvasComponent();
	private final TraversalTypeSelection tts;

	private List<JComponent> toolbarItems = new ArrayList<>();

	private final CanvasResolutionCombo canvasResolutionCombo;

	private final RayTraceStatistics stats = new RayTraceStatistics();

	public static final class StatisticsView
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

		@Override
		public void statisticsUpdated(RayTraceStatistics stats)
		{
			Instant stopTime = stats.getStopTime();
			Instant startTime = stats.getStartTime();
			String status = stopTime != null ? "Done" :
				startTime == null ? "Not started" :
				"In Progress";
			statusLabel.setText(status);

			String timeUsedStr = "";
			String progressPctStr = "";
			String timeRemainingStr = "";
			String ppsStr = "";
			if (startTime != null)
			{
				Instant endUsedTime = stopTime != null ? stopTime : Instant.now();
				Duration timeUsed = Duration.between(startTime, endUsedTime);
				timeUsedStr = formatDuration(timeUsed);

				int numPixelsCompleted = stats.getNumPixelsCompleted();
				int numTotalPixelsToRender = stats.getNumTotalPixelsToRender();

				long millisUsed = timeUsed.getSeconds() * MILLIS_PER_SECOND + timeUsed.getNano() / 1000000;
				double pixelsPerMs = 1.0 * numPixelsCompleted / millisUsed;
				ppsStr = String.format("%.3f", pixelsPerMs);

				boolean inProgress = stopTime == null;
				if (inProgress)
				{
					progressPctStr = String.format("%.2f%%",
						100.0 * numPixelsCompleted / numTotalPixelsToRender);

					int pixelsRemaining = numTotalPixelsToRender - numPixelsCompleted;
					long msRemaining = (long)(pixelsRemaining / pixelsPerMs);
					timeRemainingStr = formatDuration(Duration.ofMillis(msRemaining));
				}
			}
			pctCompleteLabel.setText(progressPctStr);
			timeUsedTxt.setText(timeUsedStr);
			timeRemainingTxt.setText(timeRemainingStr);
			speedTxt.setText(ppsStr);

			revalidate();
			repaint();
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

	public SceneDocumentView()
	{
		super();
		setLayout(new BorderLayout());


		JPanel dataArea = new JPanel(new BorderLayout());
		dataArea.add(new StatisticsView(stats), BorderLayout.NORTH);

		JScrollPane canvasScroll = new JScrollPane(canvasComp);
		dataArea.add(canvasScroll);

		sceneTree = new SceneTree();

		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sceneTree,
			dataArea);
		add(sp, BorderLayout.CENTER);

		resizeDocCanvas();

		// Toolbar Items

		canvasResolutionCombo = new CanvasResolutionCombo(
			renderSettings.getResolution(),
			renderSettings::setResolution);
		canvasResolutionCombo.addActionListener(e -> {
			resizeDocCanvas();
		});

		renderButton = new JButton("Render");
		renderButton.addActionListener(e -> {
			canvasResolutionCombo.setEnabled(false);
			renderButton.setEnabled(false);
			doc.render(renderSettings, canvas, stats);

			// FIXME: Move to listener for when doc render is done
			canvasResolutionCombo.setEnabled(true);
			renderButton.setEnabled(true);
		});
		renderButton.setEnabled(false);

		RepaintModeCombo repaintCombo = new RepaintModeCombo(
			canvasComp.getRepaintMode(),
			mode -> canvasComp.setRepaintMode(mode));

		JCheckBox parallelCb = new JCheckBox("Parallel", renderSettings.isParallel());
		parallelCb.setOpaque(false);
		parallelCb.addActionListener(ae -> renderSettings.setParallel(parallelCb.isSelected()));

		tts = new TraversalTypeSelection(
			renderSettings.getTraversalType(),
			tt -> { renderSettings.setTraversalType(tt); });
		canvasComp.setCanvas(canvas);

		toolbarItems.add(canvasResolutionCombo);
		toolbarItems.add(tts);
		toolbarItems.add(parallelCb);
		toolbarItems.add(repaintCombo);
		toolbarItems.add(renderButton);

		setDocument(null);
	}

	private void resizeDocCanvas()
	{
		Resolution res = renderSettings.getResolution();
		canvas.resize(res.getWidth(), res.getHeight());
	}

	@Override
	public void setDocument(SceneDocument doc)
	{
		this.doc = doc;
		sceneTree.setWorld(doc == null ? null : doc.getWorld());
		renderButton.setEnabled(doc != null);
		canvas.clear();
	}

	@Override
	public List<JComponent> getToolbarItems() { return toolbarItems; }
}