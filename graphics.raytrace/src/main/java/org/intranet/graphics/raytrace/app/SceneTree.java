package org.intranet.graphics.raytrace.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;

public class SceneTree
	extends JComponent
{
	private static final long serialVersionUID = 1L;

	private final SceneTreeModel treeMdl = new SceneTreeModel();
	private final JTree jtree;

	public SceneTree()
	{
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(100, 0));
		setPreferredSize(new Dimension(300, 500));

		jtree = new JTree(treeMdl);
		JScrollPane jsp = new JScrollPane(jtree);

		TreeCellRenderer renderer = new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree,
				Object value, boolean selected, boolean expanded, boolean leaf,
				int row, boolean hasFocus)
			{
				Component comp = super.getTreeCellRendererComponent(tree, value,
					selected, expanded, leaf, row, hasFocus);
				if (value instanceof Shape || value instanceof Light)
					((JLabel)comp).setText(value.getClass().getSimpleName());
				return comp;
			}
		};
		jtree.setCellRenderer(renderer);

		add(jsp, BorderLayout.CENTER);
	}

	public void setWorld(World world)
	{
		treeMdl.setWorld(world);
		expandTreeToDepth(4);
	}

	private void expandTreeToDepth(int expandDepth)
	{
		for (int i = 0; i < jtree.getRowCount(); i++)
			if (jtree.getPathForRow(i).getPath().length <= expandDepth)
				jtree.expandRow(i);
	}
}