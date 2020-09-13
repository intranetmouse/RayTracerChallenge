package org.intranet.graphics.raytrace.app;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.intranet.app.DocumentView;
import org.intranet.graphics.raytrace.PixelCoordinate;
import org.intranet.graphics.raytrace.RayTraceStatistics;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.surface.map.Canvas;
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

	public SceneDocumentView()
	{
		super();
		setLayout(new BorderLayout());


		JPanel dataArea = new JPanel(new BorderLayout());
		dataArea.add(new StatisticsView(stats), BorderLayout.NORTH);

		JScrollPane canvasScroll = new JScrollPane(canvasComp);
		dataArea.add(canvasScroll);

		canvasComp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				maybeShowPopup(e);
			}
			@Override
			public void mousePressed(MouseEvent e)
			{
				maybeShowPopup(e);
			}
			@Override
			public void mouseReleased(MouseEvent e)
			{
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e)
			{
				if (!e.isPopupTrigger())
					return;
				int clickX = e.getX();
				int clickY = e.getY();
				if (clickX > canvasComp.getWidth() || clickY > canvasComp.getHeight())
					return;
				PixelCoordinate clickPoint = new PixelCoordinate(clickX, clickY);

				// new menu with action = render point
				JPopupMenu menu = new JPopupMenu();

				JMenuItem renderPixelMenuItem = new JMenuItem("Render Pixel");
				renderPixelMenuItem.addActionListener(evt -> {
					Tracer.renderPixel(doc.getWorld().getCamera(),
						doc.getWorld(), canvas, clickPoint);
				});
				menu.add(renderPixelMenuItem);
				menu.show(canvasComp, clickX, clickY);
				e.consume();
			}
		});

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