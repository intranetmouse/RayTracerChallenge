package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.intranet.graphics.raytrace.Canvas;

public final class ToolbarCanvasProjectorUi
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final Canvas projectileCanvas = new Canvas(200, 200);

	public ToolbarCanvasProjectorUi(List<Projector> projectors)
	{
		this(projectors.toArray(new Projector[projectors.size()]));
	}

	public ToolbarCanvasProjectorUi(Projector ... projectors)
	{
		super(new BorderLayout());

		JPanel toolBarPanel = new JPanel(new BorderLayout());
		toolBarPanel.setOpaque(false);
		add(toolBarPanel, BorderLayout.NORTH);

		JLabel time = new JLabel();
		toolBarPanel.add(time, BorderLayout.EAST);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBarPanel.add(toolBar);
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
					long durationMs = System.currentTimeMillis() - startTime;
					time.setText(String.format("Time: %.3f seconds", durationMs / 1000.0));
					repaint();
				}
			});
		}


		CanvasComponent projectileCanvasComp = new CanvasComponent(projectileCanvas);
		add(projectileCanvasComp, BorderLayout.CENTER);
	}
}