package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.List;

import javax.swing.JTabbedPane;

public final class TabbedProjectorUi
	extends JTabbedPane
{
	private static final long serialVersionUID = 1L;

	public TabbedProjectorUi(
		List<ProjectorGroup> projectorGroups)
	{
		for (ProjectorGroup group : projectorGroups)
			addTab(group.getName(),
				new ToolbarCanvasProjectorUi(group.getProjectors()));
	}
}