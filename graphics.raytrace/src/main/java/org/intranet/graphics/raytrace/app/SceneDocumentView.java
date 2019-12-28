package org.intranet.graphics.raytrace.app;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.intranet.graphics.raytrace.Canvas;
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

	public SceneDocumentView()
	{
		super();
		setLayout(new BorderLayout());

		JScrollPane canvasScroll = new JScrollPane(canvasComp);
		add(canvasScroll, BorderLayout.CENTER);

		resizeDocCanvas();

		canvasResolutionCombo = new CanvasResolutionCombo(
			renderSettings.getResolution(),
			res -> renderSettings.setResolution(res));
		canvasResolutionCombo.addActionListener(e -> {
			resizeDocCanvas();
		});

		renderButton = new JButton("Render");
		renderButton.addActionListener(e -> {
			canvasResolutionCombo.setEnabled(false);
			renderButton.setEnabled(false);
			doc.render(renderSettings, canvas);

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
		renderButton.setEnabled(doc != null);
		canvas.clear();
	}

	@Override
	public List<JComponent> getToolbarItems() { return toolbarItems; }
}