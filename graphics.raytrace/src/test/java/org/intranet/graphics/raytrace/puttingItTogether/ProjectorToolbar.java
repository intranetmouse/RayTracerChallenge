package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
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

	ProjectorToolbar(Canvas projectileCanvas, Projector... projectors)
	{
		super(new BorderLayout());
		setOpaque(false);

		JLabel time = new JLabel();
		add(time, BorderLayout.EAST);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar);
		for (Projector projector : projectors)
		{
			toolBar.add(new AbstractAction(projector.getName()) {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					projectileCanvas.clear();
					long startTime = System.currentTimeMillis();
					projector.projectToCanvas(projectileCanvas);
					long durationMs = System.currentTimeMillis() -
						startTime;
					time.setText(String.format("Time: %.3f seconds",
						durationMs / 1000.0));
					repaint();
				}
			});
		}
	}
}