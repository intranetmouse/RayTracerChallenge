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
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabPane = new JTabbedPane();
		f.add(tabPane);

		Canvas projectileCanvas = ProjectileTest.createProjectileCanvas();
		CanvasComponent projectileCanvasCanvas = new CanvasComponent(projectileCanvas);
		tabPane.addTab("Projectile", projectileCanvasCanvas);

		tabPane.addTab("Sphere Projection", new SphereProjectionUi(
			new BasicSphereProjector()));

		tabPane.addTab("Phong Shading Projection", new SphereProjectionUi(
			new PhongShadingSphereProjector()));

		f.pack();
		f.setVisible(true);
	}
}
