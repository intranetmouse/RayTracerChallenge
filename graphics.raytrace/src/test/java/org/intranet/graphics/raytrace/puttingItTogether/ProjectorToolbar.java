package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
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

	ProjectorToolbar(Canvas canvas, Projector... projectors)
	{
		super(new BorderLayout());
		this.canvas = canvas;
		setOpaque(false);

		add(time, BorderLayout.EAST);

		toolBar.setMinimumSize(new Dimension(0, 32));
		toolBar.setPreferredSize(new Dimension(0, 32));

		toolBar.setFloatable(false);
		add(toolBar);
		setProjectors(projectors);
	}

	public void setProjectors(Projector... projectors)
	{
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
		projector.projectToCanvas(canvas);

		long durationMs = System.currentTimeMillis() -
			startTime;
		time.setText(String.format("Time: %.3f seconds",
			durationMs / 1000.0));
		repaint();
	}
}