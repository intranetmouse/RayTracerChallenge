package org.intranet.graphics.raytrace.puttingItTogether;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import org.intranet.graphics.raytrace.puttingItTogether.projectorGroup.ProjectorGroup;
import org.intranet.graphics.raytrace.puttingItTogether.projectors.DefaultProjectors;

public class PuttingItTogether
	extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
		throws InvocationTargetException, InterruptedException
	{
		SwingUtilities.invokeAndWait(PuttingItTogether::new);
	}

	public PuttingItTogether()
	{
		super("Putting It Together");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu appMenu = new JMenu("App");
		appMenu.setMnemonic('a');
		menuBar.add(appMenu);
		appMenu.add(new FunctionalAction("Exit", 'x', e -> System.exit(0)));

		List<ProjectorGroup> projectorGroups = DefaultProjectors.createDefaultProjectors();

		JComponent treeProjectorUi = new TreeProjectorUi(projectorGroups);

		add(treeProjectorUi);
		pack();
		setVisible(true);
	}
}
