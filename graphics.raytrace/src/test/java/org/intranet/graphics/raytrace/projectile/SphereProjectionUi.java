package org.intranet.graphics.raytrace.projectile;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.intranet.graphics.raytrace.Canvas;

public final class SphereProjectionUi
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final Canvas projectileCanvas = new Canvas(200, 200);

	public SphereProjectionUi(SphereProjector sphereProjector)
	{
		super(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
		for (SphereProjectionType projType : SphereProjectionType.values())
		{
			toolBar.add(new AbstractAction(projType.getName()) {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					projectileCanvas.clear();
					sphereProjector.projectToCanvas(projType, projectileCanvas);
					repaint();
				}
			});
		}

		CanvasComponent projectileCanvasComp = new CanvasComponent(projectileCanvas);
		add(projectileCanvasComp, BorderLayout.CENTER);
	}
}