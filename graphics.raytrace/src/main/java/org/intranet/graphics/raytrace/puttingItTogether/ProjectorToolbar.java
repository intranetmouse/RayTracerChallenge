package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.puttingItTogether.projector.CanvasTraversalType;
import org.intranet.graphics.raytrace.puttingItTogether.projector.Projector;
import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public class ProjectorToolbar
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final JToolBar toolBar = new JToolBar();
	private final Canvas canvas;
	private final JLabel time = new JLabel();

	private boolean parallel;
	private Projector[] allProjectors;

	ProjectorToolbar(Canvas canvas, CanvasComponent canvasComponent,
		Projector... projectors)
	{
		super(new BorderLayout());
		this.canvas = canvas;
		setOpaque(false);

		JPanel eastPanel = new JPanel();
		eastPanel.setOpaque(false);
		add(eastPanel, BorderLayout.EAST);

		CanvasResolutionCombo resolutionCombo = new CanvasResolutionCombo(
			Resolution.HDTV_360p,
			res -> canvas.resize(res.getWidth(), res.getHeight()));

		RepaintModeCombo repaintCombo = new RepaintModeCombo(
			canvasComponent.getRepaintMode(),
			mode -> canvasComponent.setRepaintMode(mode));

		ToggleButtons traversalPanel = new TraversalTypeSelection(
			CanvasTraversalType.AcrossDown,
			traversalType -> setCanvasTraversalType(traversalType));

		JCheckBox parallelCkb = new JCheckBox("Parallel", parallel);
		parallelCkb.addActionListener(e -> parallel = parallelCkb.isSelected());

		eastPanel.add(time);
		eastPanel.add(parallelCkb);
		eastPanel.add(traversalPanel);
		eastPanel.add(repaintCombo);
		eastPanel.add(resolutionCombo);

		toolBar.setMinimumSize(new Dimension(0, 32));
		toolBar.setPreferredSize(new Dimension(0, 32));

		toolBar.setFloatable(false);
		add(toolBar);
		setProjectors(projectors);
	}

	private void setCanvasTraversalType(CanvasTraversalType traversalType)
	{
		if (traversalType == null)
			return;
		for (Projector projector : allProjectors)
		{
			if (!(projector instanceof WorldProjector))
				continue;
			WorldProjector worldProjector = (WorldProjector)projector;
			worldProjector.setTraversalType(traversalType);
		}
	}

	public void setProjectors(Projector... projectors)
	{
		allProjectors = projectors;
		toolBar.removeAll();
		for (Projector projector : projectors)
		{
			toolBar.add(new AbstractAction(projector.getName()) {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					canvas.clear();

					Thread thread = new Thread(() -> performRender(projector));
					thread.start();
				}
			});
		}
		toolBar.repaint();
	}

	private void performRender(Projector projector)
	{
		long startTime = System.currentTimeMillis();
		projector.projectToCanvas(canvas, parallel);

		long durationMs = System.currentTimeMillis() -
			startTime;
		time.setText(String.format("Time: %.3f seconds",
			durationMs / 1000.0));
		repaint();
	}
}