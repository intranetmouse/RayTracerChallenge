package org.intranet.graphics.raytrace.projectile;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.intranet.graphics.raytrace.Canvas;

public class PuttingItTogether {

	public static void main(String[] args)
		throws InvocationTargetException, InterruptedException
	{
		SwingUtilities.invokeAndWait(PuttingItTogether::init);
	}

	public static void init()
	{
		JFrame f = new JFrame("Putting It Together");

		JTabbedPane tabPane = new JTabbedPane();
		f.add(tabPane);

		Canvas projectileCanvas = ProjectileTest.createProjectileCanvas();
		CanvasCanvas projectileCanvasCanvas = new CanvasCanvas(projectileCanvas);
		tabPane.addTab("Projectile", projectileCanvasCanvas);

		f.pack();
		f.setVisible(true);
	}
}
