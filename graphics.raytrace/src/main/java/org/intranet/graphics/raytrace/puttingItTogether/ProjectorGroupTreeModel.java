package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.intranet.graphics.raytrace.puttingItTogether.projector.ProjectorGroup;

public class ProjectorGroupTreeModel
	implements TreeModel
{
	private List<TreeModelListener> listeners = new ArrayList<>();
	@Override
	public void addTreeModelListener(TreeModelListener l)
	{ listeners.add(l); }
	@Override
	public void removeTreeModelListener(TreeModelListener l)
	{ listeners.remove(l); }

	private final List<ProjectorGroup> projectorGroups;
	private final String ROOT = "Exercises";

	public ProjectorGroupTreeModel(List<ProjectorGroup> projectorGroups)
	{
		this.projectorGroups = projectorGroups;
	}

	@Override
	public Object getChild(Object parent, int index)
	{
		if (!ROOT.equals(parent))
			return null;
		return projectorGroups.get(index);
	}

	@Override
	public int getChildCount(Object parent)
	{
		if (!ROOT.equals(parent))
			return 0;
		return projectorGroups.size();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child)
	{
		if (parent != null && child != null && ROOT.equals(parent))
			for (int i = 0; i < projectorGroups.size(); i++)
				if (projectorGroups.get(i).equals(child))
					return i;
		return -1;
	}

	@Override
	public Object getRoot()
	{ return ROOT; }

	@Override
	public boolean isLeaf(Object node)
	{
		return node != ROOT && getIndexOfChild(ROOT, node) > -1;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue)
	{
	}

}