package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

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

		add(new ProjectorToolbar(projectileCanvas, projectors),
			BorderLayout.NORTH);

		CanvasComponent canvasComp = new CanvasComponent(projectileCanvas);
		add(canvasComp, BorderLayout.CENTER);
	}
}