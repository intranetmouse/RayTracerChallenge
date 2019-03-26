package org.intranet.graphics.raytrace.puttingItTogether;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

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


		tabPane.addTab("Clock", new ProjectorUi(new ClockProjector()));

		tabPane.addTab("Projectile", new ProjectorUi(new ProjectileProjector()));

		List<Projector> basicSphereProjectors =
			Arrays.asList(SphereProjectionType.values()).stream()
				.map(BasicSphereProjector::new).collect(Collectors.toList());
		tabPane.addTab("Sphere Projection",
			new ProjectorUi(basicSphereProjectors));

		List<Projector> phongSphereProjectors = Arrays
			.asList(SphereProjectionType.values()).stream()
			.map(PhongShadingSphereProjector::new).collect(Collectors.toList());
		tabPane.addTab("Phong Shading Projection",
			new ProjectorUi(phongSphereProjectors));

		f.pack();
		f.setVisible(true);
	}
}
