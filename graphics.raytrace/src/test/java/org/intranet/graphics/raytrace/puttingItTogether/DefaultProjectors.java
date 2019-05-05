package org.intranet.graphics.raytrace.puttingItTogether;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.intranet.graphics.raytrace.puttingItTogether.projector.Projector;
import org.intranet.graphics.raytrace.puttingItTogether.projector.ProjectorGroup;

public final class DefaultProjectors
{
	static List<ProjectorGroup> createDefaultProjectors()
	{
		List<ProjectorGroup> list = new ArrayList<>();

		list.add(new ProjectorGroup("Clock", new ClockProjector()));
		list.add(new ProjectorGroup("Projectile", new ProjectileProjector()));

		List<SphereProjectionType> sphereProjectionTypeList =
			Arrays.asList(SphereProjectionType.values());

		List<Projector> basicSphereProjectors = sphereProjectionTypeList
			.stream()
			.map(BasicSphereProjector::new)
			.collect(Collectors.toList());
		list.add(new ProjectorGroup("Sphere Projection",
			basicSphereProjectors));

		List<Projector> phongSphereProjectors = sphereProjectionTypeList
			.stream()
			.map(PhongShadingSphereProjector::new)
			.collect(Collectors.toList());
		list.add(new ProjectorGroup("Phong Shading Projection",
			phongSphereProjectors));

		list.add(new ProjectorGroup("Make a Scene", new MakeAsceneProjector()));

		list.add(new ProjectorGroup("Scene with planes", new SceneWithPlanesProjector()));
		return list;
	}
}