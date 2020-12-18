package org.intranet.graphics.raytrace.app;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.shape.Csg;
import org.intranet.graphics.raytrace.shape.Group;

public final class SceneTreeModel
	implements TreeModel
{
	private static final String ROOT_NODE = "World";
	private static final String SCENE_OBJECTS = "Scene Objects";
	private static final String LIGHTS = "Lights";

	private List<TreeModelListener> listeners = new ArrayList<>();
	@Override
	public void addTreeModelListener(TreeModelListener l) { listeners.add(l); }
	@Override
	public void removeTreeModelListener(TreeModelListener l) { listeners.remove(l); }

	private World world;
	public void setWorld(World value)
	{
		world = value;
		for (TreeModelListener listener : listeners)
			listener.treeStructureChanged(new TreeModelEvent(this, new Object[] { ROOT_NODE }));
	}

	@Override
	public Object getRoot() { return ROOT_NODE; }

	@Override
	public int getChildCount(Object parent)
	{
		if (parent == ROOT_NODE)
			return world == null ? 0 : 2;
		if (parent == LIGHTS)
			return world.getLightSources().size();
		if (parent == SCENE_OBJECTS)
			return world.getSceneObjects().size();

		if (parent instanceof Group)
			return ((Group)parent).getChildren().size();
		if (parent instanceof Csg)
		{
			List<Object> objs = getCsgChildren(parent);
			return objs.size();
		}

		return 0;
	}

	@Override
	public boolean isLeaf(Object node)
	{
		if (node == ROOT_NODE || node == LIGHTS || node == SCENE_OBJECTS || node instanceof Group || node instanceof Csg)
			return false;
		return true;
	}

	@Override
	public Object getChild(Object parent, int index)
	{
		if (parent == ROOT_NODE)
		{
			if (index == 0)
				return LIGHTS;
			if (index == 1)
				return SCENE_OBJECTS;
		}

		if (parent instanceof Group)
			return ((Group)parent).getChildren().get(index);

		if (parent instanceof Csg)
		{
			List<Object> objs = getCsgChildren(parent);
			return objs.get(index);
		}

		if (parent == LIGHTS)
			return world.getLightSources().get(index);

		if (parent == SCENE_OBJECTS)
			return world.getSceneObjects().get(index);

		return null;
	}
	private List<Object> getCsgChildren(Object parent)
	{
		List<Object> objs = new ArrayList<>(2);
		Csg csg = (Csg)parent;
		if (csg.getLeft() != null)
			objs.add(csg.getLeft());
		if (csg.getRight() != null)
			objs.add(csg.getRight());
		return objs;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child)
	{
		if (parent == null || child == null)
			return -1;

		if (parent == ROOT_NODE)
		{
			if (child == LIGHTS)
				return 0;
			if (child == SCENE_OBJECTS)
				return 1;
		}
		if (parent instanceof Group)
			return ((Group)parent).getChildren().indexOf(child);
		if (parent instanceof Csg)
		{
			List<Object> objs = getCsgChildren(parent);
			return objs.indexOf(child);
		}

		if (parent == LIGHTS)
			return world.getLightSources().indexOf(child);

		if (parent == SCENE_OBJECTS)
			return world.getSceneObjects().indexOf(child);

		return -1;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue)
	{
	}
}