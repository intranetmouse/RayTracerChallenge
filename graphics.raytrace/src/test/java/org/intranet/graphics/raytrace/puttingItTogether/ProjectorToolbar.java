package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.intranet.graphics.raytrace.Canvas;

public class ProjectorToolbar
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final JToolBar toolBar = new JToolBar();
	private final Canvas canvas;
	private final JLabel time = new JLabel();

	private boolean parallel;
	private Projector[] allProjectors;

	ProjectorToolbar(Canvas canvas, Projector... projectors)
	{
		super(new BorderLayout());
		this.canvas = canvas;
		setOpaque(false);

		JPanel eastPanel = new JPanel();
		eastPanel.setOpaque(false);
		add(eastPanel, BorderLayout.EAST);

		JComboBox<Resolution> resolutionCombo = new JComboBox<>(Resolution.resolutions);
		resolutionCombo.addItemListener(itemEvent -> {
			if (itemEvent.getStateChange() != ItemEvent.SELECTED)
				return;
			Resolution res = (Resolution)itemEvent.getItem();
			if (res != null)
				canvas.resize(res.getWidth(), res.getHeight());
		});
		resolutionCombo.setSelectedItem(Resolution.HDTV_360p);

		JComboBox<CanvasTraversalType> traversalCombo = new JComboBox<>(CanvasTraversalType.values());
		traversalCombo.setSelectedItem(CanvasTraversalType.AcrossDown);
		traversalCombo.addItemListener(itemEvent -> {
			if (itemEvent.getStateChange() != ItemEvent.SELECTED)
				return;
			CanvasTraversalType traversalType = (CanvasTraversalType)itemEvent.getItem();
			if (traversalType == null)
				return;
			for (Projector projector : allProjectors)
			{
				if (!(projector instanceof WorldProjector))
					continue;
				WorldProjector worldProjector = (WorldProjector)projector;
				worldProjector.setTraversalType(traversalType);
			}
		});

		JCheckBox parallelCkb = new JCheckBox("Parallel", parallel);
		parallelCkb.addActionListener(e -> parallel = parallelCkb.isSelected());

		eastPanel.add(time);
		eastPanel.add(parallelCkb);
		eastPanel.add(traversalCombo);
		eastPanel.add(resolutionCombo);

		toolBar.setMinimumSize(new Dimension(0, 32));
		toolBar.setPreferredSize(new Dimension(0, 32));

		toolBar.setFloatable(false);
		add(toolBar);
		setProjectors(projectors);
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