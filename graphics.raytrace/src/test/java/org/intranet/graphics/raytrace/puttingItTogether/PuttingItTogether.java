package org.intranet.graphics.raytrace.puttingItTogether;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class PuttingItTogether
{
	public static void main(String[] args)
		throws InvocationTargetException, InterruptedException
	{
		SwingUtilities.invokeAndWait(PuttingItTogether::init);
	}

	public static void init()
	{
		JFrame f = new JFrame("Putting It Together");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);

		List<ProjectorGroup> projectorGroups = DefaultProjectors.createDefaultProjectors();

		JTabbedPane tabPane = new TabbedProjectorUi(projectorGroups);

		f.add(tabPane);
		f.pack();
		f.setVisible(true);
	}
}
