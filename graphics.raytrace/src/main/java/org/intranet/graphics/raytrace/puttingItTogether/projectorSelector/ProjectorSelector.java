package org.intranet.graphics.raytrace.puttingItTogether.projectorSelector;

public interface ProjectorSelector
{
	void addProjectorGroupSelectionListener(ProjectorGroupSelectionListener l);
	void removeProjectorGroupSelectionListener(ProjectorGroupSelectionListener l);
}