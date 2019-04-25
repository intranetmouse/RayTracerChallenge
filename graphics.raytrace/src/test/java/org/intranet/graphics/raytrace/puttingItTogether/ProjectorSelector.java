package org.intranet.graphics.raytrace.puttingItTogether;

public interface ProjectorSelector
{
	void addProjectorGroupSelectionListener(ProjectorGroupSelectionListener l);
	void removeProjectorGroupSelectionListener(ProjectorGroupSelectionListener l);
}