package org.intranet.graphics.raytrace.projectile;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.projectile.SphereProjectionTest.SphereProjectionType;

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

		tabPane.addTab("Sphere Projection", new SphereProjectionUi());

		f.pack();
		f.setVisible(true);
	}

	public static final class SphereProjectionUi
		extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private final Canvas projectileCanvas = new Canvas(200, 200);

		public SphereProjectionUi()
		{
			super(new BorderLayout());

			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			add(toolBar, BorderLayout.NORTH);
			for (SphereProjectionType projType : SphereProjectionType.values())
			{
				toolBar.add(new AbstractAction(projType.getName()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e)
					{
						projectileCanvas.clear();
						SphereProjectionTest.renderSphereProjectionTo(
							projectileCanvas, projType);
						repaint();
					}
				});
			}

			CanvasComponent projectileCanvasComp = new CanvasComponent(projectileCanvas);
			add(projectileCanvasComp, BorderLayout.CENTER);
		}
	}
}
