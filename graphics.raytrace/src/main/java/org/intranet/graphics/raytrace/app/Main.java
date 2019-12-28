package org.intranet.graphics.raytrace.app;

import javax.swing.SwingUtilities;

public class Main
{
	RayTracingApp app;
	SceneAppWindow sceneAppWindow;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(
			() -> {
				RayTracingApp app = new RayTracingApp();
				SceneAppWindow sceneAppWindow = new SceneAppWindow(app);
				sceneAppWindow.setVisible(true);
			}
		);
	}
}
