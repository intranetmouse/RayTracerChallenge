package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.intranet.graphics.raytrace.puttingItTogether.projectorGroup.ProjectorGroup;
import org.intranet.graphics.raytrace.puttingItTogether.projectorSelector.ProjectorGroupTree;
import org.intranet.graphics.raytrace.puttingItTogether.toolbar.ProjectorToolbar;
import org.intranet.graphics.raytrace.surface.map.Canvas;
import org.intranet.graphics.raytrace.ui.swing.canvas.CanvasComponent;
import org.intranet.graphics.raytrace.ui.swing.resolution.Resolution;

public class TreeProjectorUi
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	JPanel boxbar = new JPanel(new BorderLayout());

	public TreeProjectorUi(List<ProjectorGroup> projectorGroups)
	{
		Resolution res = Resolution.HDTV_360p;
		Canvas canvas = new Canvas(res.getWidth(), res.getHeight());
		CanvasComponent canvasComponent = new CanvasComponent(canvas);
		setLayout(new BorderLayout());

		boxbar.setOpaque(false);
		add(boxbar, BorderLayout.CENTER);

		ProjectorToolbar renderBar = new ProjectorToolbar(canvas, canvasComponent);
		boxbar.add(renderBar, BorderLayout.NORTH);

		JScrollPane jsp = new JScrollPane(canvasComponent);
		jsp.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		boxbar.add(jsp, BorderLayout.CENTER);

		ProjectorGroupTree tree = new ProjectorGroupTree(projectorGroups);
		add(tree, BorderLayout.WEST);

		tree.addProjectorGroupSelectionListener(group ->
			renderBar.setProjectors(group.getProjectors()));
	}
}
