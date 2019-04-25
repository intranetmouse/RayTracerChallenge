package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

import org.intranet.graphics.raytrace.Canvas;

public class TreeProjectorUi
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	Box boxbar = Box.createVerticalBox();

	public TreeProjectorUi(List<ProjectorGroup> projectorGroups)
	{
		Resolution res = Resolution.HDTV_720p;
		Canvas canvas = new Canvas(res.getWidth(), res.getHeight());
		CanvasComponent canvasComponent = new CanvasComponent(canvas);
		setLayout(new BorderLayout());

		add(boxbar, BorderLayout.CENTER);

		ProjectorToolbar renderBar = new ProjectorToolbar(canvas);
		boxbar.add(renderBar);

		boxbar.add(canvasComponent);

		ProjectorGroupTree tree = new ProjectorGroupTree(projectorGroups);
		add(tree, BorderLayout.WEST);

		tree.addProjectorGroupSelectionListener(new ProjectorGroupSelectionListener() {
			@Override
			public void projectorGroupSelected(ProjectorGroup group)
			{
				renderBar.setProjectors(group.getProjectors());
			}
		});
	}
}
