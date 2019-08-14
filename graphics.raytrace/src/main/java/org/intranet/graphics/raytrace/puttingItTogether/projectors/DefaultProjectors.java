package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.intranet.graphics.raytrace.puttingItTogether.projector.Projector;
import org.intranet.graphics.raytrace.puttingItTogether.projector.ProjectorGroup;

public final class DefaultProjectors
{
	public static List<ProjectorGroup> createDefaultProjectors()
	{
		List<ProjectorGroup> list = new ArrayList<>();

		list.add(new ProjectorGroup("02-Projectile", new ProjectileProjector()));
		list.add(new ProjectorGroup("04-Clock", new ClockProjector()));

		List<SphereProjectionType> sphereProjectionTypeList =
			Arrays.asList(SphereProjectionType.values());

		List<Projector> basicSphereProjectors = sphereProjectionTypeList
			.stream()
			.map(BasicSphereProjector::new)
			.collect(Collectors.toList());
		list.add(new ProjectorGroup("05-Sphere Projection",
			basicSphereProjectors));

		List<Projector> phongSphereProjectors = sphereProjectionTypeList
			.stream()
			.map(PhongShadingSphereProjector::new)
			.collect(Collectors.toList());
		list.add(new ProjectorGroup("06-Phong Shading Projection",
			phongSphereProjectors));

		list.add(new ProjectorGroup("07-Sphere Walls", new Yaml07SphereWallsProjector()));

		list.add(new ProjectorGroup("08-Puppets", new Yaml08PuppetProjector()));

		list.add(new ProjectorGroup("09-Plane Walls", new Yaml09PlaneWallsProjector()));

		list.add(new ProjectorGroup("10-Pattern Striped", new Yaml10StripesProjector()));

		list.add(new ProjectorGroup("11-Reflection & Refraction", new Yaml11ReflectRefractProjector()));
		list.add(new ProjectorGroup("11-Book Reflection & Refraction", new Yaml11BookReflectRefractProjector()));
		list.add(new ProjectorGroup("11-Refract Sphere", new Yaml11RefractSphereProjector()));
		list.add(new ProjectorGroup("11-Fresnel", new Yaml11FresnelProjector()));

		list.add(new ProjectorGroup("12-Book Cube Table", new Yaml12BookCubeTableProjector()));
		list.add(new ProjectorGroup("13-Book Cylinders", new Yaml13BookCylinderProjector()));

		return list;
	}
}