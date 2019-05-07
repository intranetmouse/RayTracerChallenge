package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import org.intranet.graphics.raytrace.puttingItTogether.projector.ProjectorGroup;

public class ProjectorGroupTree
	extends JScrollPane
	implements ProjectorSelector
{
	private static final long serialVersionUID = 1L;

	public ProjectorGroupTree(List<ProjectorGroup> projectorGroups)
	{
		ProjectorGroupTreeModel mdl = new ProjectorGroupTreeModel(projectorGroups);
		JTree projectorTree = new JTree(mdl);
		projectorTree.getSelectionModel().setSelectionMode(
			TreeSelectionModel.SINGLE_TREE_SELECTION);
		setViewportView(projectorTree);
		projectorTree.getSelectionModel().addTreeSelectionListener(
			new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e)
				{
					ProjectorGroup proj = (ProjectorGroup) e
						.getNewLeadSelectionPath().getLastPathComponent();
					fireProjectorGroupSelected(proj);
				}
			}
		);
	}

	private List<ProjectorGroupSelectionListener> projGrpSelListeners = new ArrayList<>();
	@Override
	public void addProjectorGroupSelectionListener(
		ProjectorGroupSelectionListener l)
	{
		projGrpSelListeners.add(l);
	}
	@Override
	public void removeProjectorGroupSelectionListener(
		ProjectorGroupSelectionListener l)
	{
		projGrpSelListeners.remove(l);
	}
	protected void fireProjectorGroupSelected(ProjectorGroup proj)
	{
		for (ProjectorGroupSelectionListener l : projGrpSelListeners)
			l.projectorGroupSelected(proj);
	}
}